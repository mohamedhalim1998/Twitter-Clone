package com.mohamed.halim.profileservice;

import java.io.IOException;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.mohamed.halim.dtos.AuthResponse;
import com.mohamed.halim.dtos.LoginDto;
import com.mohamed.halim.dtos.PasswordValidation;
import com.mohamed.halim.dtos.ProfileDto;
import com.mohamed.halim.dtos.RegisterDto;
import com.mohamed.halim.profileservice.Repositories.BlockRepository;
import com.mohamed.halim.profileservice.Repositories.FollowRepository;
import com.mohamed.halim.profileservice.Repositories.ProfileRepository;
import com.mohamed.halim.profileservice.model.Block;
import com.mohamed.halim.profileservice.model.Follow;
import com.mohamed.halim.profileservice.model.Profile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;
    private RabbitTemplate rabbit;
    private MessageConverter converter;
    private FollowRepository followRepository;
    private BlockRepository blockRepository;

    @RabbitListener(queues = "profile.user.register")
    public void registerUser(RegisterDto dto, Message message) throws IOException {
        MessageProperties props = message.getMessageProperties();
        if (profileRepository.findByEmail(dto.getEmail()).isPresent()) {
            props.setHeader("error", "Email used before");
            rabbit.send(props.getReplyTo(), converter.toMessage("", props));
            return;
        }
        if (profileRepository.findByUsername(dto.getUsername()).isPresent()) {
            props.setHeader("error", "username used before");
            rabbit.send(props.getReplyTo(), converter.toMessage("", props));
            return;
        }
        Profile saved = profileRepository.save(registerToProfile(dto));
        rabbit.send(props.getReplyTo(), converter.toMessage(buildAuthResponse(saved), props));
    }
    public Profile registerToProfile(RegisterDto dto) {
        return Profile.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .createdAt(new Date())
                .birthDay(new Date(dto.getBirthDay()))
                .build();
    }

    @RabbitListener(queues = "profile.load.profile")
    public Profile loadByUsername(String username) {
        return profileRepository.findByUsername(username).get();
    }

    private AuthResponse buildAuthResponse(Profile profile) {
        Message message = rabbit.sendAndReceive("jwt",
                "jwt.token.generate",
                converter.toMessage(profile, null));
        String token = (String) converter.fromMessage(message);
        return AuthResponse.builder()
                .username(profile.getUsername())
                .email(profile.getEmail())
                .token(token)
                .build();
    }

    @RabbitListener(queues = "profile.user.login")
    public void login(LoginDto dto, Message message) {
        MessageProperties props = message.getMessageProperties();
        var profileOptional = profileRepository.findByUsername(dto.getUsername());
        if (profileOptional.isEmpty()) {
            profileOptional = profileRepository.findByEmail(dto.getUsername());
        }
        if (profileOptional.isPresent()
                && checkPassword(dto.getPassword(), profileOptional.get().getPassword())) {
            rabbit.send(props.getReplyTo(),
                    converter.toMessage(buildAuthResponse(profileOptional.get()), props));
            return;
        }
        props.setHeader("error", "username or password is incorrect");
        rabbit.send(props.getReplyTo(), converter.toMessage("", props));
    }

    private boolean checkPassword(String password, String hash) {
        return rabbit.convertSendAndReceiveAsType("auth", "auth.password.validation",
                new PasswordValidation(password, hash), new ParameterizedTypeReference<Boolean>() {
                });
    }

    public ProfileDto getProfile(String username) {
        return profileRepository.findByUsername(username).map(this::mapToDto).get();
    }

    private ProfileDto mapToDto(Profile profile) {
        ProfileDto dto = dtoFromProfile(profile);
        dto.setFollowing(followRepository.countByFollower(profile.getUsername()));
        dto.setFollowers(followRepository.countByFollowing(profile.getUsername()));
        int count = rabbit.convertSendAndReceiveAsType("tweet", "tweet.user.tweets.count", profile.getUsername(),
                new ParameterizedTypeReference<Integer>() {

                });
        dto.setTweets(count);
        return dto;

    }

    public ProfileDto dtoFromProfile(Profile profile) {
        return ProfileDto.builder()
                .fullname(profile.getFullname())
                .username(profile.getUsername())
                .createdAt(profile.getCreatedAt().getTime())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .pinnedTweetId(profile.getPinnedTweetId())
                .profileImageUrl(profile.getProfileImageUrl())
                .coverImageUrl(profile.getCoverImageUrl())
                .profileProtected(profile.isProfileProtected())
                .build();
    }

    public void follow(String authHeader, String following) {
        String follower = rabbit.convertSendAndReceiveAsType("jwt", "jwt.token.extract.username",
                authHeader.substring(7), new ParameterizedTypeReference<String>() {
                });
        if (follower != null) {
            followRepository.save(new Follow(null, follower, following));
        }
    }

    public void unfollow(String authHeader, String following) {
        String follower = rabbit.convertSendAndReceiveAsType("jwt", "jwt.token.extract.username",
                authHeader.substring(7), new ParameterizedTypeReference<String>() {
                });
        if (follower != null)
            followRepository.deleteByFollowerAndFollowing(follower, following);

    }

    public void block(String authHeader, String blocking) {
        String user = rabbit.convertSendAndReceiveAsType("jwt", "jwt.token.extract.username",
                authHeader.substring(7), new ParameterizedTypeReference<String>() {
                });
        if (user != null)
            blockRepository.save(new Block(null, user, blocking));
    }

}

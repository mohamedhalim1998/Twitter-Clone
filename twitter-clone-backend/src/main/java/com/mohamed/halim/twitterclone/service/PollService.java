package com.mohamed.halim.twitterclone.service;

import org.springframework.stereotype.Service;

import com.mohamed.halim.twitterclone.model.dto.PollDto;
import com.mohamed.halim.twitterclone.repository.PollRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PollService {
    private PollRepository pollRepository;

    public Long addPoll(PollDto pollDto) {
        return pollRepository.save(pollDto.toPoll()).getId();
    }
}

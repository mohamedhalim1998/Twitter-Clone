package com.mohamed.halim.pollservice;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.mohamed.halim.dtos.PollDto;
import com.mohamed.halim.pollservice.model.Poll;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PollService {
    private PollRepository pollRepository;

    @RabbitListener(queues = "poll.add")
    public PollDto addPoll(PollDto pollDto) {
        Poll poll = pollRepository.save(Poll.fromDto(pollDto));
        return poll.toDto();
    }
    @RabbitListener(queues = "poll.get")
    public PollDto getPoll(Long pollId) {
        return pollRepository.findById(pollId).map(poll -> poll.toDto()).get();
    }

}

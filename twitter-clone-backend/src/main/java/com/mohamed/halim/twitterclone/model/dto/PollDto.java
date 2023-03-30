package com.mohamed.halim.twitterclone.model.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.IntStream;

import com.mohamed.halim.twitterclone.model.Option;
import com.mohamed.halim.twitterclone.model.Poll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollDto {
    private Long id;
    private List<String> options;
    private Long duration;
    private Long endDate;

    public static PollDto fromPoll(Poll poll) {
        return PollDto.builder()
                .id(poll.getId())
                .options(poll.getOptions().stream().map(option -> option.getLabel()).toList())
                .duration(poll.getDuration())
                .endDate(poll.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();
    }

    public Poll toPoll() {
        return Poll.builder()
                .id(this.getId())
                .options(IntStream.range(0, options.size()).mapToObj(i -> {
                    return new Option(i, getOption(i));
                }).toList())
                .duration(this.getDuration())
                .endTime(LocalDateTime.now().plusSeconds(duration / 1000))
                .build();
    }

    private String getOption(int i) {
        return options.get(i);
    }
}
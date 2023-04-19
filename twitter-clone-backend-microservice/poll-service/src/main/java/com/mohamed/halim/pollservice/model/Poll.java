package com.mohamed.halim.pollservice.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.IntStream;

import com.mohamed.halim.dtos.PollDto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "poll_options", joinColumns = @JoinColumn(name = "poll_id"), foreignKey = @ForeignKey(name = "option_poll_fk"))
    private List<Option> options;
    private Long duration;
    private LocalDateTime endTime;

    public static Poll fromDto(PollDto dto) {
        return Poll.builder()
                .id(dto.getId())
                .options(IntStream.range(0, dto.getOptions().size()).mapToObj(i -> {
                    return new Option(i, dto.getOptions().get(i));
                }).toList())
                .duration(dto.getDuration())
                .endTime(LocalDateTime.now().plusSeconds(dto.getDuration() / 1000))
                .build();
    }

    public PollDto toDto() {
        return PollDto.builder()
                .id(this.getId())
                .options(this.getOptions().stream().map(option -> option.getLabel()).toList())
                .duration(this.getDuration())
                .endDate(this.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();
    }
}

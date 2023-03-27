package com.mohamed.halim.twitterclone.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Poll {
    @Id
    private Long id;
    @ElementCollection
    @CollectionTable(name = "pool_options", joinColumns = @JoinColumn(name = "poll_id"), foreignKey = @ForeignKey(name = "option_poll_fk"))
    private List<Option> options;
    private Long duration;
    private LocalDateTime endTime;

}

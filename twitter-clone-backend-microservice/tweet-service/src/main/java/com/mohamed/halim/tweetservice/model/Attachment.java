package com.mohamed.halim.tweetservice.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Enumerated(EnumType.STRING)
    private AttachmentType type;
    private Long attacmentId;

}

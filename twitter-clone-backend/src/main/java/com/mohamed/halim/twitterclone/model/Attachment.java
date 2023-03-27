package com.mohamed.halim.twitterclone.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    private AttachmentType type;
    private Long attacmentId;

}

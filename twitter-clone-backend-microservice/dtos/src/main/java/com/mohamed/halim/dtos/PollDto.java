package com.mohamed.halim.dtos;

import java.util.List;


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

    
}
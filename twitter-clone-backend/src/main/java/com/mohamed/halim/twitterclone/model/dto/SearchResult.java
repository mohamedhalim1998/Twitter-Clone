package com.mohamed.halim.twitterclone.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResult {
    private List<ProfileDto> user;
    private List<TweetDto> tweets;

}

package com.mohamed.halim.twitterclone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohamed.halim.twitterclone.model.dto.SearchResult;
import com.mohamed.halim.twitterclone.service.SearchService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/search")
@AllArgsConstructor
public class SearchController {
    private SearchService searchService;

    @GetMapping()
    public SearchResult search(@RequestParam("query") String query,
            @RequestParam(name = "filter", required = false) String filter) {
        return searchService.search(query, filter);
    }

}

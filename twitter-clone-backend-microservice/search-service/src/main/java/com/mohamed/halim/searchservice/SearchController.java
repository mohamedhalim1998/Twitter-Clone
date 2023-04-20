package com.mohamed.halim.searchservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohamed.halim.dtos.SearchResult;

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

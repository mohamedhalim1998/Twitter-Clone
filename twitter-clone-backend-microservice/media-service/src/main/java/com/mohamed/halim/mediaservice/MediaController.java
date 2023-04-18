package com.mohamed.halim.mediaservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/media")
@AllArgsConstructor
public class MediaController {
    private MediaService mediaService;

    @GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource getMethodName(@PathVariable String name) throws FileNotFoundException {
        return mediaService.loadImage(name);
    }

}

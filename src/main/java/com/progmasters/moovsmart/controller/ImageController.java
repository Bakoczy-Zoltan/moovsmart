package com.progmasters.moovsmart.controller;

import com.progmasters.moovsmart.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    public @ResponseBody String uploadPicture(@RequestParam("myPicture") MultipartFile imageToUpload) throws IOException {
        String controllerResult = imageService.uploadImage(imageToUpload);
        System.out.println(controllerResult);
        return controllerResult;
    }
}

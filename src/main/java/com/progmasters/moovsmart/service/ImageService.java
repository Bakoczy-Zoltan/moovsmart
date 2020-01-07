package com.progmasters.moovsmart.service;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import com.cloudinary.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Map;

@Service
@Transactional
public class ImageService {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "moovsmart",
            "api_key", "214524436422785",
            "api_secret", "ZyTQRXDv4vTFXIq8SEhQEcE0ebc"));


    public String uploadImage(MultipartFile imageToUpload) throws IOException {

        Map uploadResult = cloudinary.uploader().upload(imageToUpload.getBytes(), ObjectUtils.emptyMap());

        return  ((String) uploadResult.get("public_id"));
    }
}

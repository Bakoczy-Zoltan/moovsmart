package com.progmasters.moovsmart.service;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import com.cloudinary.*;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Map;

@Service
@Transactional
public class ImageService {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "moovsmart",
            "api_key", "214524436422785",
            "api_secret", "ZyTQRXDv4vTFXIq8SEhQEcE0ebc"));


    public void uploadImage(File imageToUpload) throws IOException {

        Map uploadResult = cloudinary.uploader().upload(imageToUpload, ObjectUtils.emptyMap());
        System.out.println(uploadResult);
    }
}

package com.progmasters.moovsmart.controller;

import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private PropertyService propertyService;

    @Autowired
    public AdminController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

}

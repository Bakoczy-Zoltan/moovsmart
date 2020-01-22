package com.progmasters.moovsmart.controller;

import com.progmasters.moovsmart.dto.CreateQueryByDatesCommand;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.service.MailSenderService;
import com.progmasters.moovsmart.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/properties/admin")
public class AdminController {

    private PropertyService propertyService;
    private MailSenderService mailSenderService;
    private Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    public AdminController(PropertyService propertyService, MailSenderService mailSenderService) {
        this.propertyService = propertyService;
        this.mailSenderService = mailSenderService;
    }

    @GetMapping("/propertyListForApproval")
    public ResponseEntity<List<PropertyForm>> getAllHoldingProperty() {
        List<PropertyForm> listOfHoldingProperty = this.propertyService.getAllHoldingProperty();
        return new ResponseEntity<>(listOfHoldingProperty, HttpStatus.OK);
    }

    @GetMapping("/propertyDetailsForApproval/{id}")
    public ResponseEntity<PropertyForm> getPropertyDetailsForApproval(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.propertyService.getPropertyDetailsForApproval(id), HttpStatus.OK);
    }

    @GetMapping("getArchivedProperties")
    public ResponseEntity<List<PropertyListItem>> getArchivedProperties() {
        List<PropertyListItem> listOfArchiveds = this.propertyService.getAllArchiveds();
        return new ResponseEntity<>(listOfArchiveds, HttpStatus.OK);
    }

    @PostMapping("/getArchivedProperties")
    public ResponseEntity<List<PropertyForm>> getArchivedProperties(@RequestBody CreateQueryByDatesCommand command) {
        List<PropertyForm> listOfProperties = this.propertyService.getArchivedProperties(command);
        return new ResponseEntity<>(listOfProperties, HttpStatus.OK);
    }

    @GetMapping("/getPropertyLIstByUserMail/{id}")
    public ResponseEntity<List<PropertyForm>> getPropertyListByUserMail(@PathVariable("id") String mail) {
        List<PropertyForm> propertyForms = this.propertyService.getAllPropertyByMail(mail);
        if (propertyForms != null) {
            return new ResponseEntity<>(propertyForms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(propertyForms, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/activateProperty/{id}")
    public ResponseEntity makePropertyActivated(@PathVariable("id") Long id) {
        Boolean successActivating = this.propertyService.activateProperty(id);
        if (successActivating) {
            this.logger.info("Property of Id: " + id + " is activated");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            this.logger.warn("Property with id of " + id + " not found");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/forbiddenProperty/{id}")
    public ResponseEntity forbiddenProperty(@PathVariable("id") Long id) {
        Boolean successForbidden = this.propertyService.forbiddenProperty(id);
        if (successForbidden) {
            this.logger.info("Property of Id: " + id + " is forbidden");
            return new ResponseEntity(HttpStatus.OK);
        } else {
            this.logger.warn("Property with id of " + id + " not found");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/saveDatabase")
    public ResponseEntity saveDatabase() {
        try {
            this.mailSenderService.saveDB();
        } catch (SQLException | IOException | ClassNotFoundException f) {
            this.logger.warn(f.getMessage());
            this.logger.warn("Database saving failed");
        }
        this.logger.info("DataBase saved");
        return new ResponseEntity(HttpStatus.OK);

    }

}

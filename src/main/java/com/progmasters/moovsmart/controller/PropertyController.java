package com.progmasters.moovsmart.controller;

import com.progmasters.moovsmart.domain.County;
import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;
import com.progmasters.moovsmart.dto.*;
import com.progmasters.moovsmart.service.PropertyService;
import com.progmasters.moovsmart.validation.PropertyFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private PropertyService propertyService;

    private PropertyFormValidator propertyFormValidator;
    private Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    public PropertyController(PropertyService propertyService,
                              PropertyFormValidator propertyFormValidator) {
        this.propertyService = propertyService;
        this.propertyFormValidator = propertyFormValidator;
    }

    @InitBinder("propertyForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(propertyFormValidator);
    }

    @GetMapping("/formData")
    public ResponseEntity<PropertyInitFormData> getPropertyFormData() {
        PropertyInitFormData initFormData = new PropertyInitFormData(getCounties(),
                getPropertyTypes(), getPropertyStates());
        return new ResponseEntity<>(initFormData, HttpStatus.OK);
    }

    private List<PropertyTypeOption> getPropertyTypes() {
        List<PropertyTypeOption> propertyTypeOptions = new ArrayList<>();
        for (PropertyType propertyType : PropertyType.values()) {
            propertyTypeOptions.add(new PropertyTypeOption(propertyType));
        }
        return propertyTypeOptions;
    }

    private List<PropertyStateOption> getPropertyStates() {
        List<PropertyStateOption> propertyStateOptions = new ArrayList<>();
        for (PropertyState propertyState : PropertyState.values()) {
            propertyStateOptions.add(new PropertyStateOption(propertyState));
        }
        return propertyStateOptions;
    }

    private List<CountyOption> getCounties() {
        List<CountyOption> countyOptions = new ArrayList<>();
        for (County county : County.values()) {
            countyOptions.add(new CountyOption(county));
        }
        return countyOptions;
    }

    @GetMapping
    public ResponseEntity<List<PropertyListItem>> getAllProperties() {
        logger.info("Get properties-list");
        return new ResponseEntity<>(propertyService.getProperties(), HttpStatus.OK);
    }
    @GetMapping("/authUSer/{id}")
    public ResponseEntity<List<PropertyListItem>> getOwnProperties(@PathVariable("id")String user) {
        logger.info("Get own properties-list");
        return new ResponseEntity<>(propertyService.getOwnProperties(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDetails> getPropertyDetails(@PathVariable Long id) {

        logger.info("property-details requested");
        return new ResponseEntity<>(propertyService.getPropertyDetails(id), HttpStatus.OK);
    }

    @PostMapping("/authUser")
    public ResponseEntity createProperty(@RequestBody @Valid PropertyForm propertyForm) {
        propertyService.createProperty(propertyForm);
        this.logger.info("New Property created");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/authUser/{id}")
    public ResponseEntity updateProperty(@Valid @RequestBody PropertyForm propertyForm, @PathVariable Long id) {
        Property updatedProperty = propertyService.updateProperty(propertyForm, id);
        ResponseEntity result;
        if (updatedProperty == null) {
            result = new ResponseEntity(HttpStatus.NOT_FOUND);
            this.logger.info("Property (id: " + id + ") not found");
        } else {
            result = new ResponseEntity(HttpStatus.OK);
            this.logger.info("Property (id: " + id + ") is updated");
        }
        return result;
    }

    @DeleteMapping("/authUser/{id}")
    public ResponseEntity deleteProperty(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String userMail = user.getUsername();
        boolean isDeleteSuccessful = propertyService.deleteProperty(id, userMail);
        ResponseEntity result;
        if (isDeleteSuccessful) {
            result = new ResponseEntity<>(HttpStatus.OK);
            this.logger.info("Property (id: " + id + ") deleted");
        } else {
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            this.logger.info("Property (id: " + id + ") not found");
        }
        return result;
    }

    @PostMapping("/filteredList")
    public ResponseEntity<List<Property>> getFilteredList(@RequestBody CreateFilteredCommand command) {
        List<Property>filteredList = this.propertyService.getFilteredProperties(command);
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }
}

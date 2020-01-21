package com.progmasters.moovsmart.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.progmasters.moovsmart.domain.*;
import com.progmasters.moovsmart.dto.*;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyService {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "moovsmart",
            "api_key", "214524436422785",
            "api_secret", "ZyTQRXDv4vTFXIq8SEhQEcE0ebc"));

    private PropertyRepository propertyRepository;
    private UserRepository userRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public List<PropertyListItem> getProperties() {
        List<Property> properties = propertyRepository.findAllByIsValid();
        return properties.stream().map(PropertyListItem::new).collect(Collectors.toList());
    }

    public PropertyDetails getPropertyDetails(Long id) {
        Property property = findById(id);
        return new PropertyDetails(property);
    }

    public void createProperty(PropertyForm propertyForm, String mail) {
        Optional<UserProperty> tempUser = this.userRepository.findUserPropertiesByMail(mail);
        UserProperty user = new UserProperty();
        if (tempUser.isPresent()) {
            user = tempUser.get();
        }
        Property property = new Property();
        updateValues(propertyForm, property, user, StatusOfProperty.valueOf("HOLDING"));
        propertyRepository.save(property);
    }


    public Property updateProperty(PropertyForm propertyForm, Long id, String mail) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        Optional<UserProperty> tempUser = this.userRepository.findUserPropertiesByMail(mail);
        UserProperty user = new UserProperty();
        if (tempUser.isPresent()) {
            user = tempUser.get();
        }
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            updateValues(propertyForm, property, user, StatusOfProperty.valueOf("EXCEPTED"));
            propertyRepository.save(property);
            return property;
        } else {
            return null;
        }
    }

    private void updateValues(PropertyForm propertyForm, Property property, UserProperty user, StatusOfProperty status) {
        property.setLocalDateTime(LocalDateTime.now());
        property.setName(propertyForm.getName());
        property.setNumberOfRooms(propertyForm.getNumberOfRooms());
        property.setPrice(propertyForm.getPrice());
        property.setBuildingYear(propertyForm.getBuildingYear());
        property.setArea(propertyForm.getArea());
        property.setPropertyType(PropertyType.valueOf(propertyForm.getPropertyType()));
        property.setPropertyState(PropertyState.valueOf(propertyForm.getPropertyState()));
        property.setCounty(County.valueOf(propertyForm.getCounty()));
        property.setZipCode(propertyForm.getZipCode());
        property.setStreet(propertyForm.getStreet());
        property.setStreetNumber(propertyForm.getStreetNumber());
        property.setDescription(propertyForm.getDescription());
        property.setOwner(user);
        property.setLngCoord(propertyForm.getLngCoord());
        property.setLatCoord(propertyForm.getLatCoord());
        property.setImageUrls(propertyForm.getImageUrl());
        property.setPublicIds(propertyForm.getPublicId());
        property.setCity(propertyForm.getCity());
        property.setValid(true);
        property.setStatus(status);
    }

    public boolean deleteProperty(Long id, String userMail) {
        boolean result = false;
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        //  UserProperty user = null;
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            System.out.println("ID PROP: " + property.getId());
//            user = property.getOwner();
//            if(!user.getMail().equals(userMail)){
//                return result;
//            }
            property.setValid(false);
            result = true;
        }

        return result;
    }

    private Property findById(Long id) {
        return propertyRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    private UserProperty findUserPropertiesByMail(String mail) {
        UserProperty user = new UserProperty();
        Optional<UserProperty> tempUser = this.userRepository.findUserPropertiesByMail(mail);
        if (tempUser.isPresent()) {
            user = tempUser.get();
        }
        return user;
    }

    public List<PropertyListItem> getFilteredProperties(CreateFilteredCommand command) {
        List<PropertyListItem> propertyListItemList = new ArrayList<>();
        List<Property> filteredList = this.propertyRepository.getFilteredProperties(
                command.getMinSize(), command.getMaxSize(),
                command.getMinPrice(), command.getMaxPrice(),
                command.getPropertyState(), command.getPropertyType(),
                command.getCity(), command.getNumberOfRooms()
        );

        for (Property property : filteredList) {
            propertyListItemList.add(new PropertyListItem(property));
        }
        return propertyListItemList;
    }

    public List<PropertyListItem> getFilteredPropertiesWithoutRooms(CreateFilteredCommand command) {

        List<PropertyListItem> propertyListItemList = new ArrayList<>();
        List<Property> filteredList = this.propertyRepository.getFilteredListWithoutRoom(
                command.getMinSize(), command.getMaxSize(),
                command.getMinPrice(), command.getMaxPrice(),
                command.getPropertyState(), command.getPropertyType(),
                command.getCity()
        );

        for (Property property : filteredList) {
            propertyListItemList.add(new PropertyListItem(property));
        }
        return propertyListItemList;

    }

    public List<PropertyListItem> getOwnProperties(String userMail) {
        List<Property> properties = propertyRepository.findAllByIsValid();
        List<PropertyListItem> ownProperties = new ArrayList<>();

        Optional<UserProperty> tempUser = this.userRepository.findUserPropertiesByMail(userMail);
        UserProperty ownUser = null;

        if (tempUser.isPresent()) {
            ownUser = tempUser.get();
            System.out.println("USER " + ownUser.getMail());
            for (Property property : properties) {
                if (property.getOwner() != null) {
                    if (property.getOwner().getId().equals(ownUser.getId())) {
                        ownProperties.add(new PropertyListItem(property));
                    }
                }
            }

        }

        return ownProperties;
    }

    public List<String> getCityList() {
        return this.propertyRepository.getAllCity();
    }

    public PictureListItem getPropertyPictures(Long id) {
        Property property = findById(id);
        return new PictureListItem(property);
    }

    public void deletePicture(String pictureIdToDelete, Long id) throws IOException {
        cloudinary.uploader().destroy(pictureIdToDelete, ObjectUtils.emptyMap());
    }

    public void updatePictureList(PictureListItem imageToDelete, Long id) {
        Property property = findById(id);

        property.setImageUrls(imageToDelete.getImageUrl());
        property.setPublicIds(imageToDelete.getPublicId());
    }


    public List<PropertyForm> getAllHoldingProperty() {
        List<PropertyForm> propertyFormList = new ArrayList<>();
        List<Property> allHoldingProperty = this.propertyRepository.getAllHoldingProperty();
        for (Property property : allHoldingProperty) {
            propertyFormList.add(new PropertyForm(property));
        }
        return propertyFormList;
    }

    public List<PropertyForm> getArchivedProperties(CreateQueryByDatesCommand command) {
        List<PropertyForm> propertyFormList = new ArrayList<>();
        List<Property> allPropertiesByDates = this.propertyRepository.getAllArchivedPropertiesByDates(command.getDateFrom(), command.getDateTo());
        for (Property property : allPropertiesByDates) {
            propertyFormList.add(new PropertyForm(property));
        }
        return propertyFormList;
    }

    public ResponseEntity getAllPropertyByMail(String mail) {
        List<PropertyForm> propertyFormList = new ArrayList<>();
        Optional<UserProperty> tempUser = this.userRepository.findUserPropertiesByMail(mail);

        if (tempUser.isPresent()) {
            UserProperty user = tempUser.get();
            List<Property> properties = this.propertyRepository.findAllByOwner(user);
            for (Property property : properties) {
                propertyFormList.add(new PropertyForm(property));
            }

            return new ResponseEntity<>(propertyFormList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(propertyFormList, HttpStatus.NOT_FOUND);
        }
    }

    public Boolean activateProperty(Long id) {
        Optional<Property> tempProperty = this.propertyRepository.findById(id);
        if (tempProperty.isPresent()) {
            Property property = tempProperty.get();
            property.setStatus(StatusOfProperty.valueOf("ACCEPTED"));
            property.setValid(true);
            this.propertyRepository.save(property);
            return true;
        }
        else {
            return false;
        }
    }
}

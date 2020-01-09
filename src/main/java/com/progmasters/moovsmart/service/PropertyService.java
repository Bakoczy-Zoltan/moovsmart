package com.progmasters.moovsmart.service;

import com.progmasters.moovsmart.domain.*;
import com.progmasters.moovsmart.dto.CreateFilteredCommand;
import com.progmasters.moovsmart.dto.PropertyDetails;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyService {

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

    public void createProperty(PropertyForm propertyForm) {
        System.out.println("URLS List: => " + propertyForm.getImageUrl());
        Optional<UserProperty>tempUser = this.userRepository.findUserPropertiesByMail(propertyForm.getOwner());
        UserProperty user = new UserProperty();
        if(tempUser.isPresent()){
            user = tempUser.get();
        }
        Property property = new Property();
        findUserPropertiesByMail(propertyForm.getOwner());
        updateValues(propertyForm, property, user);
        propertyRepository.save(property);
    }


    public Property updateProperty(PropertyForm propertyForm, Long id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        Optional<UserProperty>tempUser = this.userRepository.findUserPropertiesByMail(propertyForm.getOwner());
        UserProperty user = new UserProperty();
        if(tempUser.isPresent()){
            user = tempUser.get();
        }
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            updateValues(propertyForm, property, user);
            propertyRepository.save(property);
            return property;
        } else {
            return null;
        }
    }

    private void updateValues(PropertyForm propertyForm, Property property, UserProperty user) {
        property.setName(propertyForm.getName());
        property.setNumberOfRooms(propertyForm.getNumberOfRooms());
        property.setPrice(propertyForm.getPrice());
        property.setBuildingYear(propertyForm.getBuildingYear());
        property.setArea(propertyForm.getArea());
        property.setPropertyType(PropertyType.valueOf(propertyForm.getPropertyType()));
        property.setPropertyState(PropertyState.valueOf(propertyForm.getPropertyState()));
        property.setCounty(County.valueOf(propertyForm.getCounty()));
        property.setZipCode(propertyForm.getZipCode());
        property.setDescription(propertyForm.getDescription());
        property.setOwner(user);
        property.setLngCoord(propertyForm.getLngCoord());
        property.setLatCoord(propertyForm.getLatCoord());
        property.setImageUrls(propertyForm.getImageUrl());
        property.setCity(propertyForm.getCity());
        property.setValid(true);
    }

    public boolean deleteProperty(Long id, String userMail) {
        boolean result = false;
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        UserProperty user = null;
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            user = property.getOwner();
            if(!user.getMail().equals(userMail)){
                return result;
            }
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
      Optional<UserProperty>tempUser = this.userRepository.findUserPropertiesByMail(mail);
      if(tempUser.isPresent()){
          user = tempUser.get();
      }
       return user;
    }

    public List<Property> getFilteredProperties(CreateFilteredCommand command) {
        List<Property>filteredList = this.propertyRepository.getFilteredProperties(
                command.getMinArea(), command.getMaxArea(),
                command.getMinPrice(), command.getMaxPrice(),
                command.getPropertyState(), command.getPropertyType(),
                command.getCity(), command.getNumberOfRooms()
        );
        return filteredList;
    }
}

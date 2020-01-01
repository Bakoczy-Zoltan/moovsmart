package com.progmasters.moovsmart.service;

import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.dto.PropertyDetails;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.repository.PropertyRepository;
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

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
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
        propertyRepository.save(new Property(propertyForm));
    }


    public Property updateProperty(PropertyForm propertyForm, Long id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            propertyRepository.save(property);
            return property;
        } else {
            return null;
        }
    }

    public boolean deleteProperty(Long id) {
        boolean result = false;
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            property.setValid(false);
//            propertyRepository.save(property);
//            propertyRepository.delete(property);
            result = true;
        }

        return result;
    }

    private Property findById(Long id) {
        return propertyRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

}

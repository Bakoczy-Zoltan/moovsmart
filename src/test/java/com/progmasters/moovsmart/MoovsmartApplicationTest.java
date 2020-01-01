package com.progmasters.moovsmart;

import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.service.PropertyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoovsmartApplication.class)
public class MoovsmartApplicationTest {

    @Autowired
    private PropertyService propertyService;

    @Test
    public void testAddProperty() {
        List<PropertyListItem> propertyList = propertyService.getProperties();

    }
}

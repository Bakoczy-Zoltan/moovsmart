package com.progmasters.moovsmart;

import com.progmasters.moovsmart.service.PropertyService;
import com.progmasters.moovsmart.controller.PropertyController;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MoovsmartApplicationTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;


}

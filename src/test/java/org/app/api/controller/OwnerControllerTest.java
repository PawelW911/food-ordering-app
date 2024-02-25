package org.app.api.controller;

import org.app.api.dto.OwnerDTO;
import org.app.api.dto.mapper.OwnerDTOMapper;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.RestaurantService;
import org.app.domain.Owner;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.infrastructure.zipCode.ZipCodeImpl;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

//    @Mock
//    OwnerDTOMapper ownerDTOMapper;
//
//    @Mock
//    RestaurantDTOMapper restaurantDTOMapper;
//
//    @Mock
//    RestaurantService restaurantService;
//
//
//    @InjectMocks
//    OwnerController ownerController;
//
//    @Test
//    void correctlyReturnViewOwner() throws Exception {
//        // given
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
//
//        // when, then
//
//        mockMvc.perform(MockMvcRequestBuilders.get(OwnerController.OWNER))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("owner_portal"));
//    }
}

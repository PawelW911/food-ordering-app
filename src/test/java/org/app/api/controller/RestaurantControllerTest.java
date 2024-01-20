package org.app.api.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {

    @InjectMocks
    AddNewRestaurantController addNewRestaurantController;

//    @Test
//    void correctlyReturnViewCreateNewRestaurant() throws Exception {
//        // given
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
//
//        // when, then
//
//        mockMvc.perform(MockMvcRequestBuilders.get(
//                RestaurantController.RESTAURANT + RestaurantController.ADD_NEW_RESTAURANT
//                ))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("add_new_restaurant"));
//    }
}

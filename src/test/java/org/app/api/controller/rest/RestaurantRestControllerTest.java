package org.app.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.app.api.controller.FoodOrderController;
import org.app.api.controller.OwnerFoodOrderController;
import org.app.api.dto.AvailableRestaurantsDTO;
import org.app.api.dto.RestaurantRestDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.RestaurantService;
import org.app.domain.Restaurant;
import org.app.util.RestaurantDTOFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.app.util.RestaurantFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestaurantRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RunWith(PowerMockRunner.class)
@PrepareForTest(RestaurantRestController.class)
class RestaurantRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private final RestaurantService restaurantService;
    @MockBean
    private final RestaurantDTOMapper restaurantDTOMapper;

    @InjectMocks
    private RestaurantRestController controller;

    @Test
    void checkAvailableRestaurantsDTO() throws Exception {
        // given
        RestaurantRestController spyController = spy(controller);
        RestaurantRestDTO restaurantRestDTO = RestaurantDTOFixtures.someRestaurantRestDTO();
        String responseBody = objectMapper.writeValueAsString(restaurantRestDTO);
        List<RestaurantRestDTO> list = new ArrayList<>();
        list.add(restaurantRestDTO);

        AvailableRestaurantsDTO availableRestaurantsDTO = new AvailableRestaurantsDTO(list);
        System.out.println(availableRestaurantsDTO);

        when(restaurantDTOMapper.mapToRestDTO(any(Restaurant.class))).thenReturn(restaurantRestDTO);


        // when, then

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/available_restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableRestaurants", equalTo(availableRestaurantsDTO)))
//                .andExpect(jsonPath("$.uniqueCode", is(restaurantRestDTO.getUniqueCode())))
//                .andExpect(jsonPath("$.name", is(restaurantRestDTO.getName())))
//                .andExpect(jsonPath("$.typeFood", is(restaurantRestDTO.getTypeFood())))
//                .andExpect(jsonPath("$.email", is(restaurantRestDTO.getEmail())))
//                .andExpect(jsonPath("$.phone", is(restaurantRestDTO.getPhone())))
//                .andExpect(jsonPath("$.openingHours", is(restaurantRestDTO.getOpeningHours())))
//                .andExpect(jsonPath("$.address", is(restaurantRestDTO.getAddress())))
//                .andExpect(jsonPath("$.street", is(restaurantRestDTO.getAddress().getStreet())))
//                .andExpect(jsonPath("$.localNumber", is(restaurantRestDTO.getAddress().getLocalNumber())))
//                .andExpect(jsonPath("$.postalCode", is(restaurantRestDTO.getAddress().getPostalCode())))
//                .andExpect(jsonPath("$.city", is(restaurantRestDTO.getAddress().getCity())))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(responseBody);

    }

}
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
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestaurantRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private final RestaurantService restaurantService;
    @MockBean
    private final RestaurantDTOMapper restaurantDTOMapper;

    @Test
    void checkAvailableRestaurantsDTO() throws Exception {
        // given
        RestaurantRestDTO restaurantRestDTO = RestaurantDTOFixtures.someRestaurantRestDTO();
        List<RestaurantRestDTO> list = new ArrayList<>();
        list.add(restaurantRestDTO);
        AvailableRestaurantsDTO availableRestaurantsDTO = new AvailableRestaurantsDTO(list);
        String responseBody = objectMapper.writeValueAsString(availableRestaurantsDTO);

        when(restaurantDTOMapper.mapToRestDTOList(new ArrayList<>())).thenReturn(availableRestaurantsDTO);
        // when, then

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/available_restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableRestaurants[0].uniqueCode", is(restaurantRestDTO.getUniqueCode())))
                .andExpect(jsonPath("$.availableRestaurants[0].name", is(restaurantRestDTO.getName())))
                .andExpect(jsonPath("$.availableRestaurants[0].typeFood", is(restaurantRestDTO.getTypeFood())))
                .andExpect(jsonPath("$.availableRestaurants[0].email", is(restaurantRestDTO.getEmail())))
                .andExpect(jsonPath("$.availableRestaurants[0].phone", is(restaurantRestDTO.getPhone())))
                .andExpect(jsonPath("$.availableRestaurants[0].openingHours", is(restaurantRestDTO.getOpeningHours())))
                .andExpect(jsonPath("$.availableRestaurants[0].address.street", is(restaurantRestDTO.getAddress().getStreet())))
                .andExpect(jsonPath("$.availableRestaurants[0].address.localNumber", is(restaurantRestDTO.getAddress().getLocalNumber())))
                .andExpect(jsonPath("$.availableRestaurants[0].address.postalCode", is(restaurantRestDTO.getAddress().getPostalCode())))
                .andExpect(jsonPath("$.availableRestaurants[0].address.city", is(restaurantRestDTO.getAddress().getCity())))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(responseBody);

    }

}
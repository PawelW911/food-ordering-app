package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.mapper.MenuMapperDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.MenuService;
import org.app.bussiness.OwnerService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.util.RestaurantDTOFixtures;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = AddNewRestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AddNewRestaurantControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private RestaurantDTOMapper restaurantDTOMapper;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private MenuService menuService;
    @MockBean
    private MenuMapperDTO menuMapperDTO;
    @MockBean
    private RestaurantDTO restaurantDTOFind;

    @Test
    void restaurantAddNewPage_ReturnsCorrectViewAndModelAttribute() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owner/restaurant/add_new_restaurant"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("add_new_restaurant"))
                .andExpect(model().attributeExists("restaurantDTO"))
                .andExpect(model().attribute("restaurantDTO", new RestaurantDTO()));
    }

    @Test
    void addRestaurantReturnsCorrectViewAndModelAttribute() throws Exception {
        // given
        RestaurantDTO restaurantDTO = RestaurantDTOFixtures.someRestaurantDTO();

        Restaurant restaurant = RestaurantFixtures.someRestaurant1();
        when(restaurantService.saveNewRestaurant(restaurantDTOMapper.mapFromDTO(
                restaurantDTO, restaurant.getOwner(), restaurant.getUniqueCode())))
                .thenReturn(restaurant);
        
        RestaurantDTO restaurantDTOFind = Mockito.mock(RestaurantDTO.class);
        String expectedUniqueCode = restaurantDTO.getUniqueCode();
        when(restaurantDTOFind.getUniqueCode()).thenReturn(expectedUniqueCode);
        String expectedRestaurantName = restaurantDTO.getRestaurantName();
        when(restaurantDTOFind.getRestaurantName()).thenReturn(expectedRestaurantName);
        String expectedRestaurantTypeFood = restaurantDTO.getRestaurantTypeFood();
        when(restaurantDTOFind.getRestaurantTypeFood()).thenReturn(expectedRestaurantTypeFood);
        String expectedRestaurantEmail = restaurantDTO.getRestaurantEmail();
        when(restaurantDTOFind.getRestaurantEmail()).thenReturn(expectedRestaurantEmail);
        String expectedRestaurantPhone = restaurantDTO.getRestaurantPhone();
        when(restaurantDTOFind.getRestaurantPhone()).thenReturn(expectedRestaurantPhone);
        String expectedRestaurantOpeningHours = restaurantDTO.getRestaurantOpeningHours();
        when(restaurantDTOFind.getRestaurantOpeningHours()).thenReturn(expectedRestaurantOpeningHours);
        String expectedRestaurantAddressStreet = restaurantDTO.getRestaurantAddressStreet();
        when(restaurantDTOFind.getRestaurantAddressStreet()).thenReturn(expectedRestaurantAddressStreet);
        String expectedRestaurantAddressLocalNumber = restaurantDTO.getRestaurantAddressLocalNumber();
        when(restaurantDTOFind.getRestaurantAddressLocalNumber()).thenReturn(expectedRestaurantAddressLocalNumber);
        String expectedRestaurantAddressPostalCode = restaurantDTO.getRestaurantAddressPostalCode();
        when(restaurantDTOFind.getRestaurantAddressPostalCode()).thenReturn(expectedRestaurantAddressPostalCode);
        String expectedRestaurantAddressCity = restaurantDTO.getRestaurantAddressCity();
        when(restaurantDTOFind.getRestaurantAddressCity()).thenReturn(expectedRestaurantAddressCity);

        when(restaurantDTOMapper.mapToDTO(any()))
                .thenReturn(restaurantDTOFind);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/restaurant/add_new_restaurant_submit")
                        .flashAttr("restaurantDTO", restaurantDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("add_restaurant_done"))
                .andExpect(model().attribute("restaurantUniqueCode", expectedUniqueCode))
                .andExpect(model().attribute("restaurantName", expectedRestaurantName))
                .andExpect(model().attribute("restaurantTypeFood", expectedRestaurantTypeFood))
                .andExpect(model().attribute("restaurantEmail", expectedRestaurantEmail))
                .andExpect(model().attribute("restaurantPhone", expectedRestaurantPhone))
                .andExpect(model().attribute("restaurantOpeningHours", expectedRestaurantOpeningHours))
                .andExpect(model().attribute("restaurantAddressStreet", expectedRestaurantAddressStreet))
                .andExpect(model().attribute("restaurantAddressLocalNumber", expectedRestaurantAddressLocalNumber))
                .andExpect(model().attribute("restaurantAddressPostalCode", expectedRestaurantAddressPostalCode))
                .andExpect(model().attribute("restaurantAddressCity", expectedRestaurantAddressCity));
    }
}

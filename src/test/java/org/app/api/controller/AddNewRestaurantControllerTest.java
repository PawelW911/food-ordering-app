package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.mapper.MenuMapperDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.MenuService;
import org.app.bussiness.OwnerService;
import org.app.bussiness.RestaurantService;
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

//        RestaurantDTO restaurantDTOFind = RestaurantDTOFixtures.someRestaurantDTO();
        when(restaurantDTOMapper.mapToDTO(restaurant))
                .thenReturn(restaurantDTO);
        when(restaurantDTOFind.getUniqueCode()).thenReturn(restaurantDTO.getUniqueCode());

        // Mockowanie obiektu RestaurantDTO
        RestaurantDTO restaurantDTOFind = Mockito.mock(RestaurantDTO.class);
        String expectedUniqueCode = "someUniqueCode";
        when(restaurantDTOFind.getUniqueCode()).thenReturn(expectedUniqueCode);

        // Konfiguracja zachowania mocka restaurantDTOMapper
        when(restaurantDTOMapper.mapToDTO(any()))
                .thenReturn(restaurantDTOFind);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/restaurant/add_new_restaurant_submit")
                        .flashAttr("restaurantDTO", restaurantDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("add_restaurant_done"))
                .andExpect(model().attribute("restaurantUniqueCode", expectedUniqueCode))
                .andExpect(model().attributeExists("restaurantName"))
                .andExpect(model().attributeExists("restaurantTypeFood"))
                .andExpect(model().attributeExists("restaurantEmail"))
                .andExpect(model().attributeExists("restaurantPhone"))
                .andExpect(model().attributeExists("restaurantOpeningHours"))
                .andExpect(model().attributeExists("restaurantAddressStreet"))
                .andExpect(model().attributeExists("restaurantAddressLocalNumber"))
                .andExpect(model().attributeExists("restaurantAddressPostalCode"))
                .andExpect(model().attributeExists("restaurantAddressCity"))
                .andExpect(model().attributeExists("menuDTO"));
    }
}

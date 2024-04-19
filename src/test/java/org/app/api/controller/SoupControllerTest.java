package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.DrinkDTO;
import org.app.api.dto.SoupDTO;
import org.app.api.dto.mapper.SoupMapperDTO;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.bussiness.SoupService;
import org.app.domain.Drink;
import org.app.domain.Soup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.app.util.DrinkFixtures.someDrinksForPolishFood;
import static org.app.util.SoupFixtures.someSoupsForPolishFood;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = SoupController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class SoupControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SoupService soupService;
    @MockBean
    private SoupMapperDTO soupMapperDTO;
    @MockBean
    private MenuService menuService;
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testAddSoup() throws Exception {
        // Given
        SoupDTO soupDTO = new SoupDTO();

        when(soupService.saveNewSoup(any(Soup.class)))
                .thenReturn(someSoupsForPolishFood().stream().toList().get(0));

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/add_soup")
                        .flashAttr("soupDTO", soupDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }

    @Test
    public void testDeleteSoup() throws Exception {
        // Given
        SoupDTO soupDTO = new SoupDTO();

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/owner/delete_soup")
                        .flashAttr("soupDTO", soupDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }
}
package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.MenuPosition;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ManageRestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ManageRestaurantControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MenuPosition menuPosition;

    @InjectMocks
    private ManageRestaurantController controller;

    @Test
    void checkManageRestaurantPage() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/owner/restaurant/manage"))
                .andExpect(status().isOk())
                .andExpect(view().name("manage_restaurant"));
    }

    @Test
    void checkManageRestaurantManageMenuPage() throws Exception {
        // given
        when(menuPosition.prepareMenuPositions(anyString())).thenReturn(Map.of());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/owner/restaurant/manage/menu"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("appetizerDTO"))
                .andExpect(model().attributeExists("soupDTO"))
                .andExpect(model().attributeExists("mainMealDTO"))
                .andExpect(model().attributeExists("desertDTO"))
                .andExpect(model().attributeExists("drinkDTO"))
                .andExpect(model().attributeExists("menuDTO"))
                .andExpect(view().name("manage_restaurant_menu"));
    }

}
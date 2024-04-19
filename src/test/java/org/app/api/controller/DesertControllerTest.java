package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.DesertDTO;
import org.app.api.dto.mapper.DesertMapperDTO;
import org.app.bussiness.DesertService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Desert;
import org.app.util.DesertFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.app.util.DesertFixtures.someDesertsForPolishFood;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = DesertController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DesertControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private DesertService desertService;

    @MockBean
    private DesertMapperDTO desertMapperDTO;

    @MockBean
    private MenuService menuService;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testAddDesert() throws Exception {
        // Given
        DesertDTO desertDTO = new DesertDTO();

        when(desertService.saveNewDesert(any(Desert.class)))
                .thenReturn(someDesertsForPolishFood().stream().toList().get(0));

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/add_desert")
                        .flashAttr("desertDTO", desertDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }

    @Test
    public void testDeleteDesert() throws Exception {
        // Given
        DesertDTO desertDTO = new DesertDTO();

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/owner/delete_desert")
                        .flashAttr("desertDTO", desertDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }
}
package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.mapper.AppetizerMapperDTO;
import org.app.bussiness.AppetizerService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppetizerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AppetizerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private AppetizerService appetizerService;

    @MockBean
    private AppetizerMapperDTO appetizerMapperDTO;

    @MockBean
    private MenuService menuService;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void addAppetizerToMenuReturnsRedirect() throws Exception {
        // given
        doReturn(null).when(appetizerService).saveNewAppetizer(any());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/add_appetizer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Test Appetizer\", \"price\": 10.5 }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteAppetizerReturnsRedirect() throws Exception {
        // given
        doNothing().when(appetizerService).deleteAppetizer(1);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/owner/delete_appetizer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"appetizerId\": 1 }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }
}
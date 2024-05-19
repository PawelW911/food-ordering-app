package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.MenuDTO;
import org.app.api.dto.mapper.MenuMapperDTO;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.app.util.RestaurantFixtures.someRestaurant1;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MenuControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;
    @MockBean
    private MenuMapperDTO menuMapperDTO;
    @MockBean
    private RestaurantService restaurantService;

    @InjectMocks
    private MenuController controller;

    @Test
    void checkUpdateMenu() throws Exception {
        // given
        when(restaurantService.findByUniqueCode(anyString())).thenReturn(someRestaurant1());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/owner/update_menu")
                .flashAttr("menuDTO", new MenuDTO()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owner/restaurant/manage/menu"));
    }
}
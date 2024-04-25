package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.VariableDTO;
import org.app.bussiness.FoodOrderService;
import org.app.util.FoodOrderFixtures;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.app.util.FoodOrderFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OwnerFoodOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerFoodOrderControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FoodOrderService foodOrderService;

    @InjectMocks
    private OwnerFoodOrderController controller;

    @Test
    void checkListFoodOrderToManage() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/owner/manage/food_order_owner"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("variableDTO"))
                .andExpect(view().name("manage_food_orders_by_owner"));
    }

    @Test
    void checkChooseFoodOrderToManage() throws Exception {
        // given
        VariableDTO variableDTO = new VariableDTO();
        variableDTO.setFoodOrderNumber("00000");
        Mockito.when(foodOrderService.findByFoodOrderNumber(anyString(), anyString())).thenReturn(someFoodOrder1());
        Mockito.when(foodOrderService.findByFoodOrderNumber(anyString(), anyString())).thenReturn(someFoodOrder2());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/choose_order_to_manage")
                .flashAttr("variableDTO", variableDTO))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("detailsOrder"))
                .andExpect(view().name("manage_food_order"));
    }

    @Test
    void checkChooseToCompleteOrder() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/owner/choose_order_to_complete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owner/manage/food_order_owner"));
    }
}
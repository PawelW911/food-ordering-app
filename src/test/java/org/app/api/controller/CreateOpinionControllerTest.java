package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.OpinionDTO;
import org.app.api.dto.VariableDTO;
import org.app.bussiness.CustomerService;
import org.app.bussiness.FoodOrderService;
import org.app.bussiness.OpinionService;
import org.app.domain.Opinion;
import org.app.util.FoodOrderFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CreateOpinionController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CreateOpinionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OpinionService opinionService;

    @MockBean
    private FoodOrderService foodOrderService;

    @MockBean
    private CustomerService customerService;


    @Test
    void opinionPage_ReturnsCreateOpinionPage() throws Exception {
        // Given
        VariableDTO variableDTO = new VariableDTO();
        variableDTO.setFoodOrderNumber("testFoodOrderNumber");

        // When, Then
        mockMvc.perform(get("/customer/add_opinion")
                        .param("foodOrderNumber", "testFoodOrderNumber"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("opinionDTO"))
                .andExpect(view().name("create_opinion"));
    }

    @Test
    void submitOpinion_SavesOpinionAndReturnsSuccessPage() throws Exception {
        // Given
        OpinionDTO opinionDTO = new OpinionDTO();
        opinionDTO.setOpinionText("Test opinion text");
        opinionDTO.setOpinionStars(5);

        when(foodOrderService.findByFoodOrderNumber(anyString(), anyString()))
                .thenReturn(FoodOrderFixtures.someFoodOrder1());

        // When, Then
        mockMvc.perform(post("/customer/submit_opinion")
                        .flashAttr("opinionDTO", opinionDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("add_opinion_success"));

        verify(opinionService, times(1)).saveNewOpinion(any(Opinion.class));
    }

}
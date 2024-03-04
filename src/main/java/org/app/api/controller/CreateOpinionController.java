package org.app.api.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.app.api.controller.dataForController.ForFoodOrderChoose;
import org.app.api.dto.OpinionDTO;
import org.app.api.dto.VariableDTO;
import org.app.bussiness.CustomerService;
import org.app.bussiness.FoodOrderService;
import org.app.bussiness.OpinionService;
import org.app.domain.FoodOrder;
import org.app.domain.Opinion;
import org.app.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.app.api.controller.CustomerController.*;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class CreateOpinionController {

    public static final String ADD_OPINION = "/add_opinion";
    public static final String SUBMIT_OPINION = "/submit_opinion";

    private OpinionService opinionService;
    private FoodOrderService foodOrderService;
    private CustomerService customerService;

    private static String foodOrderNumber;

    @GetMapping(value = CUSTOMER + ADD_OPINION)
    public String opinionPage(
            Model model,
            @Valid @ModelAttribute("variableDTO") VariableDTO variableDTO
            ) {
        foodOrderNumber = variableDTO.getFoodOrderNumber();
        model.addAttribute("opinionDTO", new OpinionDTO());
        return "create_opinion";
    }

    @PostMapping(value = CUSTOMER + SUBMIT_OPINION)
    public String submitOpinion(
            @Valid @ModelAttribute("opinionDTO") OpinionDTO opinionDTO
    ) {
        FoodOrder byFoodOrderNumber = foodOrderService.findByFoodOrderNumber(
                foodOrderNumber,
                ForFoodOrderChoose.MAP_WITHOUT_SET_DISHES.toString());

        opinionService.saveNewOpinion(Opinion.builder()
                        .text(opinionDTO.getOpinionText())
                        .stars(opinionDTO.getOpinionStars())
                        .dateTime(OffsetDateTime.now(ZoneOffset.UTC))
                        .customer(customerService.findCustomerByEmail(emailCustomer))
                        .restaurant(byFoodOrderNumber.getRestaurant())
                .build());
        return "add_opinion_success";
    }
}

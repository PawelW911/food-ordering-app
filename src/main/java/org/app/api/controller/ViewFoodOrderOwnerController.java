package org.app.api.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.app.api.controller.OwnerController.*;

@Controller
@AllArgsConstructor
//@NoArgsConstructor
public class ViewFoodOrderOwnerController {

    public static final String VIEW_FOOD_ORDER_OWNER = "/view_food_order_owner";

    @GetMapping(value = OWNER + VIEW_FOOD_ORDER_OWNER)
    public String viewFoodOrderOwner(Model model) {
        return "";
    }
}

package org.app.api.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class HomeController {

    static final String HOME ="/";

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }
}

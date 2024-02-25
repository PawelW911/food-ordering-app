package org.app.api.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.app.security.RoleEntity;
import org.app.security.RoleRepository;
import org.app.security.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    RoleRepository roleRepository;
    static final String HOME ="/";

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String homePage() {
        List<RoleEntity> availableRole = roleRepository.findAll();
        if (availableRole.size() < 2) {
            roleRepository.save(RoleEntity.builder()
                            .id(1)
                            .role("OWNER")
                    .build());
            roleRepository.save(RoleEntity.builder()
                            .id(2)
                            .role("CUSTOMER")
                    .build());
        }

        return "home";
    }
}

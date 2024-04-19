package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.UserDTO;
import org.app.bussiness.CustomerService;
import org.app.bussiness.OwnerService;
import org.app.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CreateUserOwnerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CreateUserOwnerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @InjectMocks
    private CreateUserOwnerController controller;

    @Test
    public void testCreateUserPage() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // when, then
        mockMvc.perform(get("/create_user_owner"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attribute("userDTO", new UserDTO()))
                .andExpect(MockMvcResultMatchers.view().name("create_user_owner_page"));
    }

    @Test
    public void testSubmitUser() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPhone("+48 654 284 654");
        userDTO.setUserName("johndoe");
        userDTO.setPassword("password");
        userDTO.setStreet("123 Street");
        userDTO.setLocalNumber("45");
        userDTO.setPostalCode("00-555");
        userDTO.setCity("City");


        Owner expectedOwner = Owner.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .phone("+48 654 284 654")
                .user(User.builder()
                        .userName("johndoe")
                        .email("john.doe@example.com")
                        .password("password")
                        .active(true)
                        .roles(Set.of(Role.builder().id(1).role("OWNER").build()))
                        .build())
                .build();

        doReturn(expectedOwner).when(ownerService).saveNewOwner(any(Owner.class));

        // when, then

        mockMvc.perform(post("/submit_user_owner")
                        .flashAttr("userDTO", userDTO))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(MockMvcResultMatchers.view().name("create_user_success"));

        verify(ownerService).saveNewOwner(expectedOwner);
    }
}
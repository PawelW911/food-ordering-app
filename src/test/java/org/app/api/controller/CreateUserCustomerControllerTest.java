package org.app.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.app.api.dto.UserDTO;
import org.app.bussiness.CustomerService;
import org.app.domain.Address;
import org.app.domain.Customer;
import org.app.domain.Role;
import org.app.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CreateUserCustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CreateUserCustomerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CreateUserCustomerController controller;

    @Test
    public void testCreateUserPage() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // when, then
        mockMvc.perform(get("/create_user_customer"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attribute("userDTO", new UserDTO()))
                .andExpect(MockMvcResultMatchers.view().name("create_user_customer_page"));
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
        

        Customer expectedCustomer = Customer.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .phone("+48 654 284 654")
                .user(User.builder()
                        .userName("johndoe")
                        .email("john.doe@example.com")
                        .password("password")
                        .active(true)
                        .roles(Set.of(Role.builder().id(2).role("CUSTOMER").build()))
                        .build())
                .address(Address.builder()
                        .street("123 Street")
                        .localNumber("45")
                        .postalCode("00-555")
                        .city("City")
                        .build())
                .build();

        doReturn(expectedCustomer).when(customerService).saveNewCustomer(any(Customer.class));

        // when, then

        mockMvc.perform(post("/submit_user_customer")
                        .flashAttr("userDTO", userDTO))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDTO"))
                        .andExpect(MockMvcResultMatchers.view().name("create_user_success"));

        verify(customerService).saveNewCustomer(expectedCustomer);
    }

}
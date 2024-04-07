package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.OwnerDTO;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.mapper.OwnerDTOMapper;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.OwnerService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Owner;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.infrastructure.zipCode.ZipCodeImpl;
import org.app.security.UserEntity;
import org.app.security.UserRepository;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OwnerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OwnerDTOMapper ownerDTOMapper;

    @MockBean
    private RestaurantDTOMapper restaurantDTOMapper;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private UserRepository userRepository;


    @InjectMocks
    OwnerController ownerController;

    @Test
    void correctlyReturnViewOwner() throws Exception {
        // given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setEmail("test@example.com");
        when(userRepository.findByUserName(anyString())).thenReturn(mockUserEntity);
        // when, then

        mockMvc.perform(MockMvcRequestBuilders.get(OwnerController.OWNER))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("availableRestaurantDTOs"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("variableDTO"))
                .andExpect(MockMvcResultMatchers.view().name("owner_portal"));
    }

    @Test
    void chooseRestaurantToManageValidInputRedirectsToManagePage() throws Exception {
        String uniqueCode = "exampleCode";

        mockMvc.perform(MockMvcRequestBuilders.post("/owner/choose_restaurant")
                        .param("uniqueCode", uniqueCode))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage"));
    }

//    @Test
//    void chooseRestaurantToManageInvalidInputThrowsException() throws Exception {
//        String uniqueCode = "";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/owner/choose_restaurant")
//                        .param("uniqueCode", uniqueCode))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
}

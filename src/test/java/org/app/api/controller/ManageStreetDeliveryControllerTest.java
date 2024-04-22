package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.PostalCodeDTO;
import org.app.bussiness.StreetDeliveryService;
import org.app.bussiness.dao.ZipCodeDAO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ManageStreetDeliveryController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RunWith(PowerMockRunner.class)
@PrepareForTest(ManageStreetDeliveryController.class)
class ManageStreetDeliveryControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StreetDeliveryService streetDeliveryService;
    @MockBean
    private ZipCodeDAO zipCodeDAO;

    @InjectMocks
    private ManageStreetDeliveryController controller;

    @Test
    void checkManageStreetDeliveryPage() throws Exception {
        // given
        ManageStreetDeliveryController spyController = spy(controller);

        doReturn(Map.of("deliveryPostalCodeDTOs", Set.of("00-543", "02-234")))
                .when(spyController).preparePostalCode();
        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/owner/manage_street_delivery"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("postalCodeDTO"))
                .andExpect(view().name("street_delivery_manage"));
    }

    @Test
    void checkAddStreetDeliveryByPostalCode() throws Exception {
        // given
        PostalCodeDTO postalCodeDTO = new PostalCodeDTO("00-543");

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/add_street_delivery")
                .flashAttr("postalCodeDTO", postalCodeDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owner/manage_street_delivery"));
    }
}
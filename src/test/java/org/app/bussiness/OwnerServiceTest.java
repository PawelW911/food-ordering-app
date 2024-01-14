package org.app.bussiness;

import org.app.business.OwnerService;
import org.app.business.dao.OwnerDAO;
import org.app.domain.Owner;
import org.app.util.OwnerFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerDAO ownerDAO;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    void checkSaveNewOwner() {
        // given
        Owner ownerExample = OwnerFixtures.someOwner1();
        Mockito.when(ownerDAO.saveOwner(Mockito.any(Owner.class))).thenReturn(ownerExample);

        // when
        Owner owner = ownerService.saveNewOwner(ownerExample);

        // then
        Assertions.assertNotNull(owner);

    }
}

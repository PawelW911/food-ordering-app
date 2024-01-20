package org.app.bussiness;

import org.app.bussiness.dao.OwnerDAO;
import org.app.domain.Owner;
import org.app.util.OwnerFixtures;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void checkFindOwnerByEmail() {
        // given
        String email = OwnerFixtures.someOwner2().getEmail();

        Mockito.when(ownerDAO.findByEmail(Mockito.anyString())).thenReturn(OwnerFixtures.someOwner2());

        // when
        Owner owner = ownerService.findByEmail(email);

        // then
        Assertions.assertNotNull(owner);
    }
}

package org.app.bussiness;

import org.app.bussiness.dao.SoupDAO;
import org.app.domain.Soup;
import org.app.util.SoupFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SoupServiceTest {

    @Mock
    private SoupDAO soupDAO;

    @InjectMocks
    private SoupService soupService;

    @Test
    void checkSaveNewSoups() {
        // given
        List<Soup> soupsExample = SoupFixtures.someSoupsForPolishFood().stream().toList();

        Mockito.when(soupDAO.saveSoups(Mockito.anyList())).thenReturn(soupsExample);

        // when
        List<Soup> soups = soupService.saveNewSoups(soupsExample);

        // then
        Assertions.assertEquals(soupsExample.size(), soups.size());
    }
}

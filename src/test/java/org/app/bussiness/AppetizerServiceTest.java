package org.app.bussiness;

import org.app.bussiness.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.app.util.AppetizerFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AppetizerServiceTest {

    @Mock
    private AppetizerDAO appetizerDAO;

    @InjectMocks
    private AppetizerService appetizerService;

    @Test
    void checkSaveNewAppetizers() {
        // given
        List<Appetizer> appetizersExample = AppetizerFixtures.someAppetizersForPolishFood().stream().toList();

        Mockito.when(appetizerDAO.saveAppetizers(Mockito.anyList())).thenReturn(appetizersExample);

        // when
        List<Appetizer> appetizers = appetizerService.saveNewAppetizers(appetizersExample);

        // then
        Assertions.assertEquals(appetizersExample.size(), appetizers.size());
    }

}

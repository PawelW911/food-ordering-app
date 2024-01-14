package org.app.bussiness;

import org.app.business.dao.DesertDAO;
import org.app.domain.Desert;
import org.app.util.DesertFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DesertServiceTest {

    @Mock
    private DesertDAO desertDAO;

    @InjectMocks
    private DesertService desertService;

    @Test
    void checkSaveNewDeserts() {
        // given
        List<Desert> desertsExample = DesertFixtures.someDesertsForPolishFood().stream().toList();

        Mockito.when(desertDAO.saveDeserts(Mockito.anyList())).thenReturn(desertsExample);

        // when
        List<Desert> deserts = desertService.saveNewDeserts(desertsExample);

        //
        Assertions.assertEquals(desertsExample.size(), deserts.size());

    }
}

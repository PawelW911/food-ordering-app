package org.app.bussiness;

import org.app.bussiness.dao.OpinionDAO;
import org.app.domain.Opinion;
import org.app.util.OpinionFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OpinionServiceTest {

    @Mock
    private OpinionDAO opinionDAO;

    @InjectMocks
    private OpinionService opinionService;

    @Test
    void checkSaveNewOpinion() {
        // given
        Opinion opinionExample = OpinionFixtures.someOpinion1();

        Mockito.when(opinionDAO.saveOpinion(Mockito.any(Opinion.class))).thenReturn(opinionExample);

        // when
        Opinion opinion = opinionService.saveNewOpinion(opinionExample);

        // then
        Assertions.assertNotNull(opinion);
    }
}

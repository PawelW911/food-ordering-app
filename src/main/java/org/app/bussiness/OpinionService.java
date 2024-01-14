package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.business.dao.OpinionDAO;
import org.app.domain.Opinion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OpinionService {

    private final OpinionDAO opinionDAO;

    @Transactional
    public Opinion saveNewOpinion(Opinion opinion) {
        return opinionDAO.saveOpinion(opinion);
    }
}

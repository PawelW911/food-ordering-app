package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.OpinionDAO;
import org.app.domain.Opinion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OpinionService {

    private final OpinionDAO opinionDAO;

    @Transactional
    public Opinion saveNewOpinion(Opinion opinion) {
        return opinionDAO.saveOpinion(opinion);
    }

    public List<Opinion> findOpinionByRestaurant(String uniqueCode) {
        return opinionDAO.findByRestaurant(uniqueCode);
    }
}

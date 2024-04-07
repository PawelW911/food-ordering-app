package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.OpinionDAO;
import org.app.domain.Opinion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OpinionService {

    private final OpinionDAO opinionDAO;

    @Transactional
    public Opinion saveNewOpinion(Opinion opinion) {
        Opinion savedOpinion = opinionDAO.saveOpinion(opinion);
        if (savedOpinion == null) {
            log.error("The attempt to save the opinion wasn't a success");
        } else {
            log.info("Opinion save a success, id: [{}]", opinion.getOpinionId());
        }
        return savedOpinion;
    }

    public List<Opinion> findOpinionByRestaurant(String uniqueCode) {
        List<Opinion> opinions = opinionDAO.findByRestaurant(uniqueCode);
        log.info("Restaurant with unique code: [{}], have opinions: [{}]", uniqueCode, opinions.size());
        return opinions;
    }
}

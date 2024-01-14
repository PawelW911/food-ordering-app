package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.business.dao.DesertDAO;
import org.app.domain.Desert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DesertService {

    private final DesertDAO desertDAO;

    @Transactional
    public List<Desert> saveNewDeserts(List<Desert> deserts) {
        return desertDAO.saveDeserts(deserts);
    }
}

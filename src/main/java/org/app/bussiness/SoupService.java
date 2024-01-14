package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.business.dao.SoupDAO;
import org.app.domain.Soup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SoupService {

    private final SoupDAO soupDAO;

    @Transactional
    public List<Soup> saveNewSoups(List<Soup> soups) {
        return soupDAO.saveSoups(soups);
    }
}

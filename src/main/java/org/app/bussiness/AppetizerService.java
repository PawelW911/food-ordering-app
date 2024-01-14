package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.business.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AppetizerService {

    private final AppetizerDAO appetizerDAO;

    @Transactional
    public List<Appetizer> saveNewAppetizers(List<Appetizer> appetizers) {
        return appetizerDAO.saveAppetizers(appetizers);
    }
}

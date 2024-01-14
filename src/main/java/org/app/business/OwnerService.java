package org.app.business;

import lombok.AllArgsConstructor;
import org.app.business.dao.OwnerDAO;
import org.app.domain.Owner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;

    @Transactional
    public Owner saveNewOwner(Owner owner) {
        return ownerDAO.saveOwner(owner);
    }
}

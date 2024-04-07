package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.OwnerDAO;
import org.app.domain.Owner;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;

    @Transactional
    public Owner saveNewOwner(Owner owner) {
        Owner savedOwner = ownerDAO.saveOwner(owner);
        if (savedOwner == null) {
            log.error("The attempt to save the owner wasn't a success");
        } else {
            log.info("Owner save a success, email: [{}]", savedOwner.getEmail());
        }
        return savedOwner;
    }

    public Owner findByEmail(String email) {
        Owner owner = ownerDAO.findByEmail(email);
        if (owner == null) {
            throw new NotFoundException("Owner with email: [%s] does not exist.".formatted(email));
        } else {
            log.info("Owner with email: [{}] is found", email);
        }
        return owner;
    }
}

package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.DesertDAO;
import org.app.domain.*;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class DesertService {

    private final DesertDAO desertDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<Desert> saveNewDeserts(List<Desert> deserts) {
        List<Desert> saveDeserts = desertDAO.saveDeserts(deserts);
        if (saveDeserts.size() == deserts.size()) {
            log.info("[{}] deserts save a success", deserts.size());
        } else {
            log.error("[{}] the attempt to save the deserts wasn't a success", deserts.size());
        }
        return saveDeserts;
    }

    @Transactional
    public Desert saveNewDesert(Desert desert) {
        Desert savedDesert = desertDAO.saveDesert(desert);
        if (savedDesert == null) {
            log.error("The attempt to save the desert wasn't a success");
        } else {
            log.info("Save desert a success, id: [{}]", savedDesert.getDesertId());
        }
        return savedDesert;
    }


    public Set<Desert> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        Set<Desert> deserts = desertDAO.findAvailableByMenu(menu);
        log.info("Available deserts in restaurant [{}] is [{}]", restaurant, deserts.size());
        return deserts;
    }

    @Transactional
    public void deleteDesert(Integer desertId) {
        desertDAO.deleteDesert(desertId);
        if (desertDAO.findById(desertId) == null) {
            log.info("Desert with id: [{}] delete is success.", desertId);
        } else {
            log.error("Desert with id: [{}] is didn't successfully.", desertId);
        }
    }

    public Desert findById(Integer desertId) {
        Desert desert = desertDAO.findById(desertId);
        if (desert == null) {
            throw new NotFoundException("Desert with id: [%s] does not exist.".formatted(desertId));
        } else {
            log.info("Desert with id: [{}] is found", desertId);
        }
        return desert;
    }

    @Transactional
    public Desert updateQuantityDesert(Integer desertId, Integer quantity) {
        Desert desert = desertDAO.updateQuantityDesert(desertId, quantity);
        if (!(desert == null)) {
            if (Objects.equals(desert.getQuantity(), quantity)) {
                log.info("Update quantity: [{}], in desert with id: [{}]", quantity, desertId);
            } else {
                log.error("Quantity: [{}], has not been updated, in desert with id: [{}]", quantity, desertId);
            }
        } else {
            log.info("Desert with id: [{}] is delete", desertId);
        }
        return desert;
    }
}

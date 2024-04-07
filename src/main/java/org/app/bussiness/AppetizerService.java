package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AppetizerService {

    private final AppetizerDAO appetizerDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<Appetizer> saveNewAppetizers(List<Appetizer> appetizers) {
        List<Appetizer> savedAppetizers = appetizerDAO.saveAppetizers(appetizers);
        if (savedAppetizers.size() == appetizers.size()) {
            log.info("[{}] appetizers save a success", appetizers.size());
        } else {
            log.error("[{}] the attempt to save the appetizers wasn't a success", appetizers.size());
        }
        return savedAppetizers;
    }

    @Transactional
    public Appetizer saveNewAppetizer(Appetizer appetizer) {
        Appetizer savedAppetizer = appetizerDAO.saveAppetizer(appetizer);
        if (savedAppetizer == null) {
            log.error("The attempt to save the appetizer wasn't a success");
        } else {
            log.info("Save appetizer a success, id: [{}]", savedAppetizer.getAppetizerId());
        }
        return savedAppetizer;
    }

    public Set<Appetizer> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        Set<Appetizer> appetizers = appetizerDAO.findAvailableByMenu(menu);
        log.info("Available appetizers in restaurant [{}] is [{}]", restaurant, appetizers.size());
        return appetizers;
    }

    @Transactional
    public void deleteAppetizer(Integer appetizerId) {
        appetizerDAO.deleteAppetizer(appetizerId);
        if (appetizerDAO.findById(appetizerId) == null) {
            log.info("Appetizer with id: [{}] delete is success.", appetizerId);
        } else {
            log.error("Appetizer with id: [{}] is didn't successfully.", appetizerId);
        }
    }

    public Appetizer findById(Integer appetizerId) {
        Appetizer appetizer = appetizerDAO.findById(appetizerId);
        if (appetizer == null) {
            throw new NotFoundException("Appetizer with id: [%s] does not exist.".formatted(appetizerId));
        } else {
            log.info("Appetizer with id: [{}] is found", appetizerId);
        }
        return appetizer;
    }

    @Transactional
    public Appetizer updateQuantityAppetizer(Integer appetizerId, Integer quantity) {
        Appetizer appetizer = appetizerDAO.updateQuantityAppetizer(appetizerId, quantity);
        if (!(appetizer == null)) {
            if (Objects.equals(appetizer.getQuantity(), quantity)) {
                log.info("Update quantity: [{}], in appetizer with id: [{}]", quantity, appetizerId);
            } else {
                log.error("Quantity: [{}], has not been updated, in appetizer with id: [{}]", quantity, appetizerId);
            }
        } else {
            log.info("Appetizer with id: [{}] is delete", appetizerId);
        }
        return appetizer;
    }
}

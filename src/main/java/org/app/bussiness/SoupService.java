package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.SoupDAO;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.Soup;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class SoupService {

    private final SoupDAO soupDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<Soup> saveNewSoups(List<Soup> soups) {
        List<Soup> soupList = soupDAO.saveSoups(soups);
        if (soupList.size() == soups.size()) {
            log.info("[{}] soups save a success", soups.size());
        } else {
            log.error("[{}] the attempt to save the soups wasn't a success", soups.size());
        }
        return soupList;
    }

    @Transactional
    public Soup saveNewSoup(Soup soup) {
        Soup soupSaved = soupDAO.saveSoup(soup);
        if (soupSaved == null) {
            log.error("The attempt to save the soup wasn't a success");
        } else {
            log.info("Soup save a success, id: [{}]", soup.getSoupId());
        }
        return soupSaved;
    }


    public Set<Soup> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        Set<Soup> soups = soupDAO.findAvailableByMenu(menu);
        log.info("Available soup in restaurant [{}] is [{}]", restaurant, soups.size());
        return soups;
    }

    @Transactional
    public void deleteSoup(Integer soupId) {
        soupDAO.deleteSoup(soupId);
        if (soupDAO.findById(soupId) == null) {
            log.info("Soup with id: [{}] delete is success.", soupId);
        } else {
            log.error("Soup with id: [{}] is didn't successfully.", soupId);
        }
    }

    public Soup findById(Integer soupId) {
        Soup soup = soupDAO.findById(soupId);
        if (soup == null) {
            throw new NotFoundException("Soup with id: [%s] does not exist.".formatted(soupId));
        } else {
            log.info("Soup with id: [{}] is found", soupId);
        }
        return soup;
    }

    @Transactional
    public Soup updateQuantitySoup(Integer soupId, Integer quantity) {
        Soup soup = soupDAO.updateQuantitySoup(soupId, quantity);
        if (!(soup == null)) {
            if (Objects.equals(soup.getQuantity(), quantity)) {
                log.info("Update quantity: [{}], in soup with id: [{}]", quantity, soupId);
            } else {
                log.error("Quantity: [{}], has not been updated, in soup with id: [{}]", quantity, soupId);
            }
        } else {
            log.info("Soup with id: [{}] is delete", soupId);
        }
        return soup;
    }
}

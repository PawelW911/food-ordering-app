package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.MenuDAO;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class MenuService {

    private final MenuDAO menuDAO;

    @Transactional
    public Menu saveNewMenu(Menu menu) {
        Menu savedMenu = menuDAO.saveMenu(menu);
        if (savedMenu == null) {
            log.error("The attempt to save the menu wasn't a success");
        } else {
            log.info("Menu save a success, id: [{}]", menu.getMenuId());
        }
        return savedMenu;

    }

    public Menu findByRestaurant(Restaurant restaurant) {
        Menu menu = menuDAO.findByRestaurant(restaurant);
        if (menu == null) {
            throw new NotFoundException("Menu with restaurant:(unique code) [%s], does not exist.".formatted(restaurant.getUniqueCode()));
        } else {
            log.info("Menu with restaurant:(unique code) [{}], is found", restaurant.getUniqueCode());
        }
        return menu;

    }

    @Transactional
    public Menu updateMenu(Menu menuToUpdate) {
        Menu menu = menuDAO.updateMenu(menuToUpdate);
        if (
                !(menu == null) &&
                        menuToUpdate.getName().equals(menu.getName()) &&
                        menuToUpdate.getDescription().equals(menu.getDescription()
                        )) {
            log.info("Update menu with id: [{}], menu is succes", menu.getMenuId());
        } else {
            log.error("Update menu with id: [{}], doesn't successfully", menuToUpdate.getMenuId());
        }
        return menu;
    }


}

package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.MenuDAO;
import org.app.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuDAO menuDAO;
    private final AppetizerService appetizerService;
    private final MainMealService mainMealService;
    private final DesertService desertService;
    private final DrinkService drinkService;
    private final SoupService soupService;

    @Transactional
    public Menu saveNewMenu(Menu menu) {

        return menuDAO.saveMenu(menu);

    }

//    private Menu assignDishesToTheMenu(Menu menu, Menu menuSaved) {
//        return menuSaved
//                .withAppetizers(new HashSet<>(appetizerService.saveNewAppetizers(
//                        menu.getAppetizers().stream()
//                                .map(appetizer -> appetizer.withMenu(menuSaved))
//                                .toList())))
//                .withMainMeals(new HashSet<>(mainMealService.saveNewMainMeals(
//                        menu.getMainMeals().stream()
//                                .map(mainMeal -> mainMeal.withMenu(menuSaved))
//                                .toList())))
//                .withDeserts(new HashSet<>(desertService.saveNewDeserts(
//                        menu.getDeserts().stream()
//                                .map(desert -> desert.withMenu(menuSaved))
//                                .toList())))
//                .withDrinks(new HashSet<>(drinkService.saveNewDrinks(
//                        menu.getDrinks().stream()
//                                .map(drink -> drink.withMenu(menuSaved))
//                                .toList())))
//                .withSoups(new HashSet<>(soupService.saveNewSoups(
//                        menu.getSoups().stream()
//                                .map(soup -> soup.withMenu(menuSaved))
//                                .toList())));
//    }

}

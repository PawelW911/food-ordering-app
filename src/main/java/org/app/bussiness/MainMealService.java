package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MainMealService {

    private final MainMealDAO mainMealDAO;

    @Transactional
    public List<MainMeal> saveNewMainMeals(List<MainMeal> mainMeals) {
        return mainMealDAO.saveMainMeals(mainMeals);
    }
}

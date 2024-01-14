package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.business.dao.DrinkDAO;
import org.app.domain.Drink;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DrinkService {

    private final DrinkDAO drinkDAO;

    @Transactional
    public List<Drink> saveNewDrinks(List<Drink> drinks) {
        return drinkDAO.saveDrinks(drinks);
    }
}

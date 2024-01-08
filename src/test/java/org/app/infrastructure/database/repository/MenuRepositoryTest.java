package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Menu;
import org.app.infrastructure.configuration.PersistenceContainerTestConfiguration;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.repository.jpa.MainMealJpaRepository;
import org.app.infrastructure.database.repository.jpa.MenuJpaRepository;
import org.app.util.MenuFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MenuRepositoryTest {

    private final MenuRepository menuRepository;
    private final MenuJpaRepository menuJpaRepository;

    @Test
    void correctlySaveMenu() {
        // given
        Menu menu = MenuFixtures.someMenuPolishFood();

        // when
        List<MenuEntity> allMenuBeforeSave = menuJpaRepository.findAll();
        menuRepository.saveMenu(menu);
        List<MenuEntity> allMenuAfterSave = menuJpaRepository.findAll();

        // then
        Assertions.assertThat(allMenuBeforeSave).hasSize(allMenuAfterSave.size()-1);
    }
}

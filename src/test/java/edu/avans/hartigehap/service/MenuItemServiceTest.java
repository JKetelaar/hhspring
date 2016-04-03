package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.FoodCategory;
import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.repository.FoodCategoryRepository;
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author JKetelaar
 */
public class MenuItemServiceTest extends AbstractTransactionRollbackTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Test
    public void dummy() {
        // empty - tests configuration of test context.
    }

    @Test
    public void testAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        FoodCategory drinkCategory = foodCategoryRepository.findByTag("energizing drinks");

        for (MenuItem menuItem : menuItemRepository.findAll()) {
            menuItems.addAll(menuItem.getFoodCategories().stream().filter(foodCategory -> foodCategory.getTag().equals(drinkCategory.getTag())).map(foodCategory -> menuItem).collect(Collectors.toList()));
        }

        assertEquals(menuItems.size(), 2);
        assertEquals(menuItems.get(1).getPrice(), menuItems.get(0).getPrice() + 1);
    }
}

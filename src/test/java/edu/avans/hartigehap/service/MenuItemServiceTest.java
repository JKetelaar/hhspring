package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.FoodCategory;
import edu.avans.hartigehap.domain.Menu;
import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.FoodCategoryRepository;
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

        for (MenuItem menuItem : menuItemRepository.findAll()){
            for(FoodCategory foodCategory : menuItem.getFoodCategories()){
                if (foodCategory.getTag().equals(drinkCategory.getTag())){
                    menuItems.add(menuItem);
                }
            }
        }

        assertEquals(menuItems.size(), 2);
    }
}

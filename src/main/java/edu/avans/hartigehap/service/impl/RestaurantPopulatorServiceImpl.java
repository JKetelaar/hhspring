package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.domain.*;
import edu.avans.hartigehap.repository.*;
import edu.avans.hartigehap.service.RestaurantPopulatorService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("restaurantPopulatorService")
@Repository
@Transactional
public class RestaurantPopulatorServiceImpl implements RestaurantPopulatorService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuComponentRepository menuComponentRepository;

    private List<Meal> meals = new ArrayList<>();
    private List<FoodCategory> foodCats = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<UserRole> userRoles = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    private void createPredefinedMenu(){
        PredefinedMenu pancakeMenu = new PredefinedMenu("Pancake menu", "Great pancakes for breakfast");
        pancakeMenu = menuComponentRepository.save(pancakeMenu);

        MenuItem pancakeItem = new Meal("Pancake", "NietTeVinden.png", 3, "easy");
        pancakeItem = menuComponentRepository.save(pancakeItem);
        pancakeItem.setParent(pancakeMenu);

        MenuItem colaItem = new Drink("Cola", "cola.png", 3, Drink.Size.MEDIUM);
        colaItem = menuComponentRepository.save(colaItem);
        colaItem.setParent(pancakeMenu);

        pancakeMenu.add(pancakeItem);
        pancakeMenu.add(colaItem);
    }

    /**
     * menu items, food categories and customers are common to all restaurants
     * and should be created only once. Although we can safely assume that the
     * are related to at least one restaurant and therefore are saved via the
     * restaurant, we save them explicitly anyway
     */
    private void createCommonEntities() {
        // create FoodCategories
        createFoodCategory("low fat");
        createFoodCategory("high energy");
        createFoodCategory("vegatarian");
        createFoodCategory("italian");
        createFoodCategory("asian");
        createFoodCategory("alcoholic drinks");
        createFoodCategory("energizing drinks");

        // create Meals
        createMeal("spaghetti", "spaghetti.jpg", 8, "easy",
                Arrays.<FoodCategory>asList(foodCats.get(3), foodCats.get(1)));
        createMeal("macaroni", "macaroni.jpg", 8, "easy",
                Arrays.<FoodCategory>asList(foodCats.get(3), foodCats.get(1)));
        createMeal("canneloni", "canneloni.jpg", 9, "easy",
                Arrays.<FoodCategory>asList(foodCats.get(3), foodCats.get(1)));
        createMeal("pizza", "pizza.jpg", 9, "easy", Arrays.<FoodCategory>asList(foodCats.get(3), foodCats.get(1)));
        createMeal("carpaccio", "carpaccio.jpg", 7, "easy",
                Arrays.<FoodCategory>asList(foodCats.get(3), foodCats.get(0)));
        createMeal("ravioli", "ravioli.jpg", 8, "easy",
                Arrays.<FoodCategory>asList(foodCats.get(3), foodCats.get(1), foodCats.get(2)));

        // create Drinks
        createDrink("beer", "beer.jpg", 1, Drink.Size.LARGE, Arrays.<FoodCategory>asList(foodCats.get(5)));
        createDrink("coffee", "coffee.jpg", 1, Drink.Size.MEDIUM, Arrays.<FoodCategory>asList(foodCats.get(6)));
        createCondimentedDrink("Coffee with sugar", "coffee.jpg", 1, drinks.get(1));

        // create Customers
        byte[] photo = new byte[]{127, -128, 0};
        createCustomer("peter", "limonade", new DateTime(), 1, "description", photo);
        createCustomer("barry", "batsbak", new DateTime(), 1, "description", photo);
        createCustomer("piet", "bakker", new DateTime(), 1, "description", photo);

        String ROLE_MANAGER = "ROLE_MANAGER", ROLE_EMPLOYEE = "ROLE_EMPLOYEE", ROLE_CUSTOMER = "ROLE_CUSTOMER";

        UserRole OBJECT_ROLE_MANAGER = createUserRoles(ROLE_MANAGER);
        UserRole OBJECT_ROLE_EMPLOYEE = createUserRoles(ROLE_EMPLOYEE);
        UserRole OBJECT_ROLE_CUSTOMER = createUserRoles(ROLE_CUSTOMER);

        createUser("jketelaar", "auditt01", true, Arrays.asList(OBJECT_ROLE_MANAGER, OBJECT_ROLE_EMPLOYEE));
        assignUserWithEmail("jeroenketelaar@me.com", users.get(0));

        createPredefinedMenu();
    }

    private void createFoodCategory(String tag) {
        FoodCategory foodCategory = new FoodCategory(tag);
        foodCategory = foodCategoryRepository.save(foodCategory);
        foodCats.add(foodCategory);
    }

    private void createMeal(String name, String image, int price, String recipe, List<FoodCategory> foodCats) {

        Meal meal = new Meal(name, image, price, recipe);
        // as there is no cascading between FoodCategory and MenuItem (both
        // ways), it is important to first
        // save foodCategory and menuItem before relating them to each other,
        // otherwise you get errors
        // like "object references an unsaved transient instance - save the
        // transient instance before flushing:"
        meal.addFoodCategories(foodCats);
        meal = menuItemRepository.save(meal);
        meals.add(meal);
    }

    private void createDrink(String name, String image, int price, Drink.Size size, List<FoodCategory> foodCats) {
        Drink drink = new Drink(name, image, price, size);
        drink = menuItemRepository.save(drink);
        drink.addFoodCategories(foodCats);
        drinks.add(drink);
    }

    private void createCondimentedDrink(String name, String image, int price, Drink drink){
        MenuItemDecorator condimentDrink = new Condiment(drink, name, image, price);
        condimentDrink = menuItemRepository.save(condimentDrink);
        drink.add(condimentDrink);
        condimentDrink.addFoodCategories(drink.getFoodCategories());
    }

    private void createCustomer(String firstName, String lastName, DateTime birthDate, int partySize,
                                String description, byte[] photo) {
        Customer customer = new Customer(firstName, lastName, birthDate, partySize, description, photo);
        customers.add(customer);
        customerRepository.save(customer);
    }

    private UserRole createUserRoles(String role) {
        UserRole userRole = new UserRole(role);
        userRole = userRoleRepository.save(userRole);

        userRoles.add(userRole);

        return userRole;
    }

    private void createUser(String username, String password, boolean enabled, List<UserRole> roles) {
        User user = new User(username, password, enabled);
        for (UserRole role : roles) {
            user.getRoles().add(role);
        }
        user = userRepository.save(user);
        users.add(user);
    }

    private void assignUserWithEmail(String email, User user){
        user.setType(NotificationAdapter.Type.EMAIL);
        user.setEmail(email);

        userRepository.save(user);
    }

    private void createDiningTables(int numberOfTables, Restaurant restaurant) {
        for (int i = 0; i < numberOfTables; i++) {
            DiningTable diningTable = new DiningTable(i + 1);
            diningTable.setRestaurant(restaurant);
            restaurant.getDiningTables().add(diningTable);
        }
    }

    private Restaurant populateRestaurant(Restaurant restaurant) {

        // will save everything that is reachable by cascading
        // even if it is linked to the restaurant after the save
        // operation
        restaurant = restaurantRepository.save(restaurant);

        // every restaurant has its own dining tables
        createDiningTables(5, restaurant);

        // for the moment every restaurant has all available food categories
        for (FoodCategory foodCat : foodCats) {
            restaurant.getMenu().getFoodCategories().add(foodCat);
        }

        // for the moment every restaurant has the same menu
        for (Meal meal : meals) {
            restaurant.getMenu().getMeals().add(meal);
        }

        // for the moment every restaurant has the same menu
        for (Drink drink : drinks) {
            restaurant.getMenu().getDrinks().add(drink);
        }

//        createCondimentedDrink("Coffee with sugar", "coffee.jpg", 2, drinks.get(1));

        // for the moment, every customer has dined in every restaurant
        // no cascading between customer and restaurant; therefore both
        // restaurant and customer
        // must have been saved before linking them one to another
        for (Customer customer : customers) {
            customer.getRestaurants().add(restaurant);
            restaurant.getCustomers().add(customer);
        }

        return restaurant;
    }

    public void createRestaurantsWithInventory() {

        createCommonEntities();

        Restaurant restaurant = new Restaurant(HARTIGEHAP_RESTAURANT_NAME, "deHartigeHap.jpg");
        restaurant = populateRestaurant(restaurant);

        restaurant = new Restaurant(PITTIGEPANNEKOEK_RESTAURANT_NAME, "dePittigePannekoek.jpg");
        restaurant = populateRestaurant(restaurant);

        restaurant = new Restaurant(HMMMBURGER_RESTAURANT_NAME, "deHmmmBurger.jpg");
        restaurant = populateRestaurant(restaurant);
    }
}

package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(Long id);

    Customer findByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findCustomersForRestaurant(Restaurant restaurant);

    Page<Customer> findAllByPage(Pageable pageable);

    Page<Customer> findCustomersForRestaurantByPage(Restaurant restaurant, Pageable pageable);

    Customer save(Customer customer);

    void delete(Long id);
}

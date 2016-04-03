package edu.avans.hartigehap.repository;


import edu.avans.hartigehap.domain.MenuItemDecorator;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author JKetelaar
 */
public interface MenuItemDecoratorRepository extends PagingAndSortingRepository<MenuItemDecorator, Long> {

}

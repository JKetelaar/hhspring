package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.MenuComponent;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author thom145, JKetelaar
 */
public interface MenuComponentRepository extends PagingAndSortingRepository<MenuComponent, String> {

    MenuComponent getMenuComponentMenuById(String id);

}

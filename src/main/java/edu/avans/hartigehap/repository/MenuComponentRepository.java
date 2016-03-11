package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.MenuComponent;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author thom145
 */
public interface MenuComponentRepository extends PagingAndSortingRepository<MenuComponent, Long> {

    MenuComponent getMenuComponentById(String id);

    List<MenuComponent> getMenuComponentsById(String id);

}

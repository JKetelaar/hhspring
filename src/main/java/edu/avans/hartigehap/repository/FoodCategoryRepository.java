package edu.avans.hartigehap.repository;

import edu.avans.hartigehap.domain.FoodCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FoodCategoryRepository extends PagingAndSortingRepository<FoodCategory, String> {

    FoodCategory findByTag(String tag);

}

package edu.avans.hartigehap.service.impl;

import com.google.common.collect.Lists;
import edu.avans.hartigehap.domain.Owner;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.service.OwnerService;
import edu.avans.hartigehap.repository.OwnerRepository;
import edu.avans.hartigehap.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author thom145
 */

//Hiermee wordt van deze klasse een Spring bean gemaakt
// met de naam ownerService. Spring beans zijn by default
// singletons, wat betekent dat er precies één Spring bean object is.
// Spring component scanning vindt deze bean,
// zodat die elders geïnjecteerd kan worden.
@Service("ownerService")
//zorgt ervoor dat de klasse als een repository functioneert
@Repository
//alle service methodes worden als een transactie uitgevoerd
@Transactional
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public List<Owner> findAll() {
        return Lists.newArrayList(ownerRepository.findAll());
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository.findOne(id);
    }

    @Override
    public List<Owner> findByName(String name) {
        return ownerRepository.findByName(name);
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Autowired
    private OwnerRepository ownerRepository;

    public List<Owner> findByRestaurant(String restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        return ownerRepository.findByRestaurants(Collections.singletonList(restaurant),
                new Sort(Sort.Direction.ASC, "name"));
    }

}

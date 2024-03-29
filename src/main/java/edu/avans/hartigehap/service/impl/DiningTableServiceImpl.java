package edu.avans.hartigehap.service.impl;

import com.google.common.collect.Lists;
import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.EmptyBillException;
import edu.avans.hartigehap.domain.MenuComponent;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.DiningTableRepository;
import edu.avans.hartigehap.repository.MenuComponentRepository;
import edu.avans.hartigehap.service.DiningTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service("diningTableService")
@Repository
@Transactional(rollbackFor = StateException.class)
@Slf4j
public class DiningTableServiceImpl implements DiningTableService {

    @Autowired
    private DiningTableRepository diningTableRepository;
    @Autowired
    private MenuComponentRepository menuComponentRepository;

    @Transactional(readOnly = true)
    public List<DiningTable> findAll() {
        return Lists.newArrayList(diningTableRepository.findAll());
    }

    @Transactional(readOnly = true)
    public DiningTable findById(Long id) {
        return diningTableRepository.findOne(id);
    }

    public DiningTable save(DiningTable diningTable) {
        return diningTableRepository.save(diningTable);
    }

    public void delete(Long id) {
        diningTableRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Page<DiningTable> findAllByPage(Pageable pageable) {
        return diningTableRepository.findAll(pageable);
    }

    // to be able to follow associations outside the context of a transaction,
    // prefetch the associated entities by traversing the associations
    @Transactional(readOnly = true)
    public DiningTable fetchWarmedUp(Long id) {
        log.info("(fetchWarmedUp) diningTable id: " + id);

        // finding an item using find
        DiningTable diningTable = diningTableRepository.findOne(id);

        // the following code will deliberately cause a null pointer exception,
        // if something is wrong
        log.info("diningTable = " + diningTable.getId());

        diningTable.warmup();

        menuItemWarmup();

        return diningTable;
    }

    public void addOrderItem(DiningTable diningTable, String menuItemName) {
        MenuComponent menuItem = menuComponentRepository.findOne(menuItemName);
        diningTable.getCurrentBill().getCurrentOrder().addOrderItem(menuItem);
    }

    public void deleteOrderItem(DiningTable diningTable, String menuItemName) {
        MenuComponent menuItem = menuComponentRepository.findOne(menuItemName);
        diningTable.getCurrentBill().getCurrentOrder().deleteOrderItem(menuItem);
    }

    public void submitOrder(DiningTable diningTable) throws StateException {
        diningTable.getCurrentBill().submitOrder();

        // for test purposes: to cause a rollback, throw new
        // StateException("boe")
    }

    public void submitBill(DiningTable diningTable) throws StateException, EmptyBillException {
        diningTable.submitBill();
    }

    private void menuItemWarmup() {
        Iterator<MenuComponent> menuItemIterator = menuComponentRepository.findAll().iterator();
        while (menuItemIterator.hasNext()) {
            MenuComponent menuComponent = menuItemIterator.next();
            log.debug(menuComponent.getId());
        }
    }
}

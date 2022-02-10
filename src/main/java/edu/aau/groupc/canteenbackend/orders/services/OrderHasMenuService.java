package edu.aau.groupc.canteenbackend.orders.services;

import edu.aau.groupc.canteenbackend.orders.OrderHasMenu;
import edu.aau.groupc.canteenbackend.orders.dao.OrderHasMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OrderHasMenuService implements IOrderHasMenuService {
    private final OrderHasMenuRepository orderHasMenuRepo;

    @Autowired
    public OrderHasMenuService(OrderHasMenuRepository orderHasMenuRepo) {
        this.orderHasMenuRepo = orderHasMenuRepo;
    }

    @Override
    public OrderHasMenu save(OrderHasMenu orderHasMenu) {
        return orderHasMenuRepo.save(orderHasMenu);
    }
}

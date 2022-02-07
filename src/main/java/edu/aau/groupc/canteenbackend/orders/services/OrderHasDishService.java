package edu.aau.groupc.canteenbackend.orders.services;

import edu.aau.groupc.canteenbackend.orders.OrderHasDish;
import edu.aau.groupc.canteenbackend.orders.dao.OrderHasDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OrderHasDishService implements IOrderHasDishService {
    private final OrderHasDishRepository orderHasDishRepo;

    @Autowired
    public OrderHasDishService(OrderHasDishRepository orderHasDishRepo) {
        this.orderHasDishRepo = orderHasDishRepo;
    }

    @Override
    public OrderHasDish save(OrderHasDish orderHasDish) {
        return orderHasDishRepo.save(orderHasDish);
    }
}

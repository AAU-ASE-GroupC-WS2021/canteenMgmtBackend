package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.dao.DishRepository;
import edu.aau.groupc.canteenbackend.dto.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DishService implements IDishService {
    private DishRepository dishRepo;

    @Autowired
    public DishService(DishRepository dishRepo) {
        this.dishRepo = dishRepo;
    }

    @Override
    public List<Dish> findAll() {
        return dishRepo.findAll();
    }

    @Override
    public void create(Dish newDish) {
        dishRepo.save(newDish);
    }
}

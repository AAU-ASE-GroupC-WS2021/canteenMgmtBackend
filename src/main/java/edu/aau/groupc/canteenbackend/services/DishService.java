package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.dao.DishRepository;
import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DishService implements IDishService {
    private final DishRepository dishRepo;

    @Autowired
    public DishService(DishRepository dishRepo) {
        this.dishRepo = dishRepo;
    }

    @Override
    public Dish findById(Integer id) {
        Optional<Dish> dishOptional = dishRepo.findById(id);
        return dishOptional.orElseThrow(() -> new IllegalArgumentException("no dish found"));
    }

    @Override
    public List<Dish> findAll() {
        return dishRepo.findAll();
    }

    @Override
    public Dish create(Dish newDish) {
        return dishRepo.save(newDish);
    }
}

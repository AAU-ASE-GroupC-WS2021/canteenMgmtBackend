package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.dao.DishRepository;
import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


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
    public Dish findById(Integer id) throws ResponseStatusException {
        Optional<Dish> dishOptional = dishRepo.findById(id);
        return dishOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "dish not found"));
    }

    @Override
    public List<Dish> findAll() {
        return dishRepo.findAllByOrderByIdDesc();
    }


    @Override
    public Dish create(Dish newDish) {
        return dishRepo.save(newDish);
    }


    public ResponseEntity<Object> update(Dish newDish) {
        List<Dish> allDish = findAll();
        int flag = 0;
        for (Dish aDish : allDish) {
            if (aDish.getName().equals(newDish.getName())) {
                aDish.setPrice(newDish.getPrice());
                aDish.setType(newDish.getType());
                aDish.setDishDay(newDish.getDishDay());
                dishRepo.save(aDish);
                flag =1;
                break;
            }
        }
        if (flag == 1)
            return ResponseEntity.ok("dish updated");

        else
            return ResponseEntity.ok("No Such Dish found");
    }

    public ResponseEntity<Object> delete(String deleteDishName) {
        List<Dish> allDish = findAll();
        int flag = 0;
        for (Dish aDish : allDish) {
            if (deleteDishName.equals(aDish.getName())) {
                dishRepo.delete(aDish);
                flag = 1;
            }
        }
        if (flag == 1)
            return ResponseEntity.ok("dish deleted");

        else
            return ResponseEntity.ok("No Such Dish");
        }

    public ResponseEntity<Object> deleteAllDishes(String deleteDishVariable) {
        if (deleteDishVariable.equals("all"))
        {
            dishRepo.deleteAll();
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.ok("invalid input parameter");
    }

    @Override
    public List<Dish> findByDishDayAll(String dishDay) {
        return dishRepo.findByDishDay(Dish.DishDay.valueOf(dishDay));
    }
}

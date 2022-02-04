package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.dao.DishRepository;
import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MenuService implements IMenuService {
    private final DishRepository dishRepo;

    @Autowired
    public MenuService(DishRepository dishRepo) {
        this.dishRepo = dishRepo;
    }


    public List<Dish> findAllMenus() {

        return dishRepo.findAll(Sort.by(Sort.Direction.ASC,"dishDay"));
    }

    public List<Dish> findMenuByDay( Dish.DishDay day) {

        return dishRepo.findBydishDay(day);
    }


}

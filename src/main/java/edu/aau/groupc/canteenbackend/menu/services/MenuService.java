package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.dao.DishRepository;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MenuService implements IMenuService {
//    private final DishRepository dishRepo;
    private final MenuRepository menuRepo;

    @Autowired
    public MenuService(MenuRepository menuRepo) {
//        this.dishRepo = dishRepo;
        this.menuRepo = menuRepo;
    }


//    public List<Dish> findAllMenus() {
//
//        return dishRepo.findAll(Sort.by(Sort.Direction.ASC,"dishDay"));
//    }
//
//    public List<Dish> findMenuByDay( Dish.DishDay day) {
//
//        return dishRepo.findBydishDay(day);
//    }

    public List<Menu> findByMenuDay(Menu.MenuDay day){

        return menuRepo.findByMenuDay(day);
    }
//    @Override
    public List<Menu> findAll() {
        return menuRepo.findAllByOrderByIdDesc();
    }

    public Menu create(Menu newMenu) {
//        List<String> menuDishNames =newMenu.getMenuDishNames();
//        for (String name: menuDishNames){
//
//        }
        return menuRepo.save(newMenu);
    }

    public ResponseEntity<Object> update(Menu newMenu) {
        List<Menu> allMenu = findAll();
        int flag = 0;
        for (Menu aMenu : allMenu) {
            if (aMenu.getName().equals(newMenu.getName())) {
                aMenu.setPrice(newMenu.getPrice());
                aMenu.setMenuDishNames(newMenu.getMenuDishNames());
                menuRepo.save(aMenu);
                flag =1;
                break;
            }
        }
        if (flag == 1)
            return ResponseEntity.ok("Menu Updated");

        else
            return ResponseEntity.ok("No Such Menu Found");
    }

    public ResponseEntity<Object> delete(Menu newMenu) {
        List<Menu> allMenu = findAll();
        int flag = 0;
        for (Menu aMenu : allMenu) {
            if (newMenu.getName().equals(aMenu.getName())) {
                menuRepo.delete(aMenu);
                flag = 1;
            }
        }
        if (flag == 1)
            return ResponseEntity.ok("dish deleted");

        else
            return ResponseEntity.ok("No Such Dish");
    }
}

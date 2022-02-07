package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.dao.DishRepository;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MenuService implements IMenuService {
    private final DishRepository dishRepo;
    private final MenuRepository menuRepo;

    @Autowired
    public MenuService(MenuRepository menuRepo, DishRepository dishRepo) {
        this.dishRepo = dishRepo;
        this.menuRepo = menuRepo;
    }

public List<Menu> findByMenuDay(String menuDay){
        return menuRepo.findByMenuDay(Menu.MenuDay.valueOf(menuDay));
}

//    @Override
    public List<Menu> findAll() {
        return menuRepo.findAllByOrderByIdDesc();
    }

    public ResponseEntity<Object> create(Menu newMenu) {
        List<String> menuDishNames =newMenu.getMenuDishNames();
        if (menuRepo.existsByName(newMenu.getName())){
            return ResponseEntity.ok("Menu already exists");
        }
        if (menuDishNames == null){
            return ResponseEntity.ok("No Dish selected");
        }

        for (String name: menuDishNames){
            if (! dishRepo.existsByName(name)){
                return ResponseEntity.ok(name + " Dish does not exist in the database");
            }
        }

        menuRepo.save(newMenu);
        return ResponseEntity.ok(newMenu);
    }

    public ResponseEntity<Object> update(Menu newMenu) {
        List<Menu> allMenu = findAll();
        List<String> menuDishNames =newMenu.getMenuDishNames();
        int flag = 0;
        for (String name: menuDishNames){
            if (! dishRepo.existsByName(name)){
                return ResponseEntity.ok(name + " Dish does not exist in the database");
            }
        }
        for (Menu aMenu : allMenu) {
            if (aMenu.getName().equals(newMenu.getName())) {
                aMenu.setPrice(newMenu.getPrice());
                aMenu.setMenuDay(newMenu.getMenuDay());
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
            return ResponseEntity.ok("Menu deleted");

        else
            return ResponseEntity.ok("No Such Menu");
    }

    public ResponseEntity<Object> deleteAllMenus(String deleteMenuVariable) {
        if (deleteMenuVariable.equals("all"))
        {
            menuRepo.deleteAll();
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.ok("invalid input parameter");
    }
}

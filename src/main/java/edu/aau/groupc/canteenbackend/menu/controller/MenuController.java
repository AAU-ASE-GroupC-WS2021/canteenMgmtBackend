package edu.aau.groupc.canteenbackend.menu.controller;

import edu.aau.groupc.canteenbackend.dto.DishDTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.dto.MenuDTO;
import edu.aau.groupc.canteenbackend.menu.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
public class MenuController {
    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

//    @GetMapping(value = "/menu")
//    public List<Dish> getMenuDishes(@RequestParam(required = false) @Valid Dish.DishDay menuDay)
//    {
//        if(menuDay !=null)
//            return menuService.findMenuByDay(menuDay);
//        else
//            return menuService.findAllMenus();
//    }

    @GetMapping(value = "/menu")
    public List<Menu> getMenus(@RequestParam(required = false) @Valid Menu.MenuDay menuDay)
    {
        if(menuDay !=null)
            return menuService.findByMenuDay(menuDay);
        else
            return menuService.findAll();

    }

    @PostMapping(value = "/menu")
    public Menu createMenu(@Valid @RequestBody MenuDTO newMenu)
    {
        return menuService.create(newMenu.toEntity());
    }

    @PutMapping(value = "/menu")
    public ResponseEntity<Object> updateMenu(@Valid @RequestBody MenuDTO updateMenu)
    {
        return menuService.update(updateMenu.toEntity());
    }

    @DeleteMapping (value = "/menu")
    public ResponseEntity<Object> deleteMenu(@Valid @RequestBody MenuDTO deleteMenu)
    {
        return menuService.delete(deleteMenu.toEntity());
    }

}

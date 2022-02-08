package edu.aau.groupc.canteenbackend.menu.controller;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.dto.MenuDTO;
import edu.aau.groupc.canteenbackend.menu.services.IMenuService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Validated
@RestController
public class MenuController {
    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(value = "/menu")
    public List<Menu> getMenu(@RequestParam(required = false) @Valid String menuDay)
    {
        if(menuDay == null || menuDay.isEmpty())
            return menuService.findAll();
        else
            return menuService.findByMenuDay(menuDay);
    }

    @Secured(User.Type.ADMIN)
    @PostMapping(value = "/menu")
    public ResponseEntity<Object>  createMenu(@Valid @RequestBody MenuDTO newMenu)
    {
        return menuService.create(newMenu.toEntity());
    }

    @Secured(User.Type.ADMIN)
    @PutMapping(value = "/menu")
    public ResponseEntity<Object> updateMenu(@Valid @RequestBody MenuDTO updateMenu)
    {
        return menuService.update(updateMenu.toEntity());
    }

    @Secured(User.Type.ADMIN)
    @DeleteMapping (value = "/menu")
    public ResponseEntity<Object> deleteMenu(@Valid @RequestBody String deleteMenuName)
    {
        return menuService.delete(deleteMenuName);
    }

}

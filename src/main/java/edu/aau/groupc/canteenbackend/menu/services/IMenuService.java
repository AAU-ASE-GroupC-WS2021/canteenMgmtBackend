package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.menu.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IMenuService {

    List<Menu> findAll();

    Menu findById(int id) throws ResponseStatusException;

    ResponseEntity<Object> create(Menu newMenu);

    ResponseEntity<Object> delete(Menu newMenu);

    ResponseEntity<Object> update(Menu newMenu);


    List<Menu> findByMenuDay(String menuDay);

    ResponseEntity<Object> deleteAllMenus(String deleteMenuVariable);
}

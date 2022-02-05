package edu.aau.groupc.canteenbackend.mgmt.services;

import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.dto.CanteenDTO;
import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface ICanteenService {
    List<Canteen> findAll();

    Optional<Canteen> findById(int id);

    Canteen findEntityById(int id) throws ResponseStatusException;

    Canteen create(Canteen canteen);

    /**
     * Update canteen entity.
     *
     * @param canteen Canteen to update
     * @return Updated canteen
     * @throws CanteenNotFoundException If no canteen with the given id exists.
     */
    Canteen update(int id, CanteenDTO canteen) throws CanteenNotFoundException;
}

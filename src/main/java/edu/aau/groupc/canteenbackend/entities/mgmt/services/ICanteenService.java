package edu.aau.groupc.canteenbackend.entities.mgmt.services;

import edu.aau.groupc.canteenbackend.entities.mgmt.Canteen;

import java.util.List;
import java.util.Optional;

public interface ICanteenService {
    List<Canteen> findAll();
    Optional<Canteen> findById(int id);
    Canteen create(Canteen canteen);

    /**
     * Update canteen entity.
     * @param canteen Canteen to update
     * @return Updated canteen
     * @throws IllegalArgumentException If no canteen with the given id exists.
     */
    Canteen update(Canteen canteen) throws IllegalArgumentException;
}

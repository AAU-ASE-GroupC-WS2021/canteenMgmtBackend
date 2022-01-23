package edu.aau.groupc.canteenbackend.entities.mgmt.repositories;

import edu.aau.groupc.canteenbackend.entities.mgmt.Canteen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICanteenRepository extends JpaRepository<Canteen, Integer> {
}

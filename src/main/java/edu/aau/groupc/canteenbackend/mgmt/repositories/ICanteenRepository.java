package edu.aau.groupc.canteenbackend.mgmt.repositories;

import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICanteenRepository extends JpaRepository<Canteen, Integer> {
}

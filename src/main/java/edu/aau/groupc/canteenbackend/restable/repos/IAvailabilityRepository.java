package edu.aau.groupc.canteenbackend.restable.repos;

import edu.aau.groupc.canteenbackend.restable.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAvailabilityRepository extends JpaRepository<Availability, Integer> {
    List<Availability> findAllBySlotAsc();

}

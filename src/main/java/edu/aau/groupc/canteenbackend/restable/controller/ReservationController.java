package edu.aau.groupc.canteenbackend.restable.controller;

import edu.aau.groupc.canteenbackend.endpoints.AbstractController;
import edu.aau.groupc.canteenbackend.restable.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api" )
public class ReservationController extends AbstractController {
    private final IReservationService reservationService;

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

}

package edu.aau.groupc.canteenbackend.mgmt.services;

import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.repositories.ICanteenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CanteenService implements ICanteenService {

    private final ICanteenRepository canteenRepo;

    @Autowired
    public CanteenService(ICanteenRepository canteenRepo) {
        this.canteenRepo = canteenRepo;
    }

    @Override
    public List<Canteen> findAll() {
        return canteenRepo.findAll();
    }

    @Override
    public Optional<Canteen> findById(int id) {
        return canteenRepo.findById(id);
    }

    @Override
    public Canteen findEntityById(int id) {
        Optional<Canteen> canteenOptional = canteenRepo.findById(id);
        return canteenOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen not found"));
    }

    @Override
    public Canteen create(Canteen canteen) {
        return canteenRepo.save(canteen);
    }

    @Override
    public Canteen update(Canteen canteen) throws IllegalArgumentException {
        if (!canteenRepo.existsById(canteen.getId())) {
            throw new IllegalArgumentException("canteen not found");
        }
        return canteenRepo.save(canteen);
    }
}

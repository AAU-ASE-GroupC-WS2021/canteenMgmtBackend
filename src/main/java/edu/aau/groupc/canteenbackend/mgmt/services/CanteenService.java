package edu.aau.groupc.canteenbackend.mgmt.services;

import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.dto.CanteenDTO;
import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import edu.aau.groupc.canteenbackend.mgmt.repositories.ICanteenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return canteenRepo.findAllByOrderByIdAsc();
    }

    @Override
    public Optional<Canteen> findById(int id) {
        return canteenRepo.findById(id);
    }

    @Override
    public Canteen create(Canteen canteen) {
        return canteenRepo.save(canteen);
    }

    @Override
    public Canteen update(int id, CanteenDTO canteen) throws CanteenNotFoundException {
        if (!canteenRepo.existsById(id)) {
            throw new CanteenNotFoundException();
        }
        Canteen entity = canteen.toEntity().setId(id);
        return canteenRepo.save(entity);
    }
}

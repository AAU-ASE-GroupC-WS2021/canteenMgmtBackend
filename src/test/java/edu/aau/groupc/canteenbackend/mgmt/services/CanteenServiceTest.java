package edu.aau.groupc.canteenbackend.mgmt.services;

import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.dto.CanteenDTO;
import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("H2Database")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CanteenServiceTest {

    private final int invalidID = 9999;

    @Autowired
    private ICanteenService canteenService;

    @Test
    public void testFindAll() {
        assertTrue(canteenService.findAll().isEmpty());
        createCanteen();
        assertEquals(1, canteenService.findAll().size());
    }

    @Test
    public void testFindById() {
        assertFalse(canteenService.findById(1).isPresent());
        Canteen createdCanteen = createCanteen();
        Optional<Canteen> foundCanteen = canteenService.findById(createdCanteen.getId());
        assertTrue(foundCanteen.isPresent());
        assertThat(createdCanteen).usingRecursiveComparison().isEqualTo(foundCanteen.get());
    }

    @Test
    public void testUpdate_invalidId_ThenIllegalArgumentException() {
        CanteenDTO c = CanteenDTO.create("someCanteen", "someAddress", 420);
        assertThrows(CanteenNotFoundException.class, () ->
                canteenService.update(invalidID, c));
    }

    @Test
    public void testUpdate_validId_ThenUpdatedCanteenReturned() throws CanteenNotFoundException {
        String updatedName = "updatedName";
        String updatedAddress = "updatedName";
        int updatedNumTables = 69;

        Canteen c = createCanteen();
        CanteenDTO cDTO = CanteenDTO.create(updatedName, updatedAddress, updatedNumTables);
        Canteen updatedCanteen = canteenService.update(c.getId(), cDTO);
        Canteen expectedCanteen = cDTO.toEntity().setId(c.getId());
        assertThat(expectedCanteen).usingRecursiveComparison().isEqualTo(updatedCanteen);
    }

    private Canteen createCanteen() {
        return canteenService.create(new Canteen("someCanteen", "someAddress", 420));
    }
}

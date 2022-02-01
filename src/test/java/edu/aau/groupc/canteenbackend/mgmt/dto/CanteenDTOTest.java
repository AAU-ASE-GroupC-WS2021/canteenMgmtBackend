package edu.aau.groupc.canteenbackend.mgmt.dto;

import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CanteenDTOTest implements ValidationTest {

    @Test
    public void testValid_ThenOk() {
        assertValid(CanteenDTO.create("canteen", "address", 69));
    }

    @Test
    public void testNullName_ThenInvalid() {
        assertInvalid(CanteenDTO.create(null, "address", 69));
    }

    @Test
    public void testNullAddress_ThenInvalid() {
        assertInvalid(CanteenDTO.create("canteen", null, 69));
    }

    @Test
    public void testNullNumTables_ThenInvalid() {
        assertInvalid(CanteenDTO.create("canteen", "address", null));
    }

    @Test
    public void testNegativeNumSeats_ThenInvalid() {
        assertInvalid(CanteenDTO.create("canteen", "address", -1));
    }

    @Test
    public void testZeroNumSeats_ThenValid() {
        assertValid(CanteenDTO.create("canteen", "address", 0));
    }

    @Test
    public void testAllInvalid_ThenInvalid() {
        assertInvalid(CanteenDTO.create(null, null, -1));
    }
}

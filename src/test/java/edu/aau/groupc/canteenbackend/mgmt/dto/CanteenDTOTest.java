package edu.aau.groupc.canteenbackend.mgmt.dto;

import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CanteenDTOTest implements ValidationTest {

    @Test
    void testValid_ThenOk() {
        assertValid(CanteenDTO.create("canteen", "address", 69));
    }

    @Test
    void testNullName_ThenInvalid() {
        assertInvalid(CanteenDTO.create(null, "address", 69));
    }

    @Test
    void testNullAddress_ThenInvalid() {
        assertInvalid(CanteenDTO.create("canteen", null, 69));
    }

    @Test
    void testNullNumTables_ThenInvalid() {
        assertInvalid(CanteenDTO.create("canteen", "address", null));
    }

    @Test
    void testNegativeNumSeats_ThenInvalid() {
        assertInvalid(CanteenDTO.create("canteen", "address", -1));
    }

    @Test
    void testZeroNumSeats_ThenValid() {
        assertValid(CanteenDTO.create("canteen", "address", 0));
    }

    @Test
    void testAllInvalid_ThenInvalid() {
        assertInvalid(CanteenDTO.create(null, null, -1));
    }
}

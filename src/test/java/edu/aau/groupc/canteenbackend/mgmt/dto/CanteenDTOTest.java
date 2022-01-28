package edu.aau.groupc.canteenbackend.mgmt.dto;

import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CanteenDTOTest implements ValidationTest {

    @Test
    public void testValid_ThenOk() {
        assertValid(createCanteen("canteen", "address", 69));
    }

    @Test
    public void testNullName_ThenInvalid() {
        assertInvalid(createCanteen(null, "address", 69));
    }

    @Test
    public void testNullAddress_ThenInvalid() {
        assertInvalid(createCanteen("canteen", null, 69));
    }

    @Test
    public void testNullNumTables_ThenInvalid() {
        assertInvalid(createCanteen("canteen", "address", null));
    }

    @Test
    public void testNegativeNumSeats_ThenInvalid() {
        assertInvalid(createCanteen("canteen", "address", -1));
    }

    @Test
    public void testZeroNumSeats_ThenValid() {
        assertValid(createCanteen("canteen", "address", 0));
    }

    @Test
    public void testAllInvalid_ThenInvalid() {
        assertInvalid(createCanteen(null, null, -1));
    }

    private CanteenDTO createCanteen(String name, String address, Integer numTables) {
        CanteenDTO c = new CanteenDTO();
        c.setName(name);
        c.setAddress(address);
        c.setNumTables(numTables);
        return c;
    }
}

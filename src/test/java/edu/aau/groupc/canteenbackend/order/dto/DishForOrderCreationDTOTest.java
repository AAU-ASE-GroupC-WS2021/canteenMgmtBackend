package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DishForOrderCreationDTOTest implements ValidationTest {

    @Test
    void testValid_ThenOk() {
        assertValid(createDishDTO(1, 1));
    }

    @Test
    void testNullDishId_ThenInvalid() {
        assertInvalid(createDishDTO(null, 1));
    }

    @Test
    void testNullCount_ThenInvalid() {
        assertInvalid(createDishDTO(1, null));
    }

    @Test
    void testNegativeCount_ThenInvalid() {
        assertInvalid(createDishDTO(1, -1));
    }

    @Test
    void testZeroCount_ThenInvalid() {
        assertInvalid(createDishDTO(1, 0));
    }


    private DishForOrderCreationDTO createDishDTO(Integer dishId, Integer count) {
        DishForOrderCreationDTO dto = new DishForOrderCreationDTO();
        dto.setId(dishId);
        dto.setCount(count);
        return dto;
    }
}

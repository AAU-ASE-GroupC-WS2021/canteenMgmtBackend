package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateOrderDTOTEST implements ValidationTest {

    final Date futureDate = new Date(new Date().getTime() + 3600 * 1000 * 3);

    @Test
    public void testValid_ThenOk() {
        assertValid(createOrderDTO(1L, 1, generateDishList(2), false, futureDate));
    }

    @Test
    public void testNullCanteenId_ThenInvalid() {
        assertInvalid(createOrderDTO(1L, null, generateDishList(2), true, futureDate));
    }

    @Test
    public void testNullDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1L, 1, null, true, futureDate));
    }

    @Test
    public void testEmptyDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1L, 1, new LinkedList<>(), false, futureDate));
    }

    // this test is only to check if the "parent" dto catches invalid states in the dtos inside the list
    @Test
    public void testInvalidDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1L, 1, generateInvalidDishList(), false, futureDate));
    }

    @Test
    public void testDateNow_ThenInvalid() {
        assertInvalid(createOrderDTO(1L, 1, generateDishList(1), false, new Date()));
    }


    private CreateOrderDTO createOrderDTO(Long userId, Integer canteenId, List<DishForOrderCreationDTO> dishList, boolean reserveTable, Date pickupDate) {
        CreateOrderDTO dto = new CreateOrderDTO();
        dto.setDishes(dishList);
        dto.setUserId(userId);
        dto.setCanteenId(canteenId);
        dto.setReserveTable(reserveTable);
        dto.setPickupDate(pickupDate);
        return dto;
    }

    // dont really care about the dishDTOs except for thembeing valid
    private List<DishForOrderCreationDTO> generateDishList(int length) {
        List<DishForOrderCreationDTO> dishList = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            DishForOrderCreationDTO dishDto = new DishForOrderCreationDTO();
            dishDto.setId(i);
            dishDto.setCount(i + 1);
            dishList.add(dishDto);
        }
        return dishList;
    }

    // dont really care about the dishDTOs except for them being invalid
    private List<DishForOrderCreationDTO> generateInvalidDishList() {
        List<DishForOrderCreationDTO> dishList = new LinkedList<>();
        DishForOrderCreationDTO dishDto = new DishForOrderCreationDTO();
        dishDto.setId(null);
        dishDto.setCount(0);
        dishList.add(dishDto);
        return dishList;
    }
}

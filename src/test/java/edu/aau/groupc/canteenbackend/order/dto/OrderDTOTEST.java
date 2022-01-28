package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDishDTO;
import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDTOTEST implements ValidationTest {

    final String name = "name";
    final Float price = 1f;
    final String type = "MAIN";
    final Integer id = 1;
    final Integer count = 1;

    @Test
    public void testValid_ThenOk() {
        assertValid(createOrderDTO(1, 1, generateDishList(2)));
    }

    @Test
    public void testNullId_ThenInvalid() {
        assertInvalid(createOrderDTO(null, 1, generateDishList(2)));
    }

    @Test
    public void testNullCanteenId_ThenInvalid() {
        assertInvalid(createOrderDTO(1, null, generateDishList(2)));
    }

    @Test
    public void testNullDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, null));
    }

    @Test
    public void testEmptyDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, new LinkedList<>()));
    }

    // this test is only to check if the "parent" dto catches invalid states in the dtos inside the list
    @Test
    public void testInvalidDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, generateInvalidDishList()));
    }


    private OrderDTO createOrderDTO(Integer id, Integer canteenId, List<OrderDishDTO> dishList) {
        OrderDTO dto = new OrderDTO();
        dto.setId(id);
        dto.setDishes(dishList);
        dto.setCanteenId(canteenId);
        return dto;
    }

    // dont really care about the dishDTOs except for them being valid
    private List<OrderDishDTO> generateDishList(int length) {
        List<OrderDishDTO> dishList = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            dishList.add(new OrderDishDTO(name, price, type, id, count));
        }
        return dishList;
    }

    // dont really care about the dishDTOs except for them being invalid
    private List<OrderDishDTO> generateInvalidDishList() {
        List<OrderDishDTO> dishList = new LinkedList<>();
        dishList.add(new OrderDishDTO());
        return dishList;
    }
}

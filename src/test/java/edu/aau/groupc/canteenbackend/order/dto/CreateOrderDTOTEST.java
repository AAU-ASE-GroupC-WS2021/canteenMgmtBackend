package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateOrderDTOTEST {
    final int canteenId = 1;
    final int userId = 2;
    final int wrongId = 999;
    final List<DishForOrderCreationDTO> emptyList = new LinkedList<>();

    // TODO FINISH TEST, after merging canteenBranch

    private CreateOrderDTO createOrderDTO(int userId, int canteenId, List<DishForOrderCreationDTO> dishList) {
        CreateOrderDTO dto = new CreateOrderDTO();
        dto.setDishes(dishList);
        dto.setUserId(userId);
        dto.setCanteenId(canteenId);
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
}

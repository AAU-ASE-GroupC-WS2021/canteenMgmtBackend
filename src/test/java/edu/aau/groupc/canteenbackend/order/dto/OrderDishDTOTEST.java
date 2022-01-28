package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDishDTO;
import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDishDTOTEST implements ValidationTest {

    final String name = "name";
    final Float price = 1f;
    final String type = "MAIN";
    final Integer id = 1;
    final Integer count = 1;

    // checks if both constructor types lead to the same result, and both should be valid
    @Test
    public void testCreationMethods_ThenValid() {
        OrderDishDTO allConstructorDish = new OrderDishDTO(name, price, type, id, count);
        OrderDishDTO setterDish = new OrderDishDTO();
        setterDish.setName(name);
        setterDish.setPrice(price);
        setterDish.setType(type);
        setterDish.setId(id);
        setterDish.setCount(count);
        assertThat(allConstructorDish).usingRecursiveComparison().isEqualTo(setterDish);
        assertValid(allConstructorDish);
        assertValid(setterDish);
    }

    // quick test to make sure the validation from the base class still works
    @Test
    public void testParentValidation_ThenInvalid() {
        assertInvalid(new OrderDishDTO(null, null, null, id, count));
    }

    @Test
    public void testNullId_ThenInvalid() {
        assertInvalid(new OrderDishDTO(name, price, type, null, count));
    }

    @Test
    public void testNullCount_ThenInvalid() {
        assertInvalid(new OrderDishDTO(name, price, type, id, null));
    }

    @Test
    public void testNegativeCount_ThenInvalid() {
        assertInvalid(new OrderDishDTO(name, price, type, id, -1));
    }

    @Test
    public void testZeroCount_ThenInvalid() {
        assertInvalid(new OrderDishDTO(name, price, type, id, 0));
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

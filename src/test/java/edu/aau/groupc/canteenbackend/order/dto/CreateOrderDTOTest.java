package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.dto.MenuForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateOrderDTOTest implements ValidationTest {

    final Date futureDate = new Date(new Date().getTime() + 3600 * 1000 * 3);

    @Test
    void testValid_ThenOk() {
        assertValid(createOrderDTO(1, generateDishList(2), generateMenuList(1), false, futureDate));
    }

    @Test
    void testNullCanteenId_ThenInvalid() {
        assertInvalid(createOrderDTO(null, generateDishList(2), generateMenuList(1), true, futureDate));
    }

    @Test
    void testNullDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, null, new LinkedList<>(), true, futureDate));
    }

    @Test
    void testEmptyDishAndMenuList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, new LinkedList<>(), new LinkedList<>(), false, futureDate));
    }

    // this test is only to check if the "parent" dto catches invalid states in the dtos inside the list
    @Test
    void testInvalidDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, generateInvalidDishList(), new LinkedList<>(), false, futureDate));
    }

    @Test
    void testNullMenuList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, new LinkedList<>(), null, true, futureDate));
    }

    // this test is only to check if the "parent" dto catches invalid states in the dtos inside the list
    @Test
    void testInvalidMenuList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, new LinkedList<>(), generateInvalidMenuList(), false, futureDate));
    }

    @Test
    void testDateNow_ThenInvalid() {
        assertInvalid(createOrderDTO(1, generateDishList(1), generateMenuList(1), false, new Date()));
    }


    private CreateOrderDTO createOrderDTO(Integer canteenId, List<DishForOrderCreationDTO> dishList, List<MenuForOrderCreationDTO> menuList, boolean reserveTable, Date pickupDate) {
        CreateOrderDTO dto = new CreateOrderDTO();
        dto.setDishes(dishList);
        dto.setMenus(menuList);
        dto.setCanteenId(canteenId);
        dto.setReserveTable(reserveTable);
        dto.setPickupDate(pickupDate);
        return dto;
    }

    // dont really care about the dishDTOs except for thembeing valid
    private List<MenuForOrderCreationDTO> generateMenuList(int length) {
        List<MenuForOrderCreationDTO> menuList = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            MenuForOrderCreationDTO menuDto = new MenuForOrderCreationDTO();
            menuDto.setId(i);
            menuDto.setCount(i + 1);
            menuList.add(menuDto);
        }
        return menuList;
    }

    // dont really care about the dishDTOs except for them being invalid
    private List<MenuForOrderCreationDTO> generateInvalidMenuList() {
        List<MenuForOrderCreationDTO> menuList = new LinkedList<>();
        MenuForOrderCreationDTO menuDto = new MenuForOrderCreationDTO();
        menuDto.setId(null);
        menuDto.setCount(-1);
        menuList.add(menuDto);
        return menuList;
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

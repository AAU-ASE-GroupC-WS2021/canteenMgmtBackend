package edu.aau.groupc.canteenbackend.order.endpoint;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.endpoint.OrderController;
import edu.aau.groupc.canteenbackend.orders.services.IOrderService;
import edu.aau.groupc.canteenbackend.services.IDishService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import edu.aau.groupc.canteenbackend.util.JsonTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("H2Database")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerTest extends AbstractControllerTest implements JsonTest {

    private MockMvc mvc;
    private User firstUser;
    private Canteen firstCanteen;
    private Dish firstDish;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICanteenService canteenService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IDishService dishService;

    @BeforeAll
    void setUpDB() {
        mvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
        initCanteens();
        initUsers();
        firstDish = dishService.create(new Dish("test", 2, Dish.Type.MAIN));
    }

    @Test
    void testOrderbyId_ThenReturn() throws Exception {
        Order order = createOderForUser(firstUser);
        createOderForUser(firstUser);

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                .get("/api/order-by-id?orderId=" + order.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertJsonObjectEquals(orderService.findById(order.getId()), res.getResponse().getContentAsString());
    }

    @Test
    void testOrderById_InvalidId() throws Exception {
        createOderForUser(firstUser);
        createOderForUser(firstUser);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/order-by-id?orderId=" + 9999L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testOrderById_MalformedId() throws Exception {
        createOderForUser(firstUser);
        createOderForUser(firstUser);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/order-by-id?orderId=" + "xyz543")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testOrderById_MissingId() throws Exception {
        createOderForUser(firstUser);
        createOderForUser(firstUser);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/order-by-id")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testOrdersForUser_ThenReturnsAll() throws Exception {
        createOderForUser(firstUser);
        createOderForUser(firstUser);

        // setting the user manually since the authetication skips it
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                .get("/api/order")
                .requestAttr("user", firstUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertJsonArrayEquals(orderService.findAllByUserAsDTO(firstUser), res.getResponse().getContentAsString());
    }

    @Test
    void testCreateOrder_ThenReturn() throws Exception {
        CreateOrderDTO dto = createCreateOrderDTOWithExampleDish(firstDish, 2, getValidDate(), firstCanteen.getId());
        // setting the user manually since the authetication skips it
        mvc.perform(MockMvcRequestBuilders
                .post("/api/create-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testCreateOrder_canteenNotFound() throws Exception {
        CreateOrderDTO dto = createCreateOrderDTOWithExampleDish(firstDish, 2, getValidDate(), 9999);
        mvc.perform(MockMvcRequestBuilders
                .post("/api/create-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testCreateOrder_constraints_count() throws Exception {
        CreateOrderDTO dto = createCreateOrderDTOWithExampleDish(firstDish, -1, getValidDate(), firstCanteen.getId());
        mvc.perform(MockMvcRequestBuilders
                .post("/api/create-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testCreateOrder_constraints_pickupDate() throws Exception {
        CreateOrderDTO dto = createCreateOrderDTOWithExampleDish(firstDish, 2, new Date(), firstCanteen.getId());
        mvc.perform(MockMvcRequestBuilders
                .post("/api/create-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    void initCanteens() {
        firstCanteen = createCanteen("Canteen #1", "Address #1", 11);
        createCanteen("Canteen #2", "Address #2", 22);
        createCanteen("Canteen #3", "Address #3", 33);
    }


    void initUsers() {
        firstUser = createUser("name_1");
        createUser("name_2");
        createUser("name_3");
    }

    private Canteen createCanteen(String name, String address, Integer numSeats) {
        return canteenService.create(new Canteen(name, address, numSeats));
    }

    private User createUser(String name) {
        User user = new User();
        user.setUsername(name);
        return userService.create(user);
    }

    private Order createOderForUser(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setCanteen(firstCanteen);
        return orderService.save(order);
    }

    private CreateOrderDTO createCreateOrderDTOWithExampleDish(Dish dish, Integer count, Date pickupDate, Integer canteenId) {
        List<DishForOrderCreationDTO> dishList = new LinkedList<>();
        DishForOrderCreationDTO dto = new DishForOrderCreationDTO();
        dto.setId(dish.getId());
        dto.setCount(count);
        dishList.add(dto);
        CreateOrderDTO orderDTO = new CreateOrderDTO();
        orderDTO.setCanteenId(canteenId);
        orderDTO.setPickupDate(pickupDate);
        orderDTO.setDishes(dishList);
        return orderDTO;
    }

    private Date getValidDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

}

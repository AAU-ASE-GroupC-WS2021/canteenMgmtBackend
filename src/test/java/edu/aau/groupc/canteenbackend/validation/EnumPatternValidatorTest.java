package edu.aau.groupc.canteenbackend.validation;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("H2Database")
public class EnumPatternValidatorTest extends AbstractControllerTest {

    private static MockMvc mvc;

    @BeforeAll
    static void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(new ValidationTestController())
                .setControllerAdvice(new ExceptionHandler())
                .build();
    }

    @Test
    void testValidate_invalidString_ThenInvalid() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"someEnum\": \"invalid\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testValidate_nullString_ThenInvalid() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"someEnum\": null}"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testValidate_validString_ThenOk() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"someEnum\": \"MAIN\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }
}

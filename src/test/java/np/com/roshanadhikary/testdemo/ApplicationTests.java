package np.com.roshanadhikary.testdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import np.com.roshanadhikary.testdemo.bootstrap.H2Bootstrap;
import np.com.roshanadhikary.testdemo.entity.Battery;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldFetchBatteryID1() throws Exception {
    mockMvc
            .perform(get("/batteries/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(H2Bootstrap.mockBatteries.get(0).getName()))
            .andExpect(jsonPath("$.postcode").value(H2Bootstrap.mockBatteries.get(0).getPostcode()))
            .andExpect(jsonPath("$.capacity").value(H2Bootstrap.mockBatteries.get(0).getCapacity()));
  }

  @Test
  void shouldReturnAllBatteries() throws Exception {
    mockMvc
            .perform(get("/batteries/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", Matchers.hasSize(H2Bootstrap.mockBatteries.size())));
  }

  @Test
  void shouldPersistBattery() throws Exception {
    Battery mockBattery =
            new Battery("Gold Coast Mc", "9729", 50000);

    mockMvc
            .perform(
                    post("/batteries")
                            .content(objectMapper.writeValueAsString(mockBattery))
                            .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(mockBattery.getName()))
            .andExpect(jsonPath("$.postcode").value(mockBattery.getPostcode()))
            .andExpect(jsonPath("$.capacity").value(mockBattery.getCapacity()));
  }

  // negative tests

  @Test
  void shouldReturn404ForNonExistentBatteryID() throws Exception {
    int id = 102;
    mockMvc
            .perform(get("/batteries/" + id))
            .andDo(print())
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestForInvalidRange() throws Exception {
    String start = "abcd";
    String end = "#123";

    mockMvc
            .perform(get(String.format("/batteries/postcode?start=%s&end=%s", start, end)))
            .andDo(print())
            .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestForNaNID() throws Exception {
    String id = "abc";
    mockMvc
            .perform(get(String.format("/batteries/%s", id)))
            .andDo(print())
            .andExpect(status().isBadRequest());
  }
}

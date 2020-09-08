package fr.ing.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ing.interview.dto.TransactionInputDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAccountBalanceNotFoundTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/account/balance/{customerId}/{accountId}", "1", "0000000001")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAccountBalanceTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/account/balance/{customerId}/{accountId}", "0000000001", "0000000001")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void depositAccountTestNotFound() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/account/deposit")
                .content(new ObjectMapper().writeValueAsString(
                        new TransactionInputDTO("1", "0000000001", BigDecimal.TEN )))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void depositAccountTestBadRequest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/account/deposit")
                .content(new ObjectMapper().writeValueAsString(
                        new TransactionInputDTO("0000000001", "0000000002", BigDecimal.ZERO )))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void depositAccountTestOk() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/account/deposit")
                .content(new ObjectMapper().writeValueAsString(
                        new TransactionInputDTO("0000000001", "0000000001", BigDecimal.TEN )))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void withdrawAccountTestNotFound() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/account/withdraw")
                .content(new ObjectMapper().writeValueAsString(
                        new TransactionInputDTO("1", "0000000001", BigDecimal.TEN )))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void withdrawAccountTestBadRequest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/account/withdraw")
                .content(new ObjectMapper().writeValueAsString(
                        new TransactionInputDTO("0000000001", "0000000002", BigDecimal.TEN )))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void withdrawAccountTestOk() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/account/withdraw")
                .content(new ObjectMapper().writeValueAsString(
                        new TransactionInputDTO("0000000001", "0000000001", BigDecimal.TEN )))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}

package com.hunsley.async.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunsley.async.Account;
import com.hunsley.async.AccountType;
import com.hunsley.async.aggregator.controller.ConsolidatedAccountController;
import com.hunsley.async.aggregator.service.AccountConsolidationService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 *    Creates an HSQLDB instance for the duration of the test which is populated with Accounts.
 *
 *    Annotated with the {@link SpringBootTest} and {@link AutoConfigureMockMvc} rather than the {@link WebMvcTest}
 *    becasuse we want a full stack to the HSQLDB rather than just the controller tier. {@link WebMvcTest} will
 *    only instantiate the controllers and not the full application context. As we're testing a Spring Data REST
 *    controller which is created dynamically from the {@link com.hunsley.async.services.controller.AccountRepository}
 *    we cannot explicitly define the controller type.
 * </p>
 * <p>
 *     The order of tests is important here because we want to write some data in one test first then
 *     read it back again in a later test. {@link FixMethodOrder} annotation ensures the methods are run
 *     in Lexicographical order and so the methods are named accordingly
 * </p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServicesIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    static AccountType TYPE;
    static Double BALANCE;

    /**
     * <p>
     *     {@link BeforeClass} Ensures this data is initialized once before all tests, rather than before each test
     * </p>
     */
    @BeforeClass
    public static void init() {
        TYPE = AccountType.CURRENT;
        BALANCE = new Random().nextDouble();
    }

    /**
     * <p>
     *     Save a new {@link Account} as JSON via a POST method
     * </p>
     * @throws Exception
     */
    @Test
    public void testASaveAccounts() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(new Account(TYPE, BALANCE));
        mockMvc.perform(
                post("/accounts")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());
    }

    /**
     * <p>
     *     Search based on the {@link AccountType}
     *
     *     accounts/search/findByAccountType?type=ISA
     * </p>
     * @throws Exception
     */
    @Test
    public void testBReadAccount() throws Exception {
        mockMvc.perform(
                get("/accounts/search/findByAccountType")
                        .param("type", TYPE.name()))
                .andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._embedded.accounts[0].accountType").value(TYPE.name()))
                .andExpect(jsonPath("$._embedded.accounts[0].balance").value(BALANCE));
        }
}

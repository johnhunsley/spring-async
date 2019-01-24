package com.hunsley.async.aggregator;

import com.hunsley.async.Account;
import com.hunsley.async.AccountType;
import com.hunsley.async.services.controller.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class AggregatorIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;


    /**
     * <p>
     *     Add some accounts of different types to the HSQLDB
     * </p>
     */
    @Before
    public void initTestData() {

        for(AccountType type : AccountType.values()) {
            accountRepository.save(new Account(type, new Random().nextDouble()));
        }

    }

    @Test
    public void testGetAccounts() throws Exception {
        mockMvc.perform(get("/asyncbank/accounts")).andDo(print()).andExpect(status().isOk());
    }

}

package com.hunsley.async.aggregator;

import com.hunsley.async.Account;
import com.hunsley.async.AccountType;
import com.hunsley.async.aggregator.client.AccountClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConsolidatedAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountClient accountClient;

    /**
     * <p>
     *     Ask the Mock {@link AccountClient} to return some dummy data in a completed {@link CompletableFuture}
     * </p>
     */
    @Before
    public void initTestData() throws InterruptedException {
        for(AccountType type : AccountType.values()) {
            List<Account> accounts = new LinkedList<>();
            //todo add some {@link Account} instances here
            CompletableFuture<List<Account>> future = new CompletableFuture<>();
            future.complete(accounts);
            when(accountClient.getAccounts(type)).thenReturn(future);
        }

    }

    @Test
    public void testGetAccounts() throws Exception {
        mockMvc.perform(get("/asyncbank/accounts")).andDo(print()).andExpect(status().isOk());
        //todo check the content within the response JSON body
    }

}

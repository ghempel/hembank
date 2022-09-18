package com.hempel.hembank.resource;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hempel.hembank.service.AccountService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountResourceTest {

    private MockMvc mockMvc;

    private final AccountService serviceMock = mock(AccountService.class);

    @InjectMocks
    private AccountResource resource;

    @Before
    public void setup() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    public void findById() throws Exception {

        UUID idAccount = UUID.fromString("e86046ec-7c95-46a9-addb-b3598956948f");

        mockMvc.perform(get("/api/accounts/e86046ec-7c95-46a9-addb-b3598956948f")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).findById(eq(idAccount));
        verifyNoMoreInteractions(serviceMock);
    }

}

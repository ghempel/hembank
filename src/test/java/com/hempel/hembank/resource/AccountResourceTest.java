package com.hempel.hembank.resource;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hempel.hembank.domain.Account;
import com.hempel.hembank.repository.AccountRepository;
import com.hempel.hembank.service.AccountService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountResource.class)
public class AccountResourceTest {

    @MockBean
    private AccountService serviceMock;

    @MockBean
    private AccountRepository repositoryMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findById() throws Exception {

        UUID idAccount = UUID.fromString("e86046ec-7c95-46a9-addb-b3598956948f");

        Account account = Account.of(idAccount);
        account.setDocumentNumber("01234567890");

        when(serviceMock.findById(eq(idAccount))).thenReturn(account);

        mockMvc.perform(get("/api/accounts/e86046ec-7c95-46a9-addb-b3598956948f")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("e86046ec-7c95-46a9-addb-b3598956948f")))
                .andExpect(jsonPath("$.documentNumber", equalTo("01234567890")));

        verify(serviceMock, times(1)).findById(eq(idAccount));
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void findAll() throws Exception {

        Account account = Account.of(UUID.fromString("e86046ec-7c95-46a9-addb-b3598956948f"));
        account.setDocumentNumber("01234567890");

        when(repositoryMock.findAll()).thenReturn(List.of(account));

        mockMvc.perform(get("/api/accounts/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", equalTo("e86046ec-7c95-46a9-addb-b3598956948f")))
                .andExpect(jsonPath("[0].documentNumber", equalTo("01234567890")));

        verify(repositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void save() throws Exception {

        Account account = new Account();
        account.setDocumentNumber("01234567890");

        this.mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        verify(serviceMock, times(1)).save(account);
        verifyNoMoreInteractions(serviceMock);
    }

}

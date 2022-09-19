package com.hempel.hembank.resource;

import java.math.BigDecimal;
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
import com.hempel.hembank.dto.TransactionDTO;
import com.hempel.hembank.service.TransactionService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionResource.class)
public class TransactionResourceTest {

    @MockBean
    private TransactionService serviceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createTransaction() throws Exception {

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setOperationTypeId(4L);
        transactionDTO.setAccountId(UUID.randomUUID());
        transactionDTO.setAmount(BigDecimal.ONE);

        this.mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        verify(serviceMock, times(1)).createTransaction(transactionDTO);
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void createTransactionArgumentNotValid() throws Exception {

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setOperationTypeId(4L);
        transactionDTO.setAccountId(UUID.randomUUID());

        this.mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("Validation Error")))
                .andExpect(jsonPath("$.fieldsErros[0].field", equalTo("amount")))
                .andExpect(jsonPath("$.fieldsErros[0].message", equalTo("must not be null")));

        verifyNoInteractions(serviceMock);
    }

}

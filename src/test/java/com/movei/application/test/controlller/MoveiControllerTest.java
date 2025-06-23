package com.movei.application.test.controlller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movei.controller.MoveiController;
import com.movei.model.CustomerDetails;
import com.movei.model.TicketDetails;
import com.movei.model.TransactioRequest;
import com.movei.model.TransactionResponse;
import com.movei.service.MoveiService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;


import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MoveiController.class)
public class MoveiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoveiService moveiService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200WithValidResponse() throws Exception {

        List<CustomerDetails> customers = List.of(
                new CustomerDetails("Sid",30)// Adult
        );

        TransactioRequest request = new TransactioRequest(1,customers );
        // Set up request data as needed
        List<TicketDetails> tickets = List.of(
                new TicketDetails("Adult",1,25)
        );
        TransactionResponse mockResponse = new TransactionResponse(1, tickets, 25.0);

        Mockito.when(moveiService.calculate(Mockito.any())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

}

package com.movei.application.test.controlller;

import com.movei.controller.MoveiController;
import com.movei.model.CustomerDetails;
import com.movei.model.TicketDetails;
import com.movei.model.TransactioRequest;
import com.movei.model.TransactionResponse;
import com.movei.service.MoveiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MoveiControllerTest {

    @Mock
    private MoveiService moveiService;

    @InjectMocks
    private MoveiController moveiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculate_success() {

        List<CustomerDetails> customers = List.of(
                new CustomerDetails("Sid", 30)    // adult amount 25
        );
        TransactioRequest request = new TransactioRequest(1, customers);
        List<TicketDetails> tickets = List.of(
                new TicketDetails("Adult", 1, 25)
        );
        TransactionResponse mockResponse = new TransactionResponse(1, tickets, 25.0);
        when(moveiService.calculate(request)).thenReturn(mockResponse);

        ResponseEntity<?> result = moveiController.calculateTicketPrice(request);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    void testCalculate_illegalArgumentException() {

        List<CustomerDetails> customers = List.of(
                new CustomerDetails("Sid", -5)// Adult
        );
        TransactioRequest request = new TransactioRequest(1, customers);
        when(moveiService.calculate(request)).thenThrow(new IllegalArgumentException("Invalid input"));

        ResponseEntity<?> result = moveiController.calculateTicketPrice(request);

        assertEquals(400, result.getStatusCodeValue());
        assertEquals("Invalid input", result.getBody());
    }

}

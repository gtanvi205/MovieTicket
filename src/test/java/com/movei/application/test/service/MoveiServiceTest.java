package com.movei.application.test.service;

import com.movei.model.CustomerDetails;
import com.movei.model.TicketDetails;
import com.movei.model.TransactioRequest;
import com.movei.model.TransactionResponse;
import com.movei.service.MoveiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveiServiceTest {
    private MoveiService moveiService;

    @BeforeEach
    void setUp() {
        moveiService = new MoveiService();
    }
    @Test
    void testCalculate_WithAdult() {
        List<CustomerDetails> customers = List.of(new CustomerDetails("Tom",30));
        TransactioRequest request = new TransactioRequest(1, customers);

        TransactionResponse response = moveiService.calculate(request);

        assertEquals(1, response.getTransactionId());
        assertEquals(1, response.getTicket().size());
        assertEquals(25.0, response.getTotalCost());

        TicketDetails ticket = response.getTicket().get(0);
        assertEquals("Adult", ticket.getTicketType());
        assertEquals(1, ticket.getQuantity());
        assertEquals(25.0, ticket.getTotalCost());
    }

    @Test
    void testCalculate_WithAges() {
        List<CustomerDetails> customers = List.of(
                new CustomerDetails("Neha",10),// Children
                new CustomerDetails("Tim",17), // Teen
                new CustomerDetails("Sid",30), // Adult
                new CustomerDetails("Paul",70)  // Senior
        );

        TransactioRequest request = new TransactioRequest(4, customers);
        TransactionResponse response = moveiService.calculate(request);

        assertEquals(4, response.getTicket().stream().map(TicketDetails::getTicketType).distinct().count());
        assertEquals(25 + 12 + 5 + 17.5, response.getTotalCost(), 0.01);
    }

    @Test
    void testCalculate_WithNullCustomer() {
        TransactioRequest request = new TransactioRequest(1, null);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                moveiService.calculate(request)
        );
        assertEquals("Request or customer details cannot be null.", ex.getMessage());
    }
    @Test
    void testCalculate_WithNegativeAge() {
        List<CustomerDetails> customers = List.of(new CustomerDetails("John",-5));
        TransactioRequest request = new TransactioRequest(1, customers);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                moveiService.calculate(request)
        );

        assertEquals("Invalid age in customer detail: -5", ex.getMessage());
    }

}

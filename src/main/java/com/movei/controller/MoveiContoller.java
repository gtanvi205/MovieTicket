package com.movei.controller;

import com.movei.model.TransactioRequest;
import com.movei.model.TransactionResponse;
import com.movei.service.MoveiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
@Tag(name = "Movie Tickets", description = "API for calculating movie ticket prices")
public class MoveiContoller {

    @Autowired
    MoveiService moveiService;

    @PostMapping
    @Operation(summary = "Calculate ticket costs for a transaction")
    public ResponseEntity<?> calculateTicketPrice(@RequestBody TransactioRequest request) {
       try{
           TransactionResponse response= moveiService.calculate(request);
           return ResponseEntity.ok(response);
       }catch (IllegalArgumentException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }catch (Exception e)
       {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
       }
    }
}

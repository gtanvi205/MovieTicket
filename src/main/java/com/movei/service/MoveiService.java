package com.movei.service;

import com.movei.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MoveiService {
    private static final Logger logger = LoggerFactory.getLogger(MoveiService.class);

    private static final double ADULT_PRICE = 25.0;
    private static final double SENIOR_DISCOUNT = 0.30;
    private static final double TEEN_PRICE = 12.0;
    private static final double CHILD_PRICE = 5.0;
    private static final double CHILD_DISCOUNT = 0.25;

    public TransactionResponse calculate(TransactioRequest request) {
        try {
            if (request == null || request.getCustomerDetails() == null) {
                throw new IllegalArgumentException("Request or customer details cannot be null.");
            }

            Map<TicketType, Integer> countMap = new EnumMap<>(TicketType.class);

            for (CustomerDetails customer : request.getCustomerDetails()) {
                if (customer == null) {
                    throw new IllegalArgumentException("Customer detail cannot be null.");
                }
                if (customer.getAge() < 0) {
                    throw new IllegalArgumentException("Invalid age in customer detail: " + customer.getAge());
                }

                TicketType type = classify(customer.getAge());
                countMap.put(type, countMap.getOrDefault(type, 0) + 1);
                logger.info("customer age {} and type {}", customer.getAge(), type);

            }
            List<TicketDetails> details = new ArrayList<>();
            double totalCost = 0;

            for (TicketType type : TicketType.values()) {

                int quantity = countMap.getOrDefault(type, 0);
                if (quantity > 0) {
                    double price = getPrice(type, quantity);
                    details.add(new TicketDetails(type.name(), quantity, price));
                    logger.info("Ticket type: {}, Quantity: {}, Price: {}", type, quantity, price);
                    totalCost += price;
                }
            }
            logger.info("Total cost calculated: {}", totalCost);
            details.sort(Comparator.comparing(TicketDetails::getTicketType));
            return new TransactionResponse(request.getTransactionId(), details, totalCost);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred during calculation.", e);
        }
    }

        private TicketType classify(int age) {
            if (age < 11) return TicketType.Children;
            else if (age < 18) return TicketType.Teen;
            else if (age < 65) return TicketType.Adult;
            else return TicketType.Senior;
        }


        private double getPrice(TicketType type, int quantity) {
            switch (type) {
                case Adult:
                    return quantity * ADULT_PRICE;
                case Senior:
                    return quantity * ADULT_PRICE * (1 - SENIOR_DISCOUNT);
                case Teen:
                    return quantity * TEEN_PRICE;
                case Children:
                    double price = quantity * CHILD_PRICE;
                    if (quantity >= 3) price *= (1 - CHILD_DISCOUNT);
                    return price;
                default:
                    return 0;
            }
        }
}

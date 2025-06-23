package com.movei.model;
import java.util.List;

public class TransactionResponse {
    private int transactionId;
    private List<TicketDetails> ticket;
    private double totalCost;

    public TransactionResponse(int transactionId, List<TicketDetails> ticket, double totalCost) {
        this.transactionId = transactionId;
        this.ticket = ticket;
        this.totalCost = totalCost;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public List<TicketDetails> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketDetails> ticket) {
        this.ticket = ticket;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}

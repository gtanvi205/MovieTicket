package com.movei.model;

public class TicketDetails {
    private String ticketType;
    private int quantity;
    private double totalCost;

    public TicketDetails(String ticketType, int quantity, double totalCost) {
        this.ticketType = ticketType;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}

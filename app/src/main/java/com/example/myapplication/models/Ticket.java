package com.example.myapplication.models;

import java.util.Date;
import java.util.List;

public class Ticket {
    private String id;
    private String userId;
    private String showtimeId;
    private List<String> seatNumbers;
    private double totalAmount;
    private Date bookingDate;
    private String status; // e.g., "BOOKED", "CANCELLED"

    public Ticket() {}

    public Ticket(String id, String userId, String showtimeId, List<String> seatNumbers, double totalAmount, Date bookingDate, String status) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getShowtimeId() { return showtimeId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }

    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

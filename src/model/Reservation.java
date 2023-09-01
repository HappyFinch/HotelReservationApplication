package model;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation() {
    }

    public Reservation(Customer customer,IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.isValidDate(checkInDate, checkOutDate);
    }

    private void isValidDate(LocalDate checkInDate, LocalDate checkOutDate) {
        if(checkInDate.isBefore(checkOutDate) && !checkInDate.isBefore(LocalDate.now())){
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }else {
            throw new IllegalArgumentException("check problems:\n" +
                    "1.The check-out date cannot be greater than the check-in date\n" +
                    "2.The check-in date cannot be earlier than today");
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public IRoom getRoom() {return room;}

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer +
                ", room=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}

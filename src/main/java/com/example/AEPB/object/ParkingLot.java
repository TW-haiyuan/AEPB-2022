package com.example.AEPB.object;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import com.example.AEPB.exception.NoCarException;
import com.example.AEPB.exception.NoTicketException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ParkingLot {
    private final int size;
    private final List<Car> carList;
    private final List<Ticket> ticketList;

    public ParkingLot(int size) {
        this.size = size;
        this.carList = new ArrayList<>(size);
        this.ticketList = new ArrayList<>(size);
    }

    public Ticket parkingCar(Car car) throws CarExistException, NoCarException {
        if (Objects.isNull(car)) {
            throw new NoCarException("This is not a car!");
        }

        if (hasNoSpace()) {
            return null;
        }

        if (carList.contains(car)) {
            throw new CarExistException("This car is already exist in parking lot!");
        }

        carList.add(car);
        Ticket ticket = new Ticket(car.getCarPlateNumber());
        ticketList.add(ticket);

        return ticket;
    }

    public boolean hasNoSpace() {
        return carList.size() >= size;
    }

    public Car pickUpCar(Ticket ticket) throws CarNotExistException, NoTicketException {
        if (Objects.isNull(ticket)) {
            throw new NoTicketException("There's no ticket!");
        }

        if (!ticketList.contains(ticket)) {
            return null;
        }

        String carPlateNumber = ticket.getCarPlateNumber();
        Car returnCar = carList.stream()
                .filter(car -> carPlateNumber.equals(car.getCarPlateNumber()))
                .findFirst()
                .orElseThrow(() -> new CarNotExistException("This car is not exist in parking lot!"));

        carList.remove(returnCar);
        ticketList.remove(ticket);
        return returnCar;
    }

    public int remainingParkingSpace() {
        return size - carList.size();
    }
}

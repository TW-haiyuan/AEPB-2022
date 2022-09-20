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
    public static final int PARKING_SPACE_SIZE = 5;

    private final List<Car> parkingLot = new ArrayList<>(PARKING_SPACE_SIZE);
    private final List<Ticket> ticketList = new ArrayList<>(PARKING_SPACE_SIZE);

    public Ticket parkingCar(Car car) throws CarExistException, NoCarException {
        if(Objects.isNull(car)) {
            throw new NoCarException("This is not a car!");
        }

        if ( parkingLot.size() >= PARKING_SPACE_SIZE) {
            return null;
        }

        if (parkingLot.contains(car)) {
            throw new CarExistException("This car is already exist in parking lot!");
        }

        parkingLot.add(car);
        Ticket ticket = new Ticket(car.getCarPlateNumber());
        ticketList.add(ticket);

        return ticket;
    }

    public Car pickUpCar(Ticket ticket) throws CarNotExistException, NoTicketException {
        if(Objects.isNull(ticket)) {
            throw new NoTicketException("There's no ticket!");
        }

        if ( !ticketList.contains(ticket)) {
            return null;
        }

        String carPlateNumber = ticket.getCarPlateNumber();
        Car car  = new Car(carPlateNumber);
        if (!parkingLot.contains(car)) {
            throw new CarNotExistException("This car is not exist in parking lot!");
        }

        parkingLot.remove(car);
        ticketList.remove(ticket);
        return car;
    }
}

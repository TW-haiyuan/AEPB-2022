package com.example.AEPB.object;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import com.example.AEPB.exception.NoCarException;
import com.example.AEPB.exception.NoTicketException;

import java.util.List;
import java.util.Objects;

public class ParkingBrother {

    private final List<ParkingLot> parkingLotList;

    public ParkingBrother(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public Ticket parkingCar(Car car) throws NoCarException, CarExistException {

        for (ParkingLot parkingLot : parkingLotList) {
            Ticket ticket = parkingLot.parkingCar(car);
            if (Objects.nonNull(ticket)) {
                return ticket;
            }
        }
        return null;
    }

    public Car pickUpCar(Ticket ticket) throws NoTicketException, CarNotExistException {

        for (ParkingLot parkingLot : parkingLotList) {
            Car car = parkingLot.pickUpCar(ticket);
            if (Objects.nonNull(car)) {
                return car;
            }
        }
        return null;
    }
}

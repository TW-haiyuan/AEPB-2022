package com.example.AEPB.object;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import com.example.AEPB.exception.NoCarException;
import com.example.AEPB.exception.NoTicketException;
import lombok.Getter;

import java.util.List;

@Getter
public class ParkingBrother {

    private final List<ParkingLot> parkingLotList = List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3));

    public Ticket parkingCar(Car car) throws NoCarException, CarExistException {
        for (ParkingLot parkingLot : parkingLotList) {
            if (!parkingLot.hasNoSpace()) {
                return parkingLot.parkingCar(car);
            }
        }
        return null;
    }

    public Car pickUpCar(Ticket ticket) throws NoTicketException, CarNotExistException {
        for (ParkingLot parkingLot : parkingLotList) {
            if (parkingLot.getTicketList().contains(ticket)) {
                return parkingLot.pickUpCar(ticket);
            }
        }
        return null;
    }
}

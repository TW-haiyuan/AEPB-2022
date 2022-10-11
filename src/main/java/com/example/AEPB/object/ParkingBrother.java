package com.example.AEPB.object;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.NoCarException;

import java.util.List;
import java.util.Objects;

public class ParkingBrother extends BaseParkingBrother{

    private final List<ParkingLot> parkingLotList;

    public ParkingBrother(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
        this.parkingLotList = parkingLotList;
    }

    @Override
    public Ticket parkingCar(Car car) throws NoCarException, CarExistException {

        for (ParkingLot parkingLot : parkingLotList) {
            Ticket ticket = parkingLot.parkingCar(car);
            if (Objects.nonNull(ticket)) {
                return ticket;
            }
        }
        return null;
    }


}

package com.example.AEPB.object;

import com.example.AEPB.exception.*;

import java.util.List;
import java.util.Objects;

public abstract class BaseParkingBrother {
    private final List<ParkingLot> parkingLotList;

    BaseParkingBrother(List<ParkingLot> parkingLotList) {
        super();
        this.parkingLotList = parkingLotList;
    }

    public Ticket parkingCar(Car car) throws NoCarException, CarExistException {
        int maxEmptySpace = 0;
        int parkingLotIndexWithMostEmptySpace = -1;
        for (int i = 0; i < parkingLotList.size(); i++) {
            int remainingParkingSpace = parkingLotList.get(i).remainingParkingSpace();
            if (remainingParkingSpace > maxEmptySpace) {
                maxEmptySpace = remainingParkingSpace;
                parkingLotIndexWithMostEmptySpace = i;
            }
        }
        if (parkingLotIndexWithMostEmptySpace >= 0) {
            return parkingLotList.get(parkingLotIndexWithMostEmptySpace).parkingCar(car);
        }
        return null;
    }

    public Car pickUpCar(Ticket ticket) throws NoTicketException, CarNotExistException, PickUpCarFailedException {

        for (ParkingLot parkingLot : parkingLotList) {
            Car car = parkingLot.pickUpCar(ticket);
            if (Objects.nonNull(car)) {
                return car;
            }
        }
        return null;
    }
}

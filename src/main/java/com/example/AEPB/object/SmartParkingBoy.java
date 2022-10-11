package com.example.AEPB.object;


import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.NoCarException;

import java.util.List;

public class SmartParkingBoy extends ParkingBrother {

    private final List<ParkingLot> parkingLotList;

    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
        this.parkingLotList = parkingLotList;
    }

    @Override
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
}

package com.example.AEPB.object;


import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.NoCarException;

import java.util.List;

public class SmartParkingBoy extends ParkingBrother {

    public Ticket parkingCar(Car car) throws NoCarException, CarExistException {
        int maxEmptySpace = -1;
        int parkingLotIndexWithMostEmptySpace = -1;
        List<ParkingLot> parkingLotList = getParkingLotList();
        for (int i = 0; i < parkingLotList.size(); i++) {
            if (parkingLotList.get(i).remainingParkingSpace() > maxEmptySpace) {
                maxEmptySpace = parkingLotList.get(i).remainingParkingSpace();
                parkingLotIndexWithMostEmptySpace = i;
            }
        }
        if (parkingLotIndexWithMostEmptySpace >= 0) {
            return parkingLotList.get(parkingLotIndexWithMostEmptySpace).parkingCar(car);
        }
        return null;
    }
}

package com.example.AEPB.object;

import com.example.AEPB.exception.PickUpCarFailedException;

import java.util.List;

public class Robot extends BaseParkingBrother {

    public Robot(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    public Car pickUpCar(Ticket ticket) throws PickUpCarFailedException {
        throw new PickUpCarFailedException("Pick up car failed!");
    }
}

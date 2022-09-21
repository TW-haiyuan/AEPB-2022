package com.example.AEPB;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import com.example.AEPB.exception.NoCarException;
import com.example.AEPB.exception.NoTicketException;
import com.example.AEPB.object.Car;
import com.example.AEPB.object.ParkingBrother;
import com.example.AEPB.object.ParkingLot;
import com.example.AEPB.object.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkingBrotherTest {

    private static final String CAR_PLATE_NUMBER = "1234";

    ParkingBrother parkingBrother = new ParkingBrother();

    @Test
    void should_park_success_in_order_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_not_exist_this_car() throws CarExistException, NoCarException {
        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = parkingBrother.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
    }

    @Test
    void should_pick_up_success_when_pick_up_car_given_a_ticket_and_car_exists_in_parking_lot_and_ticket_exist_and_valid() throws CarNotExistException, NoTicketException {
        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        List<ParkingLot> parkingLotList = parkingBrother.getParkingLotList();
        parkingLotList.get(0).getCarList().add(car);
        parkingLotList.get(0).getTicketList().add(ticket);

        Car pickUpCar = parkingBrother.pickUpCar(ticket);

        assertEquals(CAR_PLATE_NUMBER, pickUpCar.getCarPlateNumber());
    }

    @Test
    void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space() throws CarExistException, NoCarException {

    }

    @Test
    void should_throw_exception_when_park_car_given_no_car() {

    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_invalid_or_fake() throws CarNotExistException, NoTicketException {

    }

    @Test
    void should_throw_exception_when_pick_up_car_given_no_ticket() {

    }

    @Test
    void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car() {

    }

    @Test
    void should_throw_exception_when_pick_up_car_given_a_ticket_and_parking_lot_not_exist_this_car() {

    }
}

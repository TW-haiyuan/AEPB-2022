package com.example.AEPB;

import com.example.AEPB.exception.*;
import com.example.AEPB.object.Car;
import com.example.AEPB.object.ParkingBrother;
import com.example.AEPB.object.ParkingLot;
import com.example.AEPB.object.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingBrotherTest {

    private static final String CAR_PLATE_NUMBER = "1234";

    @Test
    void should_park_success_in_order_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_not_exist_this_car() throws CarExistException, NoCarException {
        ParkingBrother parkingBrother = new ParkingBrother(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = parkingBrother.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
    }

    @Test
    void should_pick_up_success_when_pick_up_car_given_a_ticket_and_car_exists_in_parking_lot_and_ticket_exist_and_valid() throws CarNotExistException, NoTicketException, NoCarException, CarExistException, PickUpCarFailedException {
        ParkingBrother parkingBrother = new ParkingBrother(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = parkingBrother.parkingCar(car);

        Car pickUpCar = parkingBrother.pickUpCar(ticket);

        assertEquals(CAR_PLATE_NUMBER, pickUpCar.getCarPlateNumber());
    }

    @Test
    void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space() throws CarExistException, NoCarException {
        ParkingBrother parkingBrother = new ParkingBrother(List.of(new ParkingLot(0), new ParkingLot(0),
                new ParkingLot(0)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Ticket ticket = parkingBrother.parkingCar(car);

        assertNull(ticket);
    }

    @Test
    void should_throw_exception_when_park_car_given_no_car() {
        ParkingBrother parkingBrother = new ParkingBrother(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        assertThrows(NoCarException.class, () -> parkingBrother.parkingCar(null), "This is not a car!");
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_invalid_or_fake() throws CarNotExistException, NoTicketException, PickUpCarFailedException {
        ParkingBrother parkingBrother = new ParkingBrother(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Car car = parkingBrother.pickUpCar(ticket);
        assertNull(car);
    }

    @Test
    void should_throw_exception_when_pick_up_car_given_no_ticket() {
        ParkingBrother parkingBrother = new ParkingBrother(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        assertThrows(NoTicketException.class, () -> parkingBrother.pickUpCar(null), "There's no ticket!");
    }

    @Test
    void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car() throws NoCarException, CarExistException {
        ParkingBrother parkingBrother = new ParkingBrother(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        parkingBrother.parkingCar(car);

        assertThrows(CarExistException.class, () -> parkingBrother.parkingCar(car), "This car is already exist in parking lot!");
    }
}

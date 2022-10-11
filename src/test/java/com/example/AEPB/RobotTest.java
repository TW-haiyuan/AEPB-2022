package com.example.AEPB;

import com.example.AEPB.exception.*;
import com.example.AEPB.object.Car;
import com.example.AEPB.object.ParkingLot;
import com.example.AEPB.object.Robot;
import com.example.AEPB.object.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    private static final String CAR_PLATE_NUMBER = "1234";

    @Test
    void should_park_success_when_park_car_given_a_car_and_one_parking_lot() throws NoCarException, CarExistException {
        Robot robot = new Robot(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = robot.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());

    }

    @Test
    void should_park_success_in_parking_lot_B_when_park_car_given_a_car_and_parking_lot_B_has_most_empty_spaces() throws NoCarException, CarExistException, NoTicketException, CarNotExistException {
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Robot robot = new Robot(List.of(parkingLot1, parkingLot2));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = robot.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals(car, parkingLot2.pickUpCar(ticket));
    }

    @Test
    void should_park_success_in_parking_lot_A_when_park_car_given_a_car_and_parking_lot_A_has_most_empty_spaces() throws NoCarException, CarExistException, NoTicketException, CarNotExistException {
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(1);
        Robot robot = new Robot(List.of(parkingLot1, parkingLot2));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = robot.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals(car, parkingLot1.pickUpCar(ticket));
    }

    @Test
    void should_park_success_in_order_when_park_car_given_a_car_and_parking_lot_has_equal_empty_space() throws NoCarException, CarExistException, NoTicketException, CarNotExistException {
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Robot robot = new Robot(List.of(parkingLot1, parkingLot2));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = robot.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals(car, parkingLot1.pickUpCar(ticket));
    }

    @Test
    void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space() throws CarExistException, NoCarException {
        Robot robot = new Robot(List.of(new ParkingLot(0), new ParkingLot(0)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Ticket ticket = robot.parkingCar(car);

        assertNull(ticket);
    }

    @Test
    void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car() throws NoCarException, CarExistException {
        Robot robot = new Robot(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        robot.parkingCar(car);

        assertThrows(CarExistException.class, () -> robot.parkingCar(car), "This car is already exist in parking lot!");
    }

    @Test
    void should_throw_exception_when_park_car_given_no_car() {
        Robot robot = new Robot(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        assertThrows(NoCarException.class, () -> robot.parkingCar(null), "This is not a car!");
    }

    @Test
    void should_throw_exception_when_pick_up_car() {
        Robot robot = new Robot(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));
        assertThrows(PickUpCarFailedException.class, () -> robot.pickUpCar(null), "Pick up car failed!");
        assertThrows(PickUpCarFailedException.class, () -> robot.pickUpCar(new Ticket()), "Pick up car failed!");
    }
}

package com.example.AEPB;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import com.example.AEPB.exception.NoCarException;
import com.example.AEPB.exception.NoTicketException;
import com.example.AEPB.object.Car;
import com.example.AEPB.object.ParkingLot;
import com.example.AEPB.object.SmartParkingBoy;
import com.example.AEPB.object.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmartParkingBoyTest {

    private static final String CAR_PLATE_NUMBER = "1234";

    @Test
    void should_park_success_when_park_car_given_a_car_and_one_parking_lot() throws NoCarException, CarExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
    }

    @Test
    void should_park_success_in_parking_lot_with_most_empty_spaces_when_park_car_given_a_car_and_multiple_parking_lots() throws NoCarException, CarExistException, NoTicketException, CarNotExistException {
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(2);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(parkingLot1, parkingLot2));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals(car, parkingLot2.pickUpCar(ticket));
    }

    @Test
    void should_park_success_in_order_when_park_car_given_a_car_and_parking_lot_has_equal_empty_space() throws NoCarException, CarExistException, NoTicketException, CarNotExistException {
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(parkingLot1, parkingLot2));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertEquals(car, parkingLot1.pickUpCar(ticket));
    }

    @Test
    void should_pick_up_success_when_pick_up_car_given_a_ticket() throws NoTicketException, CarNotExistException, NoCarException, CarExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = smartParkingBoy.parkingCar(car);

        Car pickUpCar = smartParkingBoy.pickUpCar(ticket);

        assertEquals(CAR_PLATE_NUMBER, pickUpCar.getCarPlateNumber());
    }

    @Test
    void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space() throws CarExistException, NoCarException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(new ParkingLot(0), new ParkingLot(0)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertNull(ticket);
    }

    @Test
    void should_throw_exception_when_park_car_given_no_car() {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        assertThrows(NoCarException.class, () -> smartParkingBoy.parkingCar(null), "This is not a car!");
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_invalid_or_fake() throws CarNotExistException, NoTicketException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Car car = smartParkingBoy.pickUpCar(ticket);
        assertNull(car);
    }

    @Test
    void should_throw_exception_when_pick_up_car_given_no_ticket() {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        assertThrows(NoTicketException.class, () -> smartParkingBoy.pickUpCar(null), "There's no ticket!");
    }

    @Test
    void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car() throws NoCarException, CarExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(List.of(new ParkingLot(5), new ParkingLot(2), new ParkingLot(3)));

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        smartParkingBoy.parkingCar(car);

        assertThrows(CarExistException.class, () -> smartParkingBoy.parkingCar(car), "This car is already exist in parking lot!");
    }
}

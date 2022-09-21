package com.example.AEPB;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import com.example.AEPB.exception.NoCarException;
import com.example.AEPB.exception.NoTicketException;
import com.example.AEPB.object.Car;
import com.example.AEPB.object.ParkingLot;
import com.example.AEPB.object.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingLotTest {

    private static final int PARKING_SPACE_SIZE = 5;
    private static final String CAR_PLATE_NUMBER = "1234";

    @Test
    void should_park_success_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_not_exist_this_car() throws CarExistException, NoCarException {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = parkingLot.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
    }

    @Test
    void should_pick_up_success_when_pick_up_car_given_a_ticket_and_car_exists_in_parking_lot_and_ticket_exist_and_valid() throws CarNotExistException, NoTicketException {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        List<Car> carList = parkingLot.getCarList();
        carList.add(car);
        List<Ticket> ticketList = parkingLot.getTicketList();
        ticketList.add(ticket);

        Car pickUpCar = parkingLot.pickUpCar(ticket);

        assertEquals(CAR_PLATE_NUMBER, pickUpCar.getCarPlateNumber());
    }

    @Test
    void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space() throws CarExistException, NoCarException {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        List<Car> carList = parkingLot.getCarList();
        for (int i = 0; i < PARKING_SPACE_SIZE; i++) {
            Car car = Car.builder().carPlateNumber(String.valueOf(i)).build();
            carList.add(car);
        }

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Ticket ticket = parkingLot.parkingCar(car);

        assertNull(ticket);
    }

    @Test
    void should_throw_exception_when_park_car_given_no_car() {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        assertThrows(NoCarException.class, () -> parkingLot.parkingCar(null), "This is not a car!");
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_invalid_or_fake() throws CarNotExistException, NoTicketException {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Car pickUpCar = parkingLot.pickUpCar(ticket);
        assertNull(pickUpCar);
    }

    @Test
    void should_throw_exception_when_pick_up_car_given_no_ticket() {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        assertThrows(NoTicketException.class, () -> parkingLot.pickUpCar(null), "There's no ticket!");
    }

    @Test
    void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car() {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        List<Car> carList = parkingLot.getCarList();
        carList.add(car);

        assertThrows(CarExistException.class, () -> parkingLot.parkingCar(car), "This car is already exist in parking lot!");

    }

    @Test
    void should_throw_exception_when_pick_up_car_given_a_ticket_and_parking_lot_not_exist_this_car() {
        ParkingLot parkingLot = new ParkingLot(PARKING_SPACE_SIZE);

        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        List<Ticket> ticketList = parkingLot.getTicketList();
        ticketList.add(ticket);

        assertThrows(CarNotExistException.class, () -> parkingLot.pickUpCar(ticket), "This car is not exist in parking lot!");
    }
}

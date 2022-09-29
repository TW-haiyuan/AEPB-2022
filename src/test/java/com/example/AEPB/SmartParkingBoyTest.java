package com.example.AEPB;

import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import com.example.AEPB.exception.NoCarException;
import com.example.AEPB.exception.NoTicketException;
import com.example.AEPB.object.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartParkingBoyTest {

    private static final String CAR_PLATE_NUMBER = "1234";

    @Test
    void should_park_success_when_park_car_given_a_car_and_one_parking_lot() throws NoCarException, CarExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
    }

    @Test
    void should_park_success_in_parking_lot_with_most_empty_spaces_when_park_car_given_a_car_and_multiple_parking_lots() throws NoCarException, CarExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        smartParkingBoy.getParkingLotList().get(0).getCarList().add(car);
        smartParkingBoy.getParkingLotList().get(0).getCarList().add(car);
        smartParkingBoy.getParkingLotList().get(0).getCarList().add(car);
        smartParkingBoy.getParkingLotList().get(0).getCarList().add(car);
        smartParkingBoy.getParkingLotList().get(2).getCarList().add(car);
        smartParkingBoy.getParkingLotList().get(2).getCarList().add(car);

        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertTrue(smartParkingBoy.getParkingLotList().get(1).getCarList().contains(car));
    }

    @Test
    void should_park_success_in_order_when_park_car_given_a_car_and_parking_lot_has_equal_empty_space() throws NoCarException, CarExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        Car carA =Car.builder().carPlateNumber("12").build();
        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        smartParkingBoy.getParkingLotList().get(0).getCarList().add(carA);
        smartParkingBoy.getParkingLotList().get(0).getCarList().add(carA);
        smartParkingBoy.getParkingLotList().get(0).getCarList().add(carA);
        smartParkingBoy.getParkingLotList().get(2).getCarList().add(carA);

        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertEquals(CAR_PLATE_NUMBER, ticket.getCarPlateNumber());
        assertTrue(smartParkingBoy.getParkingLotList().get(0).getCarList().contains(car));
    }

    @Test
    void should_pick_up_success_when_pick_up_car_given_a_ticket() throws NoTicketException, CarNotExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
        parkingLotList.get(0).getCarList().add(car);
        parkingLotList.get(0).getTicketList().add(ticket);

        Car pickUpCar = smartParkingBoy.pickUpCar(ticket);

        assertEquals(CAR_PLATE_NUMBER, pickUpCar.getCarPlateNumber());
        }

    @Test
    void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space() throws CarExistException, NoCarException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        List<ParkingLot> parkingLotList = smartParkingBoy.getParkingLotList();
        for (ParkingLot parkingLot : parkingLotList) {
            for (int i = 0; i < parkingLot.getSize(); i++) {
                Car car = Car.builder().carPlateNumber(String.valueOf(i)).build();
                parkingLot.getCarList().add(car);
            }
        }

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        Ticket ticket = smartParkingBoy.parkingCar(car);

        assertNull(ticket);
    }

    @Test
    void should_throw_exception_when_park_car_given_no_car() {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        assertThrows(NoCarException.class, () -> smartParkingBoy.parkingCar(null), "This is not a car!");
    }

    @Test
    void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_invalid_or_fake() throws CarNotExistException, NoTicketException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

        Car car = smartParkingBoy.pickUpCar(ticket);
        assertNull(car);
    }

    @Test
    void should_throw_exception_when_pick_up_car_given_no_ticket() {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        assertThrows(NoTicketException.class, () -> smartParkingBoy.pickUpCar(null), "There's no ticket!");
    }

    @Test
    void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car() throws NoCarException, CarExistException {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        smartParkingBoy.parkingCar(car);

        assertThrows(CarExistException.class, () -> smartParkingBoy.parkingCar(car), "This car is already exist in parking lot!");
    }

    @Test
    void should_throw_exception_when_pick_up_car_given_a_ticket_and_parking_lot_not_exist_this_car() {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy();

        Ticket ticket = Ticket.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
        List<Ticket> ticketList = smartParkingBoy.getParkingLotList().get(0).getTicketList();
        ticketList.add(ticket);
        assertThrows(CarNotExistException.class, () -> smartParkingBoy.pickUpCar(ticket), "This car is not exist in parking lot!");
    }
}

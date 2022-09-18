package com.example.AEPB.service;

import com.example.AEPB.dto.ResultDTO;
import com.example.AEPB.entity.Car;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.AEPB.service.ParkingLotService.PARKING_SPACE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ParkingLotTest {

	private static final String CAR_PLATE_NUMBER = "1234";

	@Autowired
	ParkingLotService parkingLotService;

	@Test
	void should_park_success_and_get_a_ticket_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_not_exist_this_car () throws CarExistException {

		Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();

		ResultDTO parkingResult = parkingLotService.parkingCar(car);

		assertTrue(parkingResult.isSuccess());
		assertTrue(parkingResult.getTicket().isEnabled());
		assertEquals(CAR_PLATE_NUMBER, parkingResult.getTicket().getCar().getCarPlateNumber());
	}

	@Test
	void should_pick_up_success_and_ticket_invalid_when_pick_up_car_given_a_ticket_and_car_exists_in_parking_lot_and_ticket_exist_and_valid () throws CarExistException, CarNotExistException {
		Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
		// 先停车，后取车来模拟成功取车情况
		parkingLotService.parkingCar(car);
		Ticket ticket = Ticket.builder().enabled(true).car(car).build();

		ResultDTO pickUpResult = parkingLotService.pickUpCar(ticket);

		assertTrue(pickUpResult.isSuccess());
		assertEquals(CAR_PLATE_NUMBER, pickUpResult.getTicket().getCar().getCarPlateNumber());
		assertFalse(pickUpResult.getTicket().isEnabled());
	}

	@Test
	void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space () throws CarExistException {
		for (int i = 0; i < PARKING_SPACE_SIZE; i++) {
			Car car = Car.builder().carPlateNumber(String.valueOf(i)).build();
			parkingLotService.parkingCar(car);
		}

		Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
		ResultDTO parkingResult = parkingLotService.parkingCar(car);

		assertFalse(parkingResult.isSuccess());
		assertNull(parkingResult.getTicket());
	}

	@Test
	void should_park_failed_when_park_car_given_no_car_and_parking_lot_has_space () throws CarExistException {
		ResultDTO parkingResult = parkingLotService.parkingCar(null);

		assertFalse(parkingResult.isSuccess());
	}

	@Test
	void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_invalid () throws CarExistException, CarNotExistException {
		Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
		parkingLotService.parkingCar(car);

		Ticket ticket = Ticket.builder().enabled(false).car(car).build();

		ResultDTO pickUpResult = parkingLotService.pickUpCar(ticket);
		assertFalse(pickUpResult.isSuccess());
	}

	@Test
	void should_pick_up_failed_when_pick_up_car_given_no_ticket () throws CarNotExistException {
		ResultDTO pickUpResult = parkingLotService.pickUpCar(null);

		assertFalse(pickUpResult.isSuccess());
	}

	@Test
	void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car () throws CarExistException {
		Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
		parkingLotService.parkingCar(car);

		assertThrows(CarExistException.class, () -> parkingLotService.parkingCar(car), "This car is already exist in parking lot!");

	}

	@Test
	void should_throw_exception_when_pick_up_car_given_a_ticket_and_parking_lot_not_exist_this_car () {
		Car car = Car.builder().carPlateNumber(CAR_PLATE_NUMBER).build();
		Ticket ticket = Ticket.builder().enabled(true).car(car).build();

		assertThrows(CarNotExistException.class, () -> parkingLotService.pickUpCar(ticket), "This car is not exist in parking lot!");
	}


}

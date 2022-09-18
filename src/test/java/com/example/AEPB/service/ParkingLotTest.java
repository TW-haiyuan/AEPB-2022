package com.example.AEPB.service;

import com.example.AEPB.dto.ParkCarDTO;
import com.example.AEPB.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ParkingLotTest {

	@Autowired
	ParkingLotService parkingLotService;

	@Test
	void should_park_success_and_get_a_ticket_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_not_exist_this_car () {

		Car car = Car.builder().carPlateNumber("1234").build();

		ParkCarDTO parkingResult = parkingLotService.parkingCar(car);

		assertTrue(parkingResult.getTicket().isEnabled());
		assertEquals("1234", parkingResult.getTicket().getCar().getCarPlateNumber());
		assertTrue(parkingResult.isSuccess());
	}

	@Test
	void should_pick_up_success_and_ticket_invalid_when_pick_up_car_given_a_ticket_and_car_exists_in_parking_lot_and_ticket_exist_and_valid () {

	}

	@Test
	void should_park_failed_when_park_car_given_a_car_and_parking_lot_has_no_space () {

	}

	@Test
	void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_not_exist () {

	}

	@Test
	void should_pick_up_failed_when_pick_up_car_given_a_ticket_and_ticket_invalid () {

	}

	@Test
	void should_pick_up_failed_when_pick_up_car_given_no_ticket () {}

	@Test
	void should_throw_exception_when_park_car_given_a_car_and_parking_lot_has_space_and_parking_lot_exist_this_car () {}

	@Test
	void should_throw_exception_when_pick_up_car_given_a_ticket_and_parking_lot_not_exist_this_car () {}


}

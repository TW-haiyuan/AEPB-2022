package com.example.AEPB.service;

import com.example.AEPB.dto.ResultDTO;
import com.example.AEPB.entity.Car;
import com.example.AEPB.entity.Ticket;
import com.example.AEPB.exception.CarExistException;
import com.example.AEPB.exception.CarNotExistException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ParkingLotService {

    public static final int PARKING_SPACE_SIZE = 5;

    private final List<Car> parkingLot = new ArrayList<>(PARKING_SPACE_SIZE);
    private final List<Ticket> ticketList = new ArrayList<>(PARKING_SPACE_SIZE);

    public ResultDTO parkingCar(Car car) throws CarExistException {
        if (Objects.isNull(car) || parkingLot.size() >= PARKING_SPACE_SIZE) {
            return ResultDTO.builder().success(false).build();
        }

        if (parkingLot.contains(car)) {
            throw new CarExistException("This car is already exist in parking lot!");
        }

        parkingLot.add(car);
        Ticket ticket = new Ticket(car, true);
        ticketList.add(ticket);

        return ResultDTO.builder().success(true).ticket(ticket).build();
    }

    public ResultDTO pickUpCar(Ticket ticket) throws CarNotExistException {
        if (Objects.isNull(ticket) || !ticket.isEnabled() || !ticketList.contains(ticket)) {
            return ResultDTO.builder().success(false).build();
        }

        Car car = ticket.getCar();
        if (!parkingLot.contains(car)) {
            throw new CarNotExistException("This car is not exist in parking lot!");
        }

        parkingLot.remove(car);
        ticket = ticketList.stream().filter(ticket::equals).findAny().orElseThrow();
        ticket.setEnabled(false);
        return ResultDTO.builder().success(true).ticket(ticket).build();
    }
}

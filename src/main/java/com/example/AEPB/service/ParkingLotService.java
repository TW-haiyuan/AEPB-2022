package com.example.AEPB.service;

import com.example.AEPB.dto.ResultDTO;
import com.example.AEPB.entity.Car;
import com.example.AEPB.entity.ParkingSpace;
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

    private final List<ParkingSpace> parkingSpaceList;
    private final List<Ticket> ticketList;

    public ParkingLotService() {
        parkingSpaceList = new ArrayList<>(PARKING_SPACE_SIZE);
        for (int i = 0; i < PARKING_SPACE_SIZE; i++) {
            parkingSpaceList.add(new ParkingSpace());
        }
        ticketList = new ArrayList<>(PARKING_SPACE_SIZE);
    }

    public ResultDTO parkingCar(Car car) throws CarExistException {
        if (Objects.isNull(car)) {
            return ResultDTO.builder().success(false).build();
        }

        boolean carExists = parkingSpaceList.stream().anyMatch(parkingSpace -> car.equals(parkingSpace.getCar()));
        if (carExists) {
            throw new CarExistException("This car is already exist in parking lot!");
        }

        boolean noEmptySpace = parkingSpaceList.stream().noneMatch(parkingSpace -> parkingSpace.getCar() == null);
        if (noEmptySpace) {
            return ResultDTO.builder().success(false).build();
        }

        for (ParkingSpace space : parkingSpaceList) {
            if (space.getCar() == null) {
                space.setCar(car);
                break;
            }
        }

        Ticket ticket = new Ticket(car, true);
        ticketList.add(ticket);

        return ResultDTO.builder().success(true).ticket(ticket).build();
    }

    public ResultDTO pickUpCar(Ticket ticket) throws CarNotExistException {
        if (Objects.isNull(ticket)) {
            return ResultDTO.builder().success(false).build();
        }

        Car car = ticket.getCar();
        boolean carNotExist = parkingSpaceList.stream().noneMatch(parkingSpace -> car.equals(parkingSpace.getCar()));
        if (carNotExist) {
            throw new CarNotExistException("This car is not exist in parking lot!");
        }

        boolean ticketNotExist = ticketList.stream().noneMatch(ticket::equals);
        if (ticketNotExist || !ticket.isEnabled()) {
            return ResultDTO.builder().success(false).build();
        }

        String carPlateNumberInTicket = ticket.getCar().getCarPlateNumber();
        for (ParkingSpace space : parkingSpaceList) {
            if (space.getCar() != null && carPlateNumberInTicket.equals(space.getCar().getCarPlateNumber())) {
                space.setCar(null);
            }
            break;
        }

        for (Ticket ticketInList : ticketList) {
            if (ticket.equals(ticketInList)) {
                ticketInList.setEnabled(false);
                ticket = ticketInList;
            }
            break;
        }

        return ResultDTO.builder().success(true).ticket(ticket).build();
    }
}

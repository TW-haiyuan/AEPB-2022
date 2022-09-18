package com.example.AEPB.service;

import com.example.AEPB.dto.ParkCarDTO;
import com.example.AEPB.entity.Car;
import com.example.AEPB.entity.ParkingSpace;
import com.example.AEPB.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {

    private static final int PARKING_SPACE_SIZE = 5;

    private List<ParkingSpace> parkingSpaceList = new ArrayList<>(PARKING_SPACE_SIZE);
    private List<Ticket> ticketList = new ArrayList<>(PARKING_SPACE_SIZE);

    public ParkCarDTO parkingCar(Car car) {

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setCar(car);
        parkingSpaceList.add(parkingSpace);

        Ticket ticket = new Ticket();
        ticket.setCar(car);
        ticket.setEnabled(true);
        ticketList.add(ticket);

        ParkCarDTO parkCarDTO = new ParkCarDTO();
        parkCarDTO.setSuccess(true);
        parkCarDTO.setTicket(ticket);
        return parkCarDTO;
    }
}

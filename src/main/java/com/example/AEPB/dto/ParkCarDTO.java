package com.example.AEPB.dto;

import com.example.AEPB.entity.Ticket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkCarDTO {

    private boolean success;

    private Ticket ticket;
}

package com.example.AEPB.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {

    private Long id;

    private Car car;

    private boolean enabled;

}

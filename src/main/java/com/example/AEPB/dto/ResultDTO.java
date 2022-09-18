package com.example.AEPB.dto;

import com.example.AEPB.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

    private boolean success;

    private Ticket ticket;
}

package com.anthony.project.mto.mto.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientsDto {
    String name;
    String phone;
    String email;
    String address;
    String card;
}

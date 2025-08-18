package com.anthony.project.mto.mto.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrdersDto {
    Long clientId;
    Long suitId;
    String status;
}

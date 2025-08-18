package com.anthony.project.mto.mto.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuitsDto {

    Long accessoryId;
    Long jacketId;
    Long pantId;
    Long shirtId;
    Long shoeId;
    Long vestId;
}

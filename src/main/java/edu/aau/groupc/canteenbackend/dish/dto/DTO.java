package edu.aau.groupc.canteenbackend.dish.dto;

import edu.aau.groupc.canteenbackend.DBEntity;

/**
 * This interface is required to be implemented by all DTOs (i.e. objects passed through REST
 * which have a corresponding Entity)
 */
public interface DTO {
    DBEntity toEntity();
}

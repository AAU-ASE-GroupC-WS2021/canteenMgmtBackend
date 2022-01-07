package edu.aau.groupc.canteenbackend.dto;

import edu.aau.groupc.canteenbackend.entities.DBEntity;

/**
 * This interface is required to be implemented by all DTOs (i.e. objects passed through REST
 * which have a corresponding Entity)
 */
public interface DTO {
    DBEntity toEntity();
}

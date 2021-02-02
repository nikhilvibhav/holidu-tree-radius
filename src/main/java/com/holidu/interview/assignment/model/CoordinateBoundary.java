package com.holidu.interview.assignment.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Models the boundaries for the search area to include in the $where parameter for the API's Socrata query
 *
 * @author Nikhil Vibhav
 */
@Data @AllArgsConstructor
public class CoordinateBoundary {
    private final BigDecimal xLower;
    private final BigDecimal xUpper;
    private final BigDecimal yLower;
    private final BigDecimal yUpper;
}

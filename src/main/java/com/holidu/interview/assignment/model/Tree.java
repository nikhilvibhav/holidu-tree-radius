package com.holidu.interview.assignment.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Models the response returned from the census API for the Tree data we require
 *
 * @author Nikhil Vibhav
 */
@Data @NoArgsConstructor
public class Tree implements Serializable {
    private String commonName;
    private BigDecimal xCoordinate;
    private BigDecimal yCoordinate;
}

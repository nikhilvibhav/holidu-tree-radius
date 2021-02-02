package com.holidu.interview.assignment.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.holidu.interview.assignment.model.CoordinateBoundary;

/**
 * Util class to accommodate some helper methods etc.
 *
 * @author Nikhil Vibhav
 */
@Component
public class TreeSearchUtil {

    private static final double mToFtRatio = 3.28024;

    /**
     * Checks if a tree's coordinates are within the radius of the given cartesian coordinates
     *
     * @param targetX - the X coordinate received in the request
     * @param treeX   - the X coordinate of the tree
     * @param targetY - the Y coordinate received in the request
     * @param treeY   - the Y coordinate of the tree
     * @param radius  - the search radius received in the request (in feet)
     * @return true if the distance between the tree and the target coordinates is less than the radius, false otherwise
     */
    public static boolean isTreeWithinCircle(final BigDecimal targetX, final BigDecimal treeX,
                                             final BigDecimal targetY, final BigDecimal treeY,
                                             final BigDecimal radius) {
        return BigDecimal.valueOf(
            Math.hypot(
                targetX.subtract(treeX).doubleValue(), targetY.subtract(treeY).doubleValue()
            )).compareTo(radius) < 0;
    }


    /**
     * Private method to build a {@link CoordinateBoundary} object
     *
     * @param xCoordinate  - X centre point in the Cartesian plane
     * @param yCoordinate  - Y centre point in the Cartesian plane
     * @param radiusInFeet - the search radius in feet
     * @return a new {@link CoordinateBoundary} object containing the upper and lower bounds for the search area
     */
    public static CoordinateBoundary buildBoundary(final BigDecimal xCoordinate,
                                                   final BigDecimal yCoordinate,
                                                   final BigDecimal radiusInFeet) {
        return new CoordinateBoundary(
            xCoordinate.subtract(radiusInFeet),
            xCoordinate.add(radiusInFeet),
            yCoordinate.subtract(radiusInFeet),
            yCoordinate.add(radiusInFeet)
        );
    }

    /**
     * Converts the BigDecimal unit in metres to feet
     *
     * @param valueInMetres - the value to convert to feet
     * @return the value in feet
     */
    public static BigDecimal valueInFeet(final Long valueInMetres) {
        return BigDecimal.valueOf(valueInMetres).multiply(BigDecimal.valueOf(mToFtRatio));
    }
}

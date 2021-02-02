package com.holidu.interview.assignment.util;

import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link TreeSearchUtil}
 *
 * @author Nikhil Vibhav
 */
public class TreeSearchUtilTest {

    @Test
    public void givenTreeCoordinatesAndRadius_ReturnsWhetherTreeIsInCircle() throws Exception {
        BigDecimal treeX = BigDecimal.valueOf(984249.6204);
        BigDecimal treeY = BigDecimal.valueOf(207443.0012);
        BigDecimal targetX = BigDecimal.valueOf(1005805.226);
        BigDecimal targetY = BigDecimal.valueOf(222358.9835);
        BigDecimal radius = BigDecimal.valueOf(65000);

        assertTrue(TreeSearchUtil.isTreeWithinCircle(targetX, treeX, targetY, treeY, radius));
    }

    @Test
    public void givenTreeCoordinatesAndRadius_ReturnsWhetherTreeIsNotInCircle() throws Exception {
        BigDecimal treeX = BigDecimal.valueOf(984249.6204);
        BigDecimal treeY = BigDecimal.valueOf(207443.0012);
        BigDecimal targetX = BigDecimal.valueOf(1005805.226);
        BigDecimal targetY = BigDecimal.valueOf(222358.9835);
        BigDecimal radius = BigDecimal.valueOf(1000);

        assertFalse(TreeSearchUtil.isTreeWithinCircle(targetX, treeX, targetY, treeY, radius));
    }

    @Test
    public void givenValueInMetres_ConvertsToFeet() throws Exception {
        long valueToConvert = 60L;

        BigDecimal expectedValueInFeet = BigDecimal.valueOf(valueToConvert).multiply(BigDecimal.valueOf(3.28024));

        assertEquals(expectedValueInFeet, TreeSearchUtil.valueInFeet(valueToConvert));
    }
}

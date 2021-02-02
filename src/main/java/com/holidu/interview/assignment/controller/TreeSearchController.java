package com.holidu.interview.assignment.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.holidu.interview.assignment.exception.BlankResponseException;
import com.holidu.interview.assignment.exception.JsonParseException;
import com.holidu.interview.assignment.exception.RestOperationException;
import com.holidu.interview.assignment.model.Tree;
import com.holidu.interview.assignment.service.TreeCensusService;
import com.holidu.interview.assignment.util.TreeSearchUtil;

import lombok.extern.log4j.Log4j2;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * The REST Controller that exposes an API that returns an aggregated count of the trees within a certain area
 *
 * @author Nikhil Vibhav
 */
@RestController
@RequestMapping("/holidu/tree-census/v1")
@Log4j2
public class TreeSearchController {

    private final TreeCensusService treeCensusService;

    @Autowired
    public TreeSearchController(final TreeCensusService treeCensusService) {
        this.treeCensusService = treeCensusService;
    }

    /**
     * Calls the Tree Census Endpoint to retrieve an aggregated list of number of tree along with their common names
     *
     * @param xCoordinate - X centre point in the Cartesian plane
     * @param yCoordinate - Y centre point in the Cartesian plane
     * @param radius      - the search radius in metres
     * @return a {@link Map} of common name followed by the count of trees
     * @throws BlankResponseException - when the API endpoint returns a Blank Response
     * @throws RestOperationException - when the REST call to the API endpoint fails
     * @throws JsonParseException     - when the JSON result cannot be converted to a {@link List<Tree>}
     */
    @GetMapping(path = "/aggregate/common-name")
    public ResponseEntity<Map<String, Long>> getAggregatedCountByCommonName(@RequestParam final BigDecimal xCoordinate,
                                                                            @RequestParam final BigDecimal yCoordinate,
                                                                            @RequestParam final Long radius)
        throws BlankResponseException, RestOperationException, JsonParseException {

        // Filters the list of trees to the ones that fit within the circle
        final List<Tree> treesWithinRadius = treeCensusService.getAggregatedCountByCommonName(
            TreeSearchUtil.buildBoundary(xCoordinate, yCoordinate, TreeSearchUtil.valueInFeet(radius)))
            .stream()
            .filter(tree ->
                TreeSearchUtil.isTreeWithinCircle(xCoordinate, tree.getXCoordinate(), yCoordinate, tree.getYCoordinate(), TreeSearchUtil.valueInFeet(radius)))
            .collect(Collectors.toList());

        log.info("Size of the final result list - {}", treesWithinRadius.size());

        // maps the List<Tree> response to a HashMap
        return ResponseEntity.ok(treesWithinRadius.stream().collect(Collectors.toMap(
            tree -> isBlank(tree.getCommonName()) ? "Unknown tree" : capitalize(tree.getCommonName()),
            tree -> 1L,
            Long::sum)
        ));
    }

}

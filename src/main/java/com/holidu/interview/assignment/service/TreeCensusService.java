package com.holidu.interview.assignment.service;

import java.util.List;

import com.holidu.interview.assignment.exception.BlankResponseException;
import com.holidu.interview.assignment.exception.JsonParseException;
import com.holidu.interview.assignment.exception.RestOperationException;
import com.holidu.interview.assignment.model.CoordinateBoundary;
import com.holidu.interview.assignment.model.Tree;

/**
 * Interface for the operations calling the New York city's tree census data API
 *
 * @author Nikhil Vibhav
 */
public interface TreeCensusService {
    List<Tree> getAggregatedCountByCommonName(final CoordinateBoundary boundary) throws BlankResponseException, RestOperationException, JsonParseException;
}

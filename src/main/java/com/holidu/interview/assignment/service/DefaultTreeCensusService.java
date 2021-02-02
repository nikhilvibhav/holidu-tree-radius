package com.holidu.interview.assignment.service;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.holidu.interview.assignment.exception.BlankResponseException;
import com.holidu.interview.assignment.exception.JsonParseException;
import com.holidu.interview.assignment.exception.RestOperationException;
import com.holidu.interview.assignment.model.CoordinateBoundary;
import com.holidu.interview.assignment.model.Tree;

import lombok.extern.log4j.Log4j2;

/**
 * Implementation of the {@link TreeCensusService} interface
 *
 * @author Nikhil Vibhav
 */
@Service
@Log4j2
public class DefaultTreeCensusService implements TreeCensusService {

    @Value("${tree.census.api.resource.path:https://data.cityofnewyork.us/resource/uvpi-gqnh.json}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final Gson gson;

    private static final Type LIST_TYPE = new TypeToken<List<Tree>>() {
    }.getType();

    @Autowired
    public DefaultTreeCensusService(final RestTemplate restTemplate, final Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    /**
     * Method to call the Street Tree Census Data API for New York city
     * <p>
     * The method sends query params for $select and $where like an SQL query allowing us to retrieve the data we want
     *
     * @param boundary - an object containing the boundary coordinates for the search area
     * @return a {@link List<Tree>} containing the tree's common name, and X-Y coordinates in the Cartesian plane
     * @throws BlankResponseException - when the API returns a blank response
     * @throws RestOperationException - when the connectivity to the API is broken
     * @throws JsonParseException     - when the JSON response returned by the API cannot be converted to the required
     *                                format
     */
    @Override
    public List<Tree> getAggregatedCountByCommonName(final CoordinateBoundary boundary) throws BlankResponseException, RestOperationException, JsonParseException {

        // Builds the WHERE query to search for trees inside a certain boundary
        final String whereQueryParamValue = String.format(
            "x_sp <= %.4f AND x_sp >= %.4f AND y_sp <= %.4f AND y_sp >= %.4f",
            boundary.getXUpper(), boundary.getXLower(), boundary.getYUpper(), boundary.getYLower()
        );

        // Builds the query string with the SoQL queries
        final URI uri = UriComponentsBuilder.fromUriString(baseUrl)
            .queryParam("$select", "spc_common as commonName, x_sp as xCoordinate, y_sp as yCoordinate")
            .queryParam("$where", whereQueryParamValue)
            .build()
            .toUri();

        try {
            log.info("Requesting census data from: {} with the following parameters {}", baseUrl, boundary);

            // Call the API
            final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            // Check for blank response
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || response.getBody().isEmpty()) {
                log.error("Received a blank response from the API endpoint: {}", baseUrl);
                throw new BlankResponseException("Received a blank response from the API endpoint: " + baseUrl);
            }

            log.info("Census data received: {}", response.getBody());
            return gson.fromJson(response.getBody(), LIST_TYPE);
        } catch (RestClientException ex) { // Handle the exceptions
            log.error("An exception occurred while calling the API endpoint: {}", baseUrl);
            throw new RestOperationException("An exception occurred while calling the API endpoint: " + baseUrl, ex);
        } catch (JsonSyntaxException ex) {
            log.error("An exception occurred while parsing the JSON response: {}", ex.getMessage());
            throw new JsonParseException("An exception occurred while parsing the JSON response: {}", ex);
        }
    }

}

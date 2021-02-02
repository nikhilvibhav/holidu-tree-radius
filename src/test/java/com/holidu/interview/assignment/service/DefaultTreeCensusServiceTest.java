package com.holidu.interview.assignment.service;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holidu.interview.assignment.model.CoordinateBoundary;
import com.holidu.interview.assignment.model.Tree;

import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * JUnit tests for {@link DefaultTreeCensusService}
 *
 * @author Nikhil Vibhav
 */
@RunWith(SpringRunner.class)
@RestClientTest(DefaultTreeCensusService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
public class DefaultTreeCensusServiceTest {

    @Autowired
    private DefaultTreeCensusService treeCensusService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private Gson gson;

    @Test
    public void whenCallingTreeCensusAPI_ThenClientMakesTheCorrectCall() throws Exception {
        // Given
        final List<Tree> expectedResponse = gson.fromJson(Files.readString(Path.of("src/test/resources/json/listOfTrees.json")), new TypeToken<List<Tree>>() {
        }.getType());

        final CoordinateBoundary boundary = new CoordinateBoundary(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);

        server.expect(requestTo(matchesPattern(".*data.cityofnewyork.us.*")))
            .andRespond(withSuccess(Files.readString(Path.of("src/test/resources/json/listOfTrees.json")), MediaType.APPLICATION_JSON));

        // When
        final List<Tree> trees = this.treeCensusService.getAggregatedCountByCommonName(boundary);

        // Then
        assertEquals(expectedResponse, trees);
    }
}

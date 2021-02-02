package com.holidu.interview.assignment.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holidu.interview.assignment.model.CoordinateBoundary;
import com.holidu.interview.assignment.model.Tree;
import com.holidu.interview.assignment.service.TreeCensusService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * JUnit test for REST controller {@link TreeSearchController}
 *
 * @author Nikhil Vibhav
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TreeSearchController.class)
public class TreeSearchControllerTest {

    private static final String URI = "/holidu/tree-census/v1/aggregate/common-name";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TreeCensusService treeCensusService;

    @Test
    public void givenCoordinatesAndRadius_WhenGetAggregatedCountByCommonName_ThenReturn200() throws Exception {
        // Given
        final String xCoordinate = "1021900";
        final String yCoordinate = "208600";
        final String radius = "2000";

        final String expectedResponse = Files.readString(Path.of("src/test/resources/json/expectedResponse.json"));

        final Gson gson = new Gson();
        final List<Tree> treeList = gson.fromJson(
            Files.readString(Path.of("src/test/resources/json/listOfTrees.json")),
            new TypeToken<List<Tree>>() {
            }.getType());

        given(treeCensusService.getAggregatedCountByCommonName(any(CoordinateBoundary.class))).willReturn(treeList);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
            .param("xCoordinate", xCoordinate)
            .param("yCoordinate", yCoordinate)
            .param("radius", radius)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse.replaceAll("\r\n", "")));

        verify(treeCensusService, times(1)).getAggregatedCountByCommonName(any(CoordinateBoundary.class));
    }

    @Test
    public void givenXCoordinateAndRadius_WhenGetAggregatedCountByCommonName_ThenReturn400() throws Exception {
        // Given
        final String yCoordinate = "208600";
        final String radius = "2000";

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
            .param("yCoordinate", yCoordinate)
            .param("radius", radius)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason("Required BigDecimal parameter 'xCoordinate' is not present"));
    }

    @Test
    public void givenYCoordinateAndRadius_WhenGetAggregatedCountByCommonName_ThenReturn400() throws Exception {
        // Given
        final String xCoordinate = "1021900";
        final String radius = "2000";

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
            .param("xCoordinate", xCoordinate)
            .param("radius", radius)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason("Required BigDecimal parameter 'yCoordinate' is not present"));
    }

    @Test
    public void givenCoordinates_WhenGetAggregatedCountByCommonName_ThenReturn400() throws Exception {
        // Given
        final String xCoordinate = "1021900";
        final String yCoordinate = "208600";

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
            .param("yCoordinate", yCoordinate)
            .param("xCoordinate", xCoordinate)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason("Required Long parameter 'radius' is not present"));
    }

}

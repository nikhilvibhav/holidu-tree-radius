package com.holidu.interview.assignment;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for Tree Search Application
 *
 * @author Nikhil Vibhav
 */
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = App.class)
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenCorrectParameters_WhenGetAggregateByCommonName_ThenReturn200() throws Exception {
        // Given
        final String xCoordinate = "1021900";
        final String yCoordinate = "208600";
        final String radius = "20000";

        final String expectedResponse = Files.readString(Path.of("src/test/resources/json/expectedResponse-integrationTest.json"));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/holidu/tree-census/v1/aggregate/common-name")
            .param("xCoordinate", xCoordinate)
            .param("yCoordinate", yCoordinate)
            .param("radius", radius)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse.replaceAll("\r\n", "")));
    }

}

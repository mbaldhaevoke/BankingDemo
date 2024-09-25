package com.readJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestCaseReader {
    public static void main(String[] args) {
        // Initialize ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read JSON file and map to TestCase class
            TestCase testCase = objectMapper.readValue(new File("SalesDemo_Yamini_09_23_2024_105102.json"), TestCase.class);
            // Accessing data
            System.out.println("Email ID: " + testCase.getEmailId());
            System.out.println("Test Name: " + testCase.getTestName());
            System.out.println("Test Id: " + testCase.getTestId());
            System.out.println("Session Id: " + testCase.getSessionId());
            System.out.println("Url: " + testCase.getUrl());
            System.out.println("Created Date: " + testCase.getCreatedDate());

            // Loop through steps
            for (Step step : testCase.getSteps()) {
                System.out.println("Step Order: " + step.getOrder());
                System.out.println("Step Event: " + step.getStep().getEvent());
                System.out.println("Step Locator: " + step.getStep().getLocator());
                System.out.println("Step Value: " + step.getStep().getValue());
                System.out.println("Step Variable: " + step.getStep().getVariable());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


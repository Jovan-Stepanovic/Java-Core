package com.griddynamics.service;


import com.griddynamics.model.InvertedIndex;
import com.griddynamics.model.SearchContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Trying to imitate Spring Components by making class final
 * and class members static
 */
public final class SearchService {

    private static final String NOT_FOUND = "No matching people found.";

    private static List<String> data;
    private static InvertedIndex invertedIndex;


    public static List<String> search(SearchContext context) {

        if (isPhraseInvalid(context.getPhrase())) {
            return List.of(NOT_FOUND);
        }

        List<String> result = executeSearch(context);

        return result.isEmpty() ? List.of(NOT_FOUND) : result;
    }


    public static void loadData(String filePath) {
        String text = "";

        try {
            text = readFileAsString(filePath);
        } catch (IOException e) {
            System.out.println("Error while reading data");
        }

        data =  Arrays.stream(text.split("\n")).collect(Collectors.toList());
        invertedIndex = new InvertedIndex(data);
    }

    public static void printData() {
        System.out.println("=== List of people ===");
        data.forEach(System.out::println);
    }

    public static List<String> getData() {
        return data;
    }

    private static boolean isPhraseInvalid(String phrase) {
        return phrase.length() == 1;
    }

    private static List<String> executeSearch(SearchContext context) {
        return context.getStrategy().search(context.getPhrase(), invertedIndex, data);
    }

    private static String readFileAsString(String fileName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(fileName);
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

}

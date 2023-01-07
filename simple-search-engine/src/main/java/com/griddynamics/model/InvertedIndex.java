package com.griddynamics.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Data structure to store data for most efficient search ( data retrieval )
 */
public final class InvertedIndex {

    private final Map<String, List<Integer>> index;

    public InvertedIndex(List<String> data) {
        index = new HashMap<>();

        for (int position = 0; position < data.size(); position++) {
            String line = data.get(position);

            List<String> words = Arrays
                    .stream(line.toLowerCase().split(" "))
                    .toList();

            for (String word: words) {
                updateIndex(index, word, position);
            }
        }
    }


    public List<Integer> getPositions(String phrase) {

        List<Integer> positions = new ArrayList<>();
        String[] words = phrase.toLowerCase().split(" ");

        for (String word: words) {
            if (index.get(word) != null) {
                positions.addAll(index.get(word));
            }
        }

        return positions;
    }

    private static void updateIndex(Map<String, List<Integer>> index, String word, int position) {
        List<Integer> positions;

        if (index.get(word) == null) {
            positions = new ArrayList<>();
        } else {
            positions = index.get(word);
        }
        positions.add(position);
        index.put(word, positions);
    }
}

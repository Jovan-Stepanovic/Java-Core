package com.griddynamics.model.strategy;

import com.griddynamics.model.InvertedIndex;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchStrategyAll implements SearchStrategy{

    @Override
    public List<String> search(String phrase,
                               InvertedIndex invertedIndex,
                               List<String> data) {

        List<Integer> positions = invertedIndex.getPositions(phrase);

        return positions.stream()
                .filter(i -> Collections.frequency(positions, i) > 1)
                .distinct()
                .map(data::get)
                .collect(Collectors.toList());
    }
}

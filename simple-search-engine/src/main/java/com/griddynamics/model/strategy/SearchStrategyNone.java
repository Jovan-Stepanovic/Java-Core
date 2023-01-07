package com.griddynamics.model.strategy;

import com.griddynamics.model.InvertedIndex;

import java.util.List;
import java.util.stream.Collectors;

public class SearchStrategyNone implements SearchStrategy{

    @Override
    public List<String> search(String phrase,
                               InvertedIndex invertedIndex,
                               List<String> data) {

        List<Integer> positions = invertedIndex.getPositions(phrase)
                .stream()
                .distinct().toList();;

        if (positions.isEmpty()) {
            return data;
        }

        return data.stream()
                .filter( t -> !positions.contains(data.indexOf(t)))
                .collect(Collectors.toList());
    }

}


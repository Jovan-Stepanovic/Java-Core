package com.griddynamics.model.strategy;





import com.griddynamics.model.InvertedIndex;

import java.util.List;
import java.util.stream.Collectors;

public class SearchStrategyAny implements SearchStrategy{

    @Override
    public List<String> search(String phrase,
                               InvertedIndex invertedIndex,
                               List<String> data) {

        return invertedIndex.getPositions(phrase)
                .stream()
                .distinct()
                .map(data::get)
                .collect(Collectors.toList());
    }
}

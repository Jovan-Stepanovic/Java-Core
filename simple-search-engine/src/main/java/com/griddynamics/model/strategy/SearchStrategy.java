package com.griddynamics.model.strategy;


import com.griddynamics.model.InvertedIndex;

import java.util.List;

public interface SearchStrategy {
    List<String> search(String phrase, InvertedIndex invertedIndex, List<String> data);
}

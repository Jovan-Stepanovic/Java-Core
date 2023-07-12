package com.griddynamics.model.strategy;


import com.griddynamics.model.InvertedIndex;

import java.util.List;

// TODO: 7/12/23 refactor -> rename to Searcher, transform to abstract class?
//add constructor for repository
public interface Searcher {
    List<String> search(List<String> terms, InvertedIndex repository);
}

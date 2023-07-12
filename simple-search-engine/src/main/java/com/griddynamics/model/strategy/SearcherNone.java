package com.griddynamics.model.strategy;

import com.griddynamics.model.InvertedIndex;

import java.util.List;

public class SearcherNone implements Searcher {
    @Override
    public List<String> search(List<String> terms, InvertedIndex repository) {
        return repository.queryNONE(terms);
    }
}


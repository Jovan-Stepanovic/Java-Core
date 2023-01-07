package com.griddynamics.model;


import com.griddynamics.model.strategy.SearchStrategy;

public class SearchContext {
    private String phrase;
    private SearchStrategy strategy;


    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public SearchStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }
}

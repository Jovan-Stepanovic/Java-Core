package com.griddynamics.model;


import com.griddynamics.model.strategy.Searcher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
// TODO: 7/12/23 rename to searchRequest 
public class SearchContext {
    private String phrase;
    private Searcher searcher;
}

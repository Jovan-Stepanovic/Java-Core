package com.griddynamics.model;


import com.griddynamics.model.strategy.SearchStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchContext {
    private String phrase;
    private SearchStrategy strategy;
}

package com.griddynamics;

import com.griddynamics.model.SearchContext;
import com.griddynamics.model.strategy.SearcherAll;
import com.griddynamics.model.strategy.SearcherAny;
import com.griddynamics.model.strategy.SearcherNone;
import com.griddynamics.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SearchServiceTest {

    private final String FILE_NAME = "names.txt";
    private final List<String> NOT_FOUND = List.of("No matching people found.");

    private SearchContext searchContext;

    @BeforeEach
    void init() {
        SearchService.loadData(FILE_NAME);
    }

    @Test
    public void getAllNames() {
        assertThat( "11 Names should be found", SearchService.getData().size(), equalTo(11));
        assertThat( "1st persons name is Kristofer Galley", SearchService.getData().get(0), equalTo("Kristofer Galley"));
        assertThat( "second person is Fernando Marbury and he has email", SearchService.getData().get(1), equalTo("Fernando Marbury fernando_marbury@gmail.com"));
        assertThat( "10th person is Bob Yeh and he has email", SearchService.getData().get(9), equalTo("Bob Yeh bobyeah@gmail.com"));
    }

    /**
     * Search Strategy: ALL
     * only full name and surname or full email is a valid search phrase with this strategy
     * case-insensitive
     */
    @Test
    public void searchNameStrategyAll() {
        searchContext = new SearchContext("Malena Gray", new SearcherAll());
        assertThat( "Malena Gray should be found", SearchService.search(searchContext).get(0), equalTo("Malena Gray"));

        searchContext = new SearchContext("Bla", new SearcherAll());
        assertThat( "bla should be found", SearchService.search(searchContext).get(0), equalTo("Bla"));

        searchContext.setPhrase("Kristyn Nix");
        assertThat( "Kristyn Nix should be found", SearchService.search(searchContext).get(0), equalTo("Kristyn Nix nix-kris@gmail.com"));

        searchContext.setPhrase("Kristyn Nix nix-kris@gmail.com");
        assertThat( "Kristyn Nix should be found", SearchService.search(searchContext).get(0), equalTo("Kristyn Nix nix-kris@gmail.com"));


        searchContext.setPhrase("Malena nix-kris@gmail.com");
        assertThat( "No result is found", SearchService.search(searchContext), equalTo(NOT_FOUND));

        searchContext.setPhrase("Malena");
        assertThat( "Malena Gray should be found", SearchService.search(searchContext).get(0), equalTo("Malena Gray"));

        searchContext.setPhrase("Nix Malena");
        assertThat( "No result is found", SearchService.search(searchContext), equalTo(NOT_FOUND));
    }

    /**
     * Search Strategy: ANY
     * only name, only surname or only mail as well as full name with or without email is valid search phrase
     * case-insensitive
     */
    @Test
    public void searchNameStrategyAny() {
        searchContext = new SearchContext("Malena Gray", new SearcherAny());
        assertThat( "Malena Gray should be found", SearchService.search(searchContext).get(0), equalTo("Malena Gray"));

        searchContext.setPhrase("Kristyn Nix");
        assertThat( "Kristyn Nix should be found", SearchService.search(searchContext).get(0), equalTo("Kristyn Nix nix-kris@gmail.com"));

        searchContext.setPhrase("Kristyn Nix nix-kris@gmail.com");
        assertThat( "Kristyn Nix should be found", SearchService.search(searchContext).get(0), equalTo("Kristyn Nix nix-kris@gmail.com"));


        searchContext.setPhrase("malena");
        assertThat( "Malena Gray should be found", SearchService.search(searchContext).get(0), equalTo("Malena Gray"));

        searchContext.setPhrase("Kristyn");
        assertThat( "Kristyn Nix should be found", SearchService.search(searchContext).get(0), equalTo("Kristyn Nix nix-kris@gmail.com"));

        searchContext.setPhrase("Nix");
        assertThat( "Kristyn Nix should be found", SearchService.search(searchContext).get(0), equalTo("Kristyn Nix nix-kris@gmail.com"));

        searchContext.setPhrase("nix-kris@gmail.com");
        assertThat( "Kristyn Nix should be found", SearchService.search(searchContext).get(0), equalTo("Kristyn Nix nix-kris@gmail.com"));

    }

    /**
     * Search Strategy: NONE
     * same as ANY in terms of valid search phrases
     * returns all names that don't match search phrase ( like a negation of ANY )
     * case-insensitive
     */
    @Test
    public void searchNameStrategyNone() {
        searchContext = new SearchContext("Malena Gray", new SearcherNone());

        assertThat( "9 names should be found",
                SearchService.search(searchContext).size(), equalTo(10));
        assertThat( "All names except Malena Grey should be found",
                SearchService.search(searchContext).contains("Malena Gray"), equalTo(false));

        searchContext.setPhrase("Kristyn Nix");
        assertThat( "9 names should be found",
                SearchService.search(searchContext).size(), equalTo(10));
        assertThat( "All names except Kristyn Nix should be found",
                SearchService.search(searchContext).contains("Kristyn Nix"), equalTo(false));

        searchContext.setPhrase("malena");
        assertThat( "9 names should be found",
                SearchService.search(searchContext).size(), equalTo(10));
        assertThat( "All names except Malena Grey should be found",
                SearchService.search(searchContext).contains("Malena Gray"), equalTo(false));

        searchContext.setPhrase("kristyn");
        assertThat( "9 names should be found",
                SearchService.search(searchContext).size(), equalTo(10));
        assertThat( "All names except Kristyn Nix should be found",
                SearchService.search(searchContext).contains("Kristyn Nix"), equalTo(false));

        searchContext.setPhrase("nix-kris@gmail.com");
        assertThat( "9 names should be found",
                SearchService.search(searchContext).size(), equalTo(10));
        assertThat( "All names except Kristyn Nix should be found",
                SearchService.search(searchContext).contains("Kristyn Nix"), equalTo(false));
    }
}

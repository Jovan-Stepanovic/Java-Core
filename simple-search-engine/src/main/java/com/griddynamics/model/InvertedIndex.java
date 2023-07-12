package com.griddynamics.model;

import java.util.*;
import java.util.stream.Collectors;

import static com.griddynamics.util.Constants.WORD_SEPARATOR;


/**
 * Data structure to store data for most efficient search ( data retrieval )
 */
public final class InvertedIndex {
    private final Map<String, List<Integer>> invertedIndex;
    private final List<String> data;

    public InvertedIndex(List<String> data) {
        this.data = data;

        this.invertedIndex = new HashMap<>();

        for (int position = 0; position < data.size(); position++) {
            String line = data.get(position);

            List<String> words = Arrays
                    .stream(line.toLowerCase().split(WORD_SEPARATOR))
                    .toList();

            for (String word: words) {
                updateIndex(invertedIndex, word, position);
            }

//            words.forEach(word -> updateIndex(index, word, position));
        }
    }


    public List<String> queryAND(List<String> terms) {
        Set<Integer> postings = getAllPostingsOperationAND(terms);
        return postings.stream()
                .map(data::get)
                .toList();
    }

    public List<String> queryOR(List<String> terms) {
        Set<Integer> postings = getAllPostingsOperationOR(terms);
        return postings.stream()
                .map(data::get)
                .toList();
    }

    public List<String> queryNONE(List<String> terms) {
        Set<Integer> postings = getAllPostingsOperationAND(terms);
        return data.stream()
                .filter( word -> !postings.contains(data.indexOf(word)))
                .collect(Collectors.toList());
    }

    public Set<Integer> getAllPostingsOperationAND(List<String> terms) {
        Set<Integer> postings = new HashSet<>();
        terms.forEach(term -> {
            if (postings.isEmpty()) {
                postings.addAll(getPostingsList(term));
            } else {
                postings.retainAll(getPostingsList(term));
            }
        });
        return postings;
    }

    public Set<Integer> getAllPostingsOperationOR(List<String> terms) {
        Set<Integer> postings = new HashSet<>();
        terms.forEach(
                term -> postings.addAll(getPostingsList(term))
        );
        return postings;
    }


    private List<Integer> getPostingsList(String term) {
        return invertedIndex.getOrDefault(term, new ArrayList<>());
    }




    //todo move to Repository
    //todo delete index param in this method
    private static void updateIndex(Map<String, List<Integer>> index, String word, int position) {
        List<Integer> positions;

        if (index.get(word) == null) {
            positions = new ArrayList<>();
        } else {
            positions = index.get(word);
        }
        positions.add(position);
        index.put(word, positions);
    }
}

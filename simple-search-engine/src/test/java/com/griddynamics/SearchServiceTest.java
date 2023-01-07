package com.griddynamics;

import com.griddynamics.service.SearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SearchServiceTest {

    @Test
    public void getAllNames() {


        SearchService.loadData("names.txt");

        List<String> data = SearchService.getData();

        Assertions.assertNotNull(data);
        Assertions.assertEquals(50, data.size());
    }
}

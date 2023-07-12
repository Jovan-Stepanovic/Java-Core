package com.griddynamics;

import com.griddynamics.model.SearchContext;
import com.griddynamics.model.strategy.SearcherAll;
import com.griddynamics.model.strategy.SearcherAny;
import com.griddynamics.model.strategy.SearcherNone;
import com.griddynamics.service.SearchService;


import java.util.List;
import java.util.Scanner;

import static com.griddynamics.util.Constants.*;

// TODO: 7/12/23 create CliApplicationUi something like that...
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Scanner intScanner = new Scanner(System.in);
    private static boolean IS_RUNNING = true;

    public static void main(String[] args) {

//        String text = readFileAsString(NAMES_PATH);
        SearchService.loadData(NAMES_PATH);

        while (IS_RUNNING) {
            loadUserMenu();

            int option = intScanner.nextInt();
            switch (option) {
                case 1 -> search();
                case 2 -> SearchService.printData();
                case 0 -> exit();
                default -> System.out.println(INCORRECT_OPTION);
            }
        }
    }


    private static void exit() {
        System.out.println("\n Bye!");
        IS_RUNNING = false;
    }

    private static void loadUserMenu() {
        System.out.println("""

                === Menu ===
                1. Find a person
                2. Print all people
                0. Exit""");
    }

    private static void search() {
        SearchContext context = new SearchContext();

        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();

        switch (strategy) {
            case ANY -> context.setSearcher(new SearcherAny());
            case NONE -> context.setSearcher(new SearcherNone());
            case ALL -> context.setSearcher(new SearcherAll());
        }

        System.out.println("Enter a name or email to search all suitable people:");
        context.setPhrase(scanner.nextLine());

        List<String> response = SearchService.search(context);
        response.forEach(System.out::println);
    }


}
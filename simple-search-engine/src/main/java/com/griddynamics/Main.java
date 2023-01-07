package com.griddynamics;

import com.griddynamics.model.SearchContext;
import com.griddynamics.model.strategy.SearchStrategyAll;
import com.griddynamics.model.strategy.SearchStrategyAny;
import com.griddynamics.model.strategy.SearchStrategyNone;
import com.griddynamics.service.SearchService;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Scanner intScanner = new Scanner(System.in);
    private static boolean IS_RUNNING = true;
    private static final String INCORRECT_OPTION = "\nIncorrect option! Try again.";
    private static final String NAMES_PATH = "names.txt";

    private static final String ANY = "ANY";
    private static final String ALL = "ALL";
    private static final String NONE = "NONE";


    public static void main(String[] args) throws IOException {

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
            case ANY -> context.setStrategy(new SearchStrategyAny());
            case NONE -> context.setStrategy(new SearchStrategyNone());
            case ALL -> context.setStrategy(new SearchStrategyAll());
        }

        System.out.println("Enter a name or email to search all suitable people:");
        context.setPhrase(scanner.nextLine());

        List<String> response = SearchService.search(context);
        response.forEach(System.out::println);
    }


}
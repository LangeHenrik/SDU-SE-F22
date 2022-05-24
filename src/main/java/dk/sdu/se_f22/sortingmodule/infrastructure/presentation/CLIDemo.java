package dk.sdu.se_f22.sortingmodule.infrastructure.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModule;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModuleImpl;
import dk.sdu.se_f22.sortingmodule.scoring.ScoreSortType;

public class CLIDemo {
    private boolean exit = false;
    private Scanner reader;
    private SortingModule module;
    private SearchHits searchHits;

    private boolean useMockDataBrands = true;
    private boolean useMockDataContent = true;
    private boolean useMockDataProducts = true;

    public static void main(String[] args) {
        CLIDemo demo = new CLIDemo();
        demo.demo();
    }

    public CLIDemo() {
    }

    public void demo() {
        // Setup
        module = new SortingModuleImpl();
        module.useMockData(useMockDataBrands, useMockDataContent, useMockDataProducts);
        reader = new Scanner(System.in);
        printWelcome();

        // Handle command:
        while (!exit) {
            System.out.println("");
            handleCommand(readLine());
        }
    } 

    public void printWelcome() {
        System.out.println("");
        System.out.println("CLI demo of sorting module - Infrastructure");
    }

    public String readLine() {
        String line = "";
        try {
            System.out.print("> ");
            line = reader.nextLine();
        } catch (NoSuchElementException|IllegalStateException e) {
            return "read error";
        }
        return line;
    }

    public void handleCommand(String line) {
        Command command = Command.getEnum(line);

        switch (command) {
            case QUIT:
                exit = true;
                break;
            case HELP:
                System.out.println("Commands:");
                System.out.println(Arrays.toString(Command.values()));
                break;

            case SEARCH:
                System.out.println("What should the search string be?");
                module.setSearchString(readLine());
                searchHits = module.search();
                handleCommand(Command.GETSEARCH.toString());
                break;
            case GETSEARCH:
                System.out.println("---------:");
                System.out.println("Search contains:");

                System.out.println("- Products:");
                for (Product product : searchHits.getProducts()) {
                    System.out.println("    - " + product.getName());
                }

                System.out.println("- Brands:");
                for (Brand brand : searchHits.getBrands()) {
                    System.out.println("    - " + brand.getName());
                }

                System.out.println("- Content:");
                for (Content content : searchHits.getContents()) {
                    System.out.println("    - " + content.getTitle());
                }

                break;

            

            case SCORE:
                System.out.println("What scoring do you want to use?");
                while (true) {

                    ScoreSortType.valueOf(readLine());

                    break;
                }
                break;

            case MOCKDATA:
                System.out.println("The program is currently using the following mock data:");
                System.out.println("    Product: " + this.useMockDataProducts);
                System.out.println("    Brand: " + this.useMockDataBrands);
                System.out.println("    Content: " + this.useMockDataContent);
                break;
            case ENABLEMOCK:
                System.out.println("Whick mock would you like to enable? (product, brand, content, all):");
                useEnum(true);
                module.useMockData(useMockDataBrands, useMockDataContent, useMockDataProducts);
                break;
            case DISABLEMOCK:
                System.out.println("Whick mock would you like to disable? (product, brand, content, all):");
                useEnum(false);
                module.useMockData(useMockDataBrands, useMockDataContent, useMockDataProducts);
                break;


            default:
                System.out.println("I didn't know that command");
                break;
        }
    }

    private void useEnum(boolean mockState) {
        boolean actionComplete = false;
        while (!actionComplete) {
            actionComplete = true;
            switch (readLine().toLowerCase()) {
                case "product":
                    this.useMockDataProducts = mockState;
                    break;
                case "brand":
                    this.useMockDataBrands = mockState;
                    break;
                case "content":
                    this.useMockDataContent = mockState;
                    break;
                case "all":
                    this.useMockDataProducts = mockState;
                    this.useMockDataBrands = mockState;
                    this.useMockDataContent = mockState;
                    break;
                default:
                    actionComplete = false;
                    continue;
            }
        }
    }

    private enum Command {
        QUIT("quit", "q", "exit"),
        HELP("help"),

        SEARCH("search"),
        GETSEARCH("get search", "retrieve data", "data"),
        SEARCHINFORMATION("search information", "search info", "info"),

        SCORE("score", "set scoring", "scoring", "sorting", "sort"),

        MOCKDATA("mock"),
        ENABLEMOCK("use mock", "mock on"),
        DISABLEMOCK("remove mock", "mock off"),

        UNKNOWN();

        ArrayList<String> keywords;

        Command(String... keywords) {
            this.keywords = new ArrayList<>();
            for (String keyword : keywords) {
                this.keywords.add(keyword.toLowerCase());
            }
        }

        public static Command getEnum(String searchedCommand) {
            for (Command command : Command.values()) {
                if (command.keywords.contains(searchedCommand.toLowerCase())) {
                    return command;
                }
            }
            return Command.UNKNOWN;
        }

        @Override
        public String toString() {
            if (keywords.size() == 0) {
                return "-";
            }
            return keywords.get(0);
        }
    }
}

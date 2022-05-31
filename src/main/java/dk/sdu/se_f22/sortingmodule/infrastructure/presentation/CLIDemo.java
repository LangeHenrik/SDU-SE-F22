package dk.sdu.se_f22.sortingmodule.infrastructure.presentation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.category.Category;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModule;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SortingModuleImpl;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sharedlibrary.utils.ScoreSortType;
import java.util.*;

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
            case SEARCHINFORMATION:
                System.out.println("Page: " + (module.getQuery().getPagination()[0] + 1));
                System.out.println("Page size: " + module.getQuery().getPagination()[1]);
                System.out.println("Scoring: " + module.getQuery().getScoring());
                System.out.println("Categories filtered by: " + module.getQuery().getCategory());
                System.out.print("Ranges filtered by: ");
                for (Map range : module.getQuery().getAllRanges()) {
                    range.forEach((key, value) -> {
                        System.out.print(key+" ");
                    });
                }
                System.out.println("");
                break;
            
            case SCORE:
                String scores = "";
                for (ScoreSortType type : ScoreSortType.values()) {
                    scores += ", "+type;
                } 
                scores = scores.substring(2);

                System.out.println("What scoring do you want to use ("+scores+")?");
                while (true) {
                    try {
                        module.setScoring(ScoreSortType.valueOf(readLine())); 
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Try again");
                    }
                }
                break;
            case SCORES:
                for (ScoreSortType type : ScoreSortType.values()) {
                    System.out.println("- " + type);
                }
                break;
            case CATEGORY:
                System.out.println("What categories would you like to have in search? (More can be added, by using comma, or none by entering empty)");
                System.out.println("Available categories:");
                for (Object category : module.getAllCategories()) {
                    if (category instanceof Category) {
                        Category categoryToDisplay = (Category) category;
                        System.out.println("    - ("+categoryToDisplay.getId()+") "+categoryToDisplay.getName());
                    }
                }
                
                String[] ids = readLine().split(",");
                module.clearCategory();
                for (String id : ids) {
                    try {
                        module.addCategory(Integer.parseInt(id));
                    } catch (Exception e) {
                    }
                }
                System.out.println("Categories filtered by: " + module.getQuery().getCategory());
                break;
            case CATEGORIES:
                for (Object category : module.getAllCategories()) {
                    if (category instanceof Category) {
                        Category categoryToDisplay = (Category) category;
                        System.out.println("- ("+categoryToDisplay.getId()+") "+categoryToDisplay.getName());
                    }
                }
                break;
            case RANGE:
                System.out.println("What ranges would you like to have in search? (More can be added, by using comma, or none by entering empty)");
                System.out.println("Available ranges:");
                HashMap<Integer, RangeFilter> rangeFilterMap = new HashMap<>();
                for (Object range : module.getAvailableRangeFilters()) {
                    if (range instanceof RangeFilter) {
                        RangeFilter rangeToDisplay = (RangeFilter) range;
                        System.out.println("- ("+rangeToDisplay.getId()+") "+rangeToDisplay.getName()+" | "+rangeToDisplay.getType());
                        rangeFilterMap.put(rangeToDisplay.getId(), rangeToDisplay);
                    }
                }
                
                String[] rangeIds = readLine().split(",");
                module.clearRange();
                for (String id : rangeIds) {
                    int formattedId = Integer.parseInt(id);
                    try {
                        if (rangeFilterMap.get(formattedId) == null) {
                            continue;
                        }
                        System.out.println(rangeFilterMap.get(formattedId).getName()+": ");
                        switch (rangeFilterMap.get(formattedId).getType()) {
                            default:
                            case DOUBLE:
                                System.out.println("Start range:");
                                double startRangeDouble = Double.parseDouble(readLine());
                                System.out.println("End range:");
                                double endRangeDouble = Double.parseDouble(readLine());
                                module.addRange(formattedId, startRangeDouble, endRangeDouble);
                                break;
                            case LONG:
                                System.out.println("Start range:");
                                long startRangeLong = Long.parseLong(readLine());
                                System.out.println("End range:");
                                long endRangeLong = Long.parseLong(readLine());
                                module.addRange(formattedId, startRangeLong, endRangeLong);
                                break;
                            case TIME:
                                System.out.println("Start range (epoch):");
                                Instant startRangeTime = Instant.ofEpochMilli(Long.parseLong(readLine()));
                                System.out.println("End range (epoch):");
                                Instant endRangeTime = Instant.ofEpochMilli(Long.parseLong(readLine()));
                                module.addRange(formattedId, startRangeTime, endRangeTime);
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                System.out.print("Ranges filtered by: ");
                for (Map range : module.getQuery().getAllRanges()) {
                    range.forEach((key, value) -> {
                        System.out.print(key+" ");
                    });
                }
                System.out.println("");
                
                break;
            case RANGES:
                for (Object range : module.getAvailableRangeFilters()) {
                    if (range instanceof RangeFilter) {
                        RangeFilter rangeToDisplay = (RangeFilter) range;
                        System.out.println("- ("+rangeToDisplay.getId()+") "+rangeToDisplay.getName()+" | "+rangeToDisplay.getType());
                    }
                }
                break;
            case PAGINATION:
                int page = 0;
                int pageSize = 25;

                System.out.println("What page do you want to see? (currently: "+module.getQuery().getPagination()[0]+")");
                while (true) {
                    try {
                        page = Integer.parseInt(readLine());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Try again");
                    }
                }

                System.out.println("How many hits do you want per page? (currently: "+module.getQuery().getPagination()[1]+")");
                while (true) {
                    try {
                        pageSize = Integer.parseInt(readLine());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Try again");
                    }
                }

                module.setPagination(page, pageSize);
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
        SCORES("scores", "score list"),
        CATEGORY("category", "set category"),
        CATEGORIES("categories", "category list"),
        RANGE("range", "set range"),
        RANGES("ranges", "range list"),
        PAGINATION("pagination"),

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

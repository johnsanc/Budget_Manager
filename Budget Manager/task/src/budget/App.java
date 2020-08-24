package budget;

import budget.utility.Item;
import budget.utility.ItemType;
import budget.utility.Manager;
import budget.utility.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    List<String> mainMenuOptions;
    List<String> addPurchaseOptions;
    List<String> purchaseOptions;
    List<String> sortingOptions;
    List<String> specificTypesOptions;
    Menu mainMenu;
    Menu addPurchaseMenu;
    Menu purchasesMenu;
    Menu sortingMenu;
    Menu specificTypesMenu;
    Scanner scanner;
    Manager manager;

    public App() {
        mainMenuOptions = new ArrayList<>(Arrays.asList(
                "1) Add income",
                "2) Add purchase",
                "3) Show list of purchases",
                "4) Balance",
                "5) Save",
                "6) Load",
                "7) Analyze (sort)",
                "0) Exit"));

        addPurchaseOptions = new ArrayList<>(Arrays.asList(
                "1) Food",
                "2) Clothes",
                "3) Entertainment",
                "4) Other",
                "5) Back"
        ));

        purchaseOptions = new ArrayList<>(Arrays.asList(
                "1) Food",
                "2) Clothes",
                "3) Entertainment",
                "4) Other",
                "5) All",
                "6) Back"
        ));

        sortingOptions = new ArrayList<>(Arrays.asList(
                "1) Sort all purchases",
                "2) Sort by type",
                "3) Sort by specific type",
                "4) Back"
        ));

        specificTypesOptions = new ArrayList<>(Arrays.asList(
                "1) Food",
                "2) Clothes",
                "3) Entertainment",
                "4) Other"
        ));

        mainMenu = new Menu(mainMenuOptions);
        addPurchaseMenu = new Menu(addPurchaseOptions);
        purchasesMenu = new Menu(purchaseOptions);
        sortingMenu = new Menu(sortingOptions);
        specificTypesMenu = new Menu(specificTypesOptions);
        scanner = new Scanner(System.in);
        manager = new Manager();
    }

    public void run() {
        int userChoice;

        do {
            mainMenu.showMenu();
            userChoice = Integer.parseInt(scanner.nextLine());

            switch (userChoice) {
                case 1:
                    System.out.println("\nEnter income:");
                    Float incomeToAdd = Float.parseFloat(scanner.nextLine());
                    manager.addIncome(incomeToAdd);
                    System.out.println("Income was added!\n");
                    break;

                case 2:
                    runAddPurchaseLoop();
                    break;

                case 3:
                    runShowPurchasesLoop();
                    break;

                case 4:
                    manager.showBalance();
                    break;

                case 5:
                    saveToFile();
                    System.out.println("\nPurchases were saved!\n");
                    break;

                case 6:
                    loadFromFile();
                    System.out.println("\nPurchases were loaded!\n");
                    break;

                case 7:
                    runSortingLoop();
                    break;

                default:
                    break;
            }
        } while (userChoice != 0);
    }

    private void runSortingLoop() {
        int choice;

        do {
            System.out.println();
            System.out.println("How do you want to sort?");
            sortingMenu.showMenu();
            choice = Integer.parseInt(scanner.nextLine());

            if (choice != 4) {
                switch (choice) {
                    case 1:
                    case 2:
                        manager.sortAndShow(choice);
                        break;

                    case 3:
                        System.out.println("\nChoose the type of purchase:");
                        specificTypesMenu.showMenu();
                        manager.sortAndShow(getSpecificTypeFromInput());
                        break;

                    default:
                        break;
                }
            }
        } while (choice != 4);
        System.out.println();
    }

    private ItemType getSpecificTypeFromInput() {
        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                return ItemType.FOOD;

            case 2:
                return ItemType.CLOTHES;

            case 3:
                return ItemType.ENTERTAINMENT;

            default:
                return ItemType.OTHER;
        }
    }

    private void runAddPurchaseLoop() {
        int choice;

        do {
            System.out.println();
            addPurchaseMenu.showMenu();
            choice = Integer.parseInt(scanner.nextLine());
            ItemType purchaseType = ItemType.OTHER;

            if (choice != 5) {
                switch (choice) {
                    case 1:
                        purchaseType = ItemType.FOOD;
                        break;

                    case 2:
                        purchaseType = ItemType.CLOTHES;
                        break;

                    case 3:
                        purchaseType = ItemType.ENTERTAINMENT;
                        break;

                    case 4:
                        purchaseType = ItemType.OTHER;
                        break;

                    default:
                        break;
                }

                System.out.println("\nEnter purchase name:");
                String purchaseName = scanner.nextLine();

                System.out.println("Enter its price:");
                Float price = Float.parseFloat(scanner.nextLine());

                manager.addPurchase(new Item(purchaseName, price, purchaseType));

                System.out.println("Purchase was added!");
            }
        } while (choice != 5);
        System.out.println();
    }

    private void runShowPurchasesLoop() {
        if (manager.getTotalPurchases() == 0) {
            System.out.println("Purchase list is empty!");
        } else {
            int choice;

            do {
                System.out.println();
                purchasesMenu.showMenu();
                choice = Integer.parseInt(scanner.nextLine());

                if (choice != 6) {
                    switch (choice) {
                        case 1:
                            manager.showPurchases(ItemType.FOOD);
                            break;

                        case 2:
                            manager.showPurchases(ItemType.CLOTHES);
                            break;

                        case 3:
                            manager.showPurchases(ItemType.ENTERTAINMENT);
                            break;

                        case 4:
                            manager.showPurchases(ItemType.OTHER);
                            break;

                        case 5:
                            manager.showPurchases();
                            break;

                        default:
                            break;
                    }
                }
            } while (choice != 6);
            System.out.println();
        }
    }

    private void saveToFile() {
        File file = new File("./purchases.txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(manager.getPurchasesForFileContent());
        } catch (IOException ex) {
            System.out.println("An exception occurred: " + ex.getLocalizedMessage());
        }
    }

    private void loadFromFile() {
        File file = new File("./purchases.txt");

        try (Scanner scanner = new Scanner(file)) {
            // Clear the purchases
            manager.clearPurchases();

            // Get the income total
            String total = scanner.nextLine();
            manager.setBalance(Float.parseFloat(total));

            // Get each line from the file and convert to an Item and add to purchases
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] item = line.split(",");

                var newItem = new Item(item[0], Float.parseFloat(item[1]), ItemType.valueOf(item[2]));
                manager.addPurchase(newItem);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("An exception occurred: " + ex.getLocalizedMessage());
        }
    }
}

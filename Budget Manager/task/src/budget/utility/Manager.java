package budget.utility;

import budget.utility.strategy.PurchaseSorter;
import budget.utility.strategy.SortAll;
import budget.utility.strategy.SortByCertainType;
import budget.utility.strategy.SortByType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Manager {
    private float balance;
    private float purchaseTotal;
    private List<Item> purchases;
    private PurchaseSorter sorter;

    public Manager() {
        this.balance = 0f;
        this.purchaseTotal = 0f;
        this.purchases = new ArrayList<>();
        this.sorter = new PurchaseSorter();
    }

    public void addIncome(float amount) {
        this.balance += amount;
    }

    public void addPurchase(Item item) {
        this.purchases.add(item);
        this.purchaseTotal += item.price;
        this.balance -= item.price;
        if (this.balance < 0) {
            this.balance = 0;
        }
    }

    public void showPurchases() {
        if (!this.purchases.isEmpty()) {
            System.out.println();
            for (Item item : this.purchases) {
                System.out.println(item);
            }
            System.out.println(String.format("Total sum: $%.2f\n", this.purchaseTotal));
        } else {
            System.out.println("\nPurchase list is empty!\n");
        }
    }

    public void showPurchases(ItemType type) {
        Predicate<Item> byType = item -> item.getType() == type;
        var itemsOfType = purchases.stream().filter(byType).collect(Collectors.toList());

        if (!itemsOfType.isEmpty()) {
            float total = 0;
            for (Item item : itemsOfType) {
                total += item.getPrice();
            }
            System.out.println();
            itemsOfType.forEach(System.out::println);
            System.out.println(String.format("Total sum: $%.2f\n", total));
        } else {
            System.out.println("\nPurchase list is empty!\n");
        }
    }

    public void showBalance() {
        System.out.println(String.format("\nBalance: $%.2f\n", this.balance));
    }

    public void setBalance(float newBalance) {
        balance = newBalance;
    }

    public int getTotalPurchases() {
        return purchases.size();
    }

    public String getPurchasesForFileContent() {

        StringBuilder builder = new StringBuilder();

        // Add income total
        builder.append(purchaseTotal + balance + "\n");

        for (var item: purchases) {
            builder.append(item.name + "," + item.getPrice() + "," + item.getType() + "\n");
        }

        return builder.toString();
    }

    public void clearPurchases() {
        balance += purchaseTotal;
        purchaseTotal = 0f;
        purchases.clear();
    }

    public void sortAndShow(int choice) {

        switch (choice) {
            case 1:
                sorter.setAlgorithm(new SortAll(purchases));
                break;

            case 2:
                sorter.setAlgorithm(new SortByType(purchases));

            default:
                break;
        }

        sorter.sort();
        System.out.println(sorter.getString());
    }

    public void sortAndShow(ItemType type) {
        sorter.setAlgorithm(new SortByCertainType(purchases, type));
        sorter.sort();
        System.out.println(sorter.getString());
    }
}

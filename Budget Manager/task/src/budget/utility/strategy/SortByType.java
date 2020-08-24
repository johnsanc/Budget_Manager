package budget.utility.strategy;

import budget.utility.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByType implements SortAlgorithm {
    private List<Item> items;
    private List<PurchaseCategory> totalPurchases;

    public SortByType(List<Item> items) {
        this.items = items;
        totalPurchases = new ArrayList<>();
    }

    @Override
    public void sort() {

        var foodPurchases = new ArrayList<Item>();
        var entertainmentPurchases = new ArrayList<Item>();
        var clothesPurchases = new ArrayList<Item>();
        var otherPurchases = new ArrayList<Item>();

        // Populate each category
        for (Item item : items) {
            switch (item.getType()) {
                case FOOD:
                    foodPurchases.add(item);
                    break;

                case CLOTHES:
                    clothesPurchases.add(item);
                    break;

                case ENTERTAINMENT:
                    entertainmentPurchases.add(item);
                    break;

                case OTHER:
                    otherPurchases.add(item);
                    break;

                default:
                    break;
            }
        }

        // Get totals for each category
        float foodTotal = foodPurchases.stream().reduce(0f, (subtotal, item) -> subtotal + item.getPrice(), Float::sum);
        float entertainmentTotal = entertainmentPurchases.stream().reduce(0f, (subtotal, item) -> subtotal + item.getPrice(), Float::sum);
        float clothingTotal = clothesPurchases.stream().reduce(0f, (subtotal, item) -> subtotal + item.getPrice(), Float::sum);
        float otherTotal = otherPurchases.stream().reduce(0f, (subtotal, item) -> subtotal + item.getPrice(), Float::sum);

        totalPurchases.add(new PurchaseCategory("Food", foodTotal));
        totalPurchases.add(new PurchaseCategory("Entertainment", entertainmentTotal));
        totalPurchases.add(new PurchaseCategory("Clothes", clothingTotal));
        totalPurchases.add(new PurchaseCategory("Other", otherTotal));

        // Sort descending
        Comparator<PurchaseCategory> compareByCost = (PurchaseCategory p1, PurchaseCategory p2) -> Float.compare(p1.total, p2.total);
        Collections.sort(totalPurchases, compareByCost.reversed());
    }

    public String getStringResult() {
        StringBuilder builder = new StringBuilder();
        float total = totalPurchases.stream().reduce(0f, (subtotal, purchase) -> subtotal + purchase.total, Float::sum);

        builder.append("\nTypes:\n");

        for (var purchase : totalPurchases) {
            builder.append(String.format("%s - $%.2f\n", purchase.name, purchase.total));
        }

        builder.append(String.format("Total: $%.2f", total));

        return builder.toString();
    }
}

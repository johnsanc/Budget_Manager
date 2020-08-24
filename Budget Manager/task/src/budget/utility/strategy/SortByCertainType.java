package budget.utility.strategy;

import budget.utility.Item;
import budget.utility.ItemType;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByCertainType implements SortAlgorithm {
    private List<Item> list;
    private ItemType type;
    private List<Item> filteredResults;

    public SortByCertainType(List<Item> list, ItemType type) {
        this.list = list;
        this.type = type;
    }

    @Override
    public void sort() {
        filteredResults = list.stream().filter(item -> item.getType() == type).collect(Collectors.toList());
        Comparator<Item> compareByCost = (Item item1, Item item2) -> Float.compare(item1.getPrice(), item2.getPrice());
        Collections.sort(filteredResults, compareByCost.reversed());
    }

    public String getStringResult() {

        if (filteredResults.size() != 0) {
            StringBuilder builder = new StringBuilder();
            float totalSum = filteredResults.stream().reduce(0f, (subtotal, item) -> subtotal + item.getPrice(), Float::sum);

            builder.append(String.format("\n%s:\n", type.toString()));

            for (var item : filteredResults) {
                builder.append(String.format("%s\n", item.toString()));
            }

            builder.append(String.format("Total sum: $%.2f", totalSum));

            return builder.toString();
        }

        return "\nPurchase list is empty!";
    }
}

package budget.utility.strategy;

import budget.utility.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortAll implements SortAlgorithm {
    private List<Item> list;

    public SortAll(List<Item> list) {
        this.list = list;
    }

    @Override
    public void sort() {
        Comparator<Item> compareByPrice = (Item item1, Item item2) -> Float.compare(item1.getPrice(), item2.getPrice());
        Collections.sort(list, compareByPrice.reversed());
    }

    @Override
    public String getStringResult() {
        if (list.size() != 0) {
            StringBuilder builder = new StringBuilder();
            float total = list.stream().reduce(0f, (subtotal, item) -> subtotal + item.getPrice(), Float::sum);

            builder.append("\nAll:\n");

            for (var item : list) {
                builder.append(String.format("%s\n", item.toString()));
            }

            builder.append(String.format("Total: $%.2f", total));
            return builder.toString();
        }
        return "\nPurchase list is empty!";
    }
}

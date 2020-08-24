package budget.utility;

public class Item {
    String name;
    float price;
    ItemType type;

    public Item(String name, float price, ItemType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", this.name, this.price);
    }
}

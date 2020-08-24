package budget.utility.strategy;

public class PurchaseSorter {
    private SortAlgorithm algorithm;

    public void setAlgorithm(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void sort() {
        this.algorithm.sort();
    }

    public String getString() {
        return this.algorithm.getStringResult();
    }
}

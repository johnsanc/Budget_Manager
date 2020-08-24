package budget.utility;

public enum ItemType {
    FOOD,
    CLOTHES,
    ENTERTAINMENT,
    OTHER;

    public String convertToTitleCase(String string) {
        boolean convert = true;
        var builder = new StringBuilder();

        for (char ch : string.toCharArray()) {
            if (convert) {
                ch = Character.toTitleCase(ch);
                convert = false;
            }
            builder.append(ch);
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return convertToTitleCase(super.toString());
    }
}

package budget.utility;

import java.util.List;

public class Menu {
    private List<String> menuItems;

    public Menu(List<String> menuItems) {
        this.menuItems = menuItems;
    }

    public void showMenu() {
        for (String option : this.menuItems) {
            System.out.println(option);
        }
    }
}

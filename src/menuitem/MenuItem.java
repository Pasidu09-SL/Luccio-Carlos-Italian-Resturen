package menuitem;

public class MenuItem {
    // Private fields for the name and price
    private String name;
    private double price;

    //Constructor to create a new MenuItem
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    //Get the name of the menu item
    public String getName() {
        return name;
    }

    //Gets the price of the menu item
    public double getPrice() {
        return price;
    }
}
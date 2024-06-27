package oder;

import menuitem.MenuItem; //Import the MenuItem
import java.util.*;

public class Order {
    // A private List to store the MenuItem
    private List<MenuItem> items;

    // Constructor to initialize the items list
    public Order() {
        this.items = new ArrayList<>();
    }

    // Method to add a MenuItem to the order
    public void addItem(MenuItem item) {
        items.add(item);
    }

    // Method to return the list of items in the order
    public List<MenuItem> getItems() {
        return items;
    }

    // Method to calculate the total cost of all items in the order
    public double getTotalCost() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.getPrice(); // Add the price of the current item to the total cost
        }
        return total; // Return the total cost
    }

    // Method to display the order
    public void displayOrder() {
        System.out.println("Items in the Order:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName() + " - $" + items.get(i).getPrice());
        }
    }

    // Method to remove item from the order
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        } else {
            System.out.println("Invalid index. Item not removed from the order.");
        }
    }
}
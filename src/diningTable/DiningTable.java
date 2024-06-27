package diningTable;

import java.util.*;
import oder.Order; //Import order class 
import customer.Customer;
import menuitem.MenuItem;

public class DiningTable {

    private int number; // Table number
    private boolean isBooked; // Status of the table
    private int dinersCount; // number of diners at the table
    private List<Order> orders; // List of the oder for the table
    public String paymentType; // Payment type of the order
    public double tipAmount; // Tip amount
    private Stack<List<Order>> orderHistory; // Stack to maintain order history
    private Queue<Customer> waitingList; // Queue to manage the waiting list

    // Constructor to initialize a DiningTable object
    public DiningTable(int number) {
        this.number = number;
        this.isBooked = false;
        this.dinersCount = 0;
        this.orders = new ArrayList<>();
        this.paymentType = "cash";
        this.tipAmount = 0.0;
        this.orderHistory = new Stack<>();
        this.waitingList = new LinkedList<>();
        orders = new ArrayList<>();
    }

    // Gert table number
    public int getNumber() {
        return number;
    }

    // get the status of the table
    public boolean isBooked() {
        return isBooked;
    }

    // Set the table booked
    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    // Get diners count at the table
    public int getDinersCount() {
        return dinersCount;
    }

    // Set the dinars count at the table
    public void setDinersCount(int dinersCount) {
        if (dinersCount > 0 && dinersCount <= 8) {
            this.dinersCount = dinersCount;
        } else {
            throw new IllegalArgumentException("Number of diners must be between 1 and 8.");
        }
    }

    // Get the orders list
    public List<Order> getOrders() {
        return orders;
    }

    // Add oder to the oder list
    public void addOrder(Order order) {
        orders.add(order);
        saveOrderHistory(); // Save the current state of orders
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        saveOrderHistory(); // Save the current state of orders
    }

    private void saveOrderHistory() {
        List<Order> ordersCopy = new ArrayList<>(orders);
        orderHistory.push(ordersCopy);
    }

    public void undo() {
        if (!orderHistory.isEmpty()) {
            orders = orderHistory.pop();
        } else {
            System.out.println("No more actions to undo.");
        }
    }

    public void addToWaitingList(Customer customer) {
        waitingList.add(customer);
        System.out.println(customer.getName() + " added to waiting list.");
    }

    public void removeFromWaitingList(Customer customer) {
        if (waitingList.remove(customer)) {
            System.out.println(customer.getName() + " removed from waiting list.");
        } else {
            System.out.println(customer.getName() + " is not in the waiting list.");
        }
    }

    // Calculate and get the total cost
    public double getTotalCost() {
        double total = 0.0;
        for (Order order : orders) {
            total += order.getTotalCost();
        }
        return total;
    }

    // Set payment type
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    // Set tip amount
    public void setTipAmount(double tipAmount) {
        this.tipAmount = tipAmount;
    }

    // Calculate the final Bill
    public double calculateFinalBill() {
        double totalCost = getTotalCost();
        if (paymentType.equalsIgnoreCase("card")) {
            totalCost *= 1.10; // Adding 10% charge
        }
        totalCost += tipAmount; // Adding tip
        return totalCost;
    }

    // Add method to display current order
    public void displayCurrentOrder() {
        System.out.println("Current Order for Table " + number + ":");
        for (Order order : orders) {
            order.displayOrder();
        }
    }

    // Add method to add item to order
    public void addItemToOrder(MenuItem item) {
        if (!orders.isEmpty()) {
            orders.get(orders.size() - 1).addItem(item);
        } else {
            System.out.println("No active order exists for Table " + number + ". Please place an order first.");
        }
    }

    // Add method to remove item from order
    public void removeItemFromOrder(int index) {
        if (!orders.isEmpty()) {
            orders.get(orders.size() - 1).removeItem(index);
        } else {
            System.out.println("No active order exists for Table " + number + ". Please place an order first.");
        }
    }
}

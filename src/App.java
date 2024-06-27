import java.util.*;

import customer.Customer;
import menuitem.MenuItem; //Import menuItem class 
import oder.Order; //Import order class 
import diningTable.DiningTable; //Import diningTable class 

class App {
    private List<DiningTable> tables;
    private Queue<Customer> waitingList;

    // Method to add a customer to the waiting list
    public void addToWaitingList(Customer customer) {
        waitingList.add(customer);
        System.out.println(customer.getName() + " has been added to the waiting list.");
    }

    // Method to remove a customer from the waiting list
    public Customer removeFromWaitingList(Customer customer) {
        if (!waitingList.isEmpty()) {
            Customer nextCustomer = waitingList.poll();
            System.out.println(nextCustomer.getName() + " has been removed from the waiting list.");
            return nextCustomer;
        } else {
            System.out.println("The waiting list is empty.");
            return null;
        }
    }

    // Constructor to initialize the list of dining tables
    public App() {
        this.tables = new ArrayList<>();
        waitingList = new LinkedList<>();
        initializeTables();
    }

    // Method to initialize the dining tables
    private void initializeTables() {
        for (int i = 1; i <= 5; i++) {
            tables.add(new DiningTable(i));
        }
    }

    // Method to display the status of all dining tables
    public void displayTableStatus() {
        System.out.print(
                "\n|===============|   |===============|   |===============|   |===============|   |===============|\n");

        // Display Table numbers
        for (DiningTable table : tables) {
            System.out.print("|    Table " + table.getNumber() + "    |");
            System.out.print("   ");
        }
        System.out.println("");
        // display each table booked or free
        for (DiningTable table : tables) {
            System.out.print((table.isBooked() ? "|     Booked    |" : "|      Free     |"));
            System.out.print("   ");
        }
        System.out.println("");
        // Display the number of diners each tables
        for (DiningTable table : tables) {
            System.out.print("|    " + (table.getDinersCount()) + " diners   |");
            System.out.print("   ");
        }
        System.out.print(
                "\n|===============|   |===============|   |===============|   |===============|   |===============|\n");

    }

    // Method to add a customer name to the waiting list
    public void addToWaitingList(String customerName) {
        Customer customer = new Customer(customerName); // Create a new Customer object with the provided name
        waitingList.add(customer);
        System.out.println(customerName + " has been added to the waiting list.");
    }

    // Method to remove a customer name from the waiting list
    public boolean removeFromWaitingList(String customerName) {
        // Iterate through the waiting list to find the customer with the provided name
        Iterator<Customer> iterator = waitingList.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (customer.getName().equals(customerName)) {
                iterator.remove(); // Remove the customer from the waiting list
                System.out.println(customerName + " has been removed from the waiting list.");
                return true; // Return true indicating successful removal
            }
        }
        // If the customer is not found in the waiting list
        System.out.println(customerName + " is not in the waiting list.");
        return false; // Return false indicating that the customer was not found
    }

    // Method to book a table diners
    public void bookTable(int tableNumber, int dinersCount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name to book Table " + tableNumber + ": ");
        String customerName = scanner.nextLine();

        // Check if the customer is in the waiting list
        if (isCustomerInWaitingList(customerName)) {
            // If the customer is in the waiting list, remove them from the waiting list
            removeFromWaitingList(customerName);
            // Book the table for the customer
            DiningTable table = tables.get(tableNumber - 1);
            if (!table.isBooked()) {
                table.setBooked(true);
                table.setDinersCount(dinersCount);
                System.out.println("Table " + table.getNumber() + " is booked for " + dinersCount + " diners for "
                        + customerName + ".");
            } else {
                System.out.println("Table " + table.getNumber() + " is already booked.");
            }
        } else {
            // If the customer is not in the waiting list, display an error message
            System.out.println(customerName + " is not in the waiting list. Cannot book the table.");
        }
    }

    // Method to check if a customer is in the waiting list
    private boolean isCustomerInWaitingList(String customerName) {
        for (Customer customer : waitingList) {
            if (customer.getName().equalsIgnoreCase(customerName)) {
                return true;
            }
        }
        return false;
    }

    // Method to pale an Oder
    public void placeOrder(int tableNumber, Order order) {
        DiningTable table = tables.get(tableNumber - 1);
        if (!table.isBooked()) { // Check if the table is booked
            System.out.println("Table " + table.getNumber() + " is not booked.");
            return;
        }

        // Add oder to the table
        table.addOrder(order);
        System.out.println("Order placed for Table " + table.getNumber());
        processOrder(order);
    }

    // Method to procces the oder
    private void processOrder(Order order) {
        System.out.println("Processing order...");
        try {
            Thread.sleep(1000); // Simulate processing time (1 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order processed.");
    }

    // Method to display the final bill
    public void displayFinalBill(int tableNumber) {
        DiningTable table = tables.get(tableNumber - 1);
        if (table.isBooked()) {
            double totalCost = table.getTotalCost();
            double finalBill = table.calculateFinalBill();
            System.out.println("\n|=========================================|");
            System.out.println("|                                         |");
            System.out.println("|  Table " + table.getNumber() + ":                               |");
            System.out.println("|     Total cost before charges: $" + String.format("%06.2f", totalCost) + "  |");
            if (table.paymentType.equalsIgnoreCase("card")) {
                System.out.println(
                        "|     Credit card charge (10%): $" + String.format("%06.2f", totalCost * 0.10) + "   |");
            }
            System.out.println("|     Tip: $" + String.format("%06.2f", table.tipAmount) + "                        |");
            System.out.println("|     Final bill: $" + String.format("%06.2f", finalBill) + "                 |");
            System.out.println("|                                         |");
            System.out.println("|=========================================|");
        } else {
            System.out.println("Table " + tableNumber + " is not booked.");
        }
    }

    // Method to display overal Report
    public void displayOverallReport() {
        // Initialize variables to get ovaral
        double overallIncome = 0.0;
        double totalTips = 0.0;
        double maxSpent = 0.0;
        int maxSpentTable = 0;

        // Initialize maps to get the count of different menuu items
        Map<String, Integer> pastaAndSpaghettiCount = new HashMap<>();
        Map<String, Integer> dessertCount = new HashMap<>();
        Map<String, Integer> alcoholicBeveragesCount = new HashMap<>();
        Map<String, Integer> nonAlcoholicBeveragesCount = new HashMap<>();

        // Loop to get all tables purchesed items
        for (DiningTable table : tables) {
            if (table.isBooked()) {
                double finalBill = table.calculateFinalBill();
                overallIncome += finalBill;
                totalTips += table.tipAmount;
                double totalCost = table.getTotalCost();

                if (totalCost > maxSpent) {
                    maxSpent = totalCost;
                    maxSpentTable = table.getNumber();
                }

                // Count different types of menu items oder at the table
                for (Order order : table.getOrders()) {
                    for (MenuItem item : order.getItems()) {
                        String itemName = item.getName();
                        // Catagorize Menu items
                        if (itemName.toLowerCase().contains("spaghetti") || itemName.toLowerCase().contains("risotto")
                                || itemName.toLowerCase().contains("fettuccine")
                                || itemName.toLowerCase().contains("gnocchi")
                                || itemName.toLowerCase().contains("orecchiette")) {
                            pastaAndSpaghettiCount.put(itemName, pastaAndSpaghettiCount.getOrDefault(itemName, 0) + 1);
                        } else if (itemName.toLowerCase().contains("dessert")
                                || itemName.toLowerCase().contains("tiramisu")
                                || itemName.toLowerCase().contains("sfogliatella")
                                || itemName.toLowerCase().contains("zabaglione") ||
                                itemName.toLowerCase().contains("torta")) {
                            dessertCount.put(itemName, dessertCount.getOrDefault(itemName, 0) + 1);
                        } else if (itemName.toLowerCase().contains("prosecco")
                                || itemName.toLowerCase().contains("amaretto") ||
                                itemName.toLowerCase().contains("limoncello")) {
                            alcoholicBeveragesCount.put(itemName,
                                    alcoholicBeveragesCount.getOrDefault(itemName, 0) + 1);
                        } else if (itemName.toLowerCase().contains("san pellegrino")
                                || itemName.toLowerCase().contains("chinotto") ||
                                itemName.toLowerCase().contains("orzata")) {
                            nonAlcoholicBeveragesCount.put(itemName,
                                    nonAlcoholicBeveragesCount.getOrDefault(itemName, 0) + 1);
                        }
                    }
                }
            }
        }

        // Display overal income and tips
        System.out.println("Overall final total for the income: $" + String.format("%06.2f", overallIncome));
        System.out.println("Total amount of tips collected: $" + String.format("%06.2f", totalTips));
        System.out.println(
                "Table that spent the most: Table " + maxSpentTable + " with $" + String.format("%06.2f", maxSpent));

        // Display popularity of items
        System.out.println("\nPasta & Spaghetti in order of popularity:");
        pastaAndSpaghettiCount.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " orders"));

        System.out.println("\nDesserts in order of popularity:");
        dessertCount.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " orders"));

        System.out.println("\nAlcoholic Beverages in order of popularity:");
        alcoholicBeveragesCount.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " orders"));

        System.out.println("\nNon-Alcoholic Beverages in order of popularity:");
        nonAlcoholicBeveragesCount.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " orders"));
    }

    // Main method to run application
    public static void main(String[] args) {
        App restaurant = new App();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display the Main menu
            System.out.println("\n:======================================:");
            System.out.println("|               Main Menu              |");
            System.out.println(":======================================:");
            System.out.println("|                                      |");
            System.out.println("|  1. Display Table Status             |");
            System.out.println("|  2. Add Customer to Wating List      |");
            System.out.println("|  3. Book a Table                     |");
            System.out.println("|  4. Remove Customer from Wating List |");
            System.out.println("|  5. Place an Order                   |");
            System.out.println("|  6. Change the Order                 |");
            System.out.println("|  7. Display Final Bill for a Table   |");
            System.out.println("|  8. Display Overall Report           |");
            System.out.println("|  0/9. Exit                           |");
            System.out.println("|______________________________________|\n");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // Display Table status
                    restaurant.displayTableStatus();
                    break;
                case 2:
                    // Add customer to waiting list
                    System.out.print("Enter customer name to add to waiting list: ");
                    String customerNameToAdd = scanner.nextLine();
                    restaurant.addToWaitingList(customerNameToAdd);
                    System.out.println(customerNameToAdd + " added to the waiting list.");
                    break;
                case 3: // Book table
                    System.out.print("\nEnter table number to book: ");
                    int tableToBook = scanner.nextInt();
                    System.out.print("Enter number of diners (1-8): ");
                    int dinersCount = scanner.nextInt();
                    try {
                        restaurant.bookTable(tableToBook, dinersCount);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    // Remove customer from waiting list
                    System.out.print("Enter customer name to remove from waiting list: ");
                    String customerNameToRemove = scanner.nextLine();
                    if (restaurant.removeFromWaitingList(customerNameToRemove)) {
                        System.out.println(customerNameToRemove + " removed from the waiting list.");
                    } else {
                        System.out.println(customerNameToRemove + " is not in the waiting list.");
                    }
                    break;
                case 5: // Place an order
                    System.out.print("\nEnter table number to place order: ");
                    int tableToOrder = scanner.nextInt();
                    scanner.nextLine();

                    Order order = new Order();// Display the item menu
                    System.out.println(
                            "\n:=============================================================================:");
                    System.out
                            .println("|                                    Menu Item                                |");
                    System.out
                            .println(":=============================================================================:");
                    System.out
                            .println("|          Pasta & Spaghetti          | |               Dessert               |");
                    System.out
                            .println(":-------------------------------------: :-------------------------------------:");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println("|  01. Spaghetti Carbonara   - $12.50 | |  09. Tiramisu              - $10.00 |");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println("|  02. Risotto alla Milanese - $12.00 | |  10. Sfogliatella          - $ 7.00 |");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println("|  03. Fettuccine Alfredo    - $14.00 | |  11. Zabaglione            - $ 7.00 |");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println("|  04. Potato Gnocchi        - $14.00 | |  12. Torta della Nonna     - $ 9.00 |");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println("|  05. Orecchiette           - $14.00 | |  13. Zabaglione            - $ 7.00 |");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println(":-------------------------------------- --------------------------------------:");
                    System.out
                            .println("|          Alcoholic Beverages        | |       Non-Alcoholic Beverages       |");
                    System.out
                            .println(":-------------------------------------- --------------------------------------:");
                    System.out
                            .println("|              Per Bottle             | |              Per Bottle             |");
                    System.out
                            .println("|  06. Prosecco              - $25.00 | |  14. San Pellegrino 500ml  - $ 2.00 |");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println("|  07. Amaretto              - $27.50 | |  15. Chinotto 330ml        - $ 2.25 |");
                    System.out
                            .println("|                                     | |                                     |");
                    System.out
                            .println("|  08. Limoncello            - $20.00 | |  16. Orzata 750ml          - $12.50 |");
                    System.out
                            .println("|_____________________________________| |_____________________________________|");

                    System.out.print("\nEnter item number to add to order (0 to finish): ");

                    while (true) {
                        int itemNumber = scanner.nextInt();
                        if (itemNumber == 0) {
                            break;
                        }
                        scanner.nextLine(); // Consume newline

                        // Add seleted item to the order
                        switch (itemNumber) {
                            case 1:
                                order.addItem(new MenuItem("Spaghetti Carbonara", 12.50));
                                break;
                            case 2:
                                order.addItem(new MenuItem("Risotto alla Milanese", 14.00));
                                break;
                            case 3:
                                order.addItem(new MenuItem("Fettuccine Alfredo", 12.00));
                                break;
                            case 4:
                                order.addItem(new MenuItem("Potato Gnocchi", 14.00));
                                break;
                            case 5:
                                order.addItem(new MenuItem("Orecchiette", 14.00));
                                break;
                            case 6:
                                order.addItem(new MenuItem("Prosecco", 25.00));
                                break;
                            case 7:
                                order.addItem(new MenuItem("Amaretto", 27.50));
                                break;
                            case 8:
                                order.addItem(new MenuItem("Limoncello", 20.00));
                                break;
                            case 9:
                                order.addItem(new MenuItem("Tiramisu", 10.00));
                                break;
                            case 10:
                                order.addItem(new MenuItem("Sfogliatella", 7.00));
                                break;
                            case 11:
                                order.addItem(new MenuItem("Zabaglione", 7.00));
                                break;
                            case 12:
                                order.addItem(new MenuItem("Torta della Nonna", 9.00));
                                break;
                            case 13:
                                order.addItem(new MenuItem("Zabaglione", 7.00));
                                break;
                            case 14:
                                order.addItem(new MenuItem("San Pellegrino 500ml", 2.00));
                                break;
                            case 15:
                                order.addItem(new MenuItem("Chinotto 330ml", 2.25));
                                break;
                            case 16:
                                order.addItem(new MenuItem("Orzata 750ml", 12.50));
                                break;
                            default:
                                System.out.println("Invalid item number.");
                                break;
                        }

                        System.out.print("\nEnter next item number (0 to finish): ");
                    }

                    restaurant.placeOrder(tableToOrder, order);// Place the Oder
                    break;
                case 6: // Modify customer order
                    // Ask for table number
                    System.out.print("Enter table number to modify order: ");
                    int tableToModifyNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Check if the table is booked
                    DiningTable tableToModify = restaurant.tables.get(tableToModifyNumber - 1);
                    if (!tableToModify.isBooked()) {
                        System.out.println("Table " + tableToModifyNumber + " is not booked. Cannot modify order.");
                        break;
                    }

                    // Display current order
                    System.out.println("Current order for Table " + tableToModifyNumber + ":");
                    tableToModify.displayCurrentOrder();

                    // Ask for modification options
                    System.out.println("\nModification Options:");
                    System.out.println("1. Add item");
                    System.out.println("2. Remove item");
                    System.out.println("0. Exit");

                    System.out.print("Enter your choice: ");
                    int modificationChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (modificationChoice) {
                        case 1: // Add item
                            // Ask for item details
                            System.out.print("Enter item name to add: ");
                            String itemNameToAdd = scanner.nextLine();
                            System.out.print("Enter item price: ");
                            double itemPriceToAdd = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline
                            // Add item to order
                            tableToModify.addItemToOrder(new MenuItem(itemNameToAdd, itemPriceToAdd));
                            System.out.println("Item added to order.");
                            break;
                        case 2: // Remove item
                            // Ask for item index to remove
                            System.out.print("Enter index of item to remove: ");
                            int itemIndexToRemove = scanner.nextInt();
                            // Remove item from order
                            tableToModify.removeItemFromOrder(itemIndexToRemove);
                            System.out.println("Item removed from order.");
                            break;
                        case 0: // Exit
                            break;
                        default: // Validation
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;

                case 7: // Display the final bill for the each table
                    System.out.print("Enter table number to display final bill: ");
                    int tableToBill = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    DiningTable table = restaurant.tables.get(tableToBill - 1);
                    if (table.isBooked()) {
                        System.out.print("Enter payment type for Table " + table.getNumber() + " (cash/card): ");
                        String paymentType = scanner.next();
                        table.setPaymentType(paymentType);

                        System.out.print("Enter tip amount for Table " + table.getNumber() + ": ");
                        double tipAmount = scanner.nextDouble();
                        table.setTipAmount(tipAmount);
                    }
                    restaurant.displayFinalBill(tableToBill);
                    break;
                case 8: // Display Overall Reprot
                    restaurant.displayOverallReport();
                    break;
                case 0: // Esc Application
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                case 9: // Esc Application
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default: // Validation
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}

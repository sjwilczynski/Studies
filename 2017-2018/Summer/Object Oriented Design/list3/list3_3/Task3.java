package list3_3;

public class Task3 {
    public static void main(String[] args) {

        TaxCalculator taxCalc = new TaxCalculator(0.28D);
        Item[] items = {new Item("mouse", 50), new Item("keyboard", 60), new Item("pc", 999.99D)};
        CashRegisterOld old = new CashRegisterOld();
        CashRegister cashRegister = new CashRegister();
        double p1 = old.calculatePrice(items);
        double p2 = cashRegister.calculatePrice(items, taxCalc);
        System.out.println("Prices: " + p1 + ' ' + p2);
        old.printBill(items);
        cashRegister.printBill(items, new AlphabeticSorter(), taxCalc);
        cashRegister.printBill(items, new PriceSorter(), taxCalc);
    }
}

class TaxCalculatorOld {
    public double calculateTax(double price) {
        return price * 0.22D;
    }
}

class TaxCalculator {
    private double taxRatio;

    TaxCalculator(double taxRatio) {
        this.taxRatio = taxRatio;
    }

    public double calculateTax(double price) {
        return price * taxRatio;
    }
}

class Item {
    private final String name;
    private final double price;

    Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class CashRegisterOld {
    private TaxCalculatorOld taxCalc = new TaxCalculatorOld();

    public double calculatePrice(Item[] items) {
        double price = 0;
        for (Item item : items) {
            price += item.getPrice() + taxCalc.calculateTax(item.getPrice());
        }
        return price;
    }

    public void printBill(Item[] items) {
        for (Item item : items)
            System.out.println("item: " + item.getName() + " price " + item.getPrice()
                    + " tax " + taxCalc.calculateTax(item.getPrice()));
    }
}

class CashRegister {
    public double calculatePrice(Item[] items, TaxCalculator taxCalc) {
        double price = 0;
        for (Item item : items) {
            price += item.getPrice() + taxCalc.calculateTax(item.getPrice());
        }
        return price;
    }

    public void printBill(Item[] items, ItemSorter itemSorter, TaxCalculator taxCalc) {
        if (itemSorter != null) {
            itemSorter.sortItems(items);
        }
        for (Item item : items)
            System.out.println("item: " + item.getName() + " price " + item.getPrice()
                    + " tax " + taxCalc.calculateTax(item.getPrice()));
    }
}
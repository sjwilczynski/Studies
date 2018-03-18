package list3_1;

public class SaleLineItem {

    private int quantity;
    private long id;
    private String name;
    private long price;

    SaleLineItem(int quantity, long id, String name, long price) {
        this.quantity = quantity;
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getSubTotal() {
        return quantity * price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }
}

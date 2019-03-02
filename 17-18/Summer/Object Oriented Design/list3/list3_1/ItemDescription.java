package list3_1;


import java.util.List;

//6. Protected variation thanks to Descriptor?
public class ItemDescription implements Descriptor {

    //5. High cohesion - methods are connected to each other
    public String getDescription(Sale sale) {
        List<SaleLineItem> items = sale.getItems();
        StringBuilder desc = new StringBuilder();
        for (SaleLineItem item : items) {
            desc.append(getDescription(item));
        }
        return desc.toString();
    }

    private String getDescription(SaleLineItem item) {
        return String.format("The item's price is %d", item.getPrice());
    }
}

package list3_1;

import java.util.ArrayList;
import java.util.List;

public class Sale implements HasPrice{
    private List<SaleLineItem> items;

    Sale(){
        items = new ArrayList<>();
    }

    //4. Creator - Sale has a list of SaleLineItems - so it creates them
    public void addLine(int quantity, long id, String name, long price) {
        items.add(new SaleLineItem(quantity, id, name, price));
    }

    //2. Information expert - Sale has everything to calculate total price - that's why we put it here
    @Override
    public long getTotalPrice() {
        long total = 0;
        for (SaleLineItem item : items) {
            total += item.getSubTotal();
        }
        return total;
    }

    //3. Low coupling - Sale depends gets all information needed from SaleLineItem - it depends only on one class

    public List<SaleLineItem> getItems() {
        return items;
    }
}

package list3_1;

import java.util.ArrayList;
import java.util.List;

public class Task1 {

    public static void main(String[] args) {
        List<HasPrice> sales = new ArrayList<>();
        Sale sale1 = new Sale();
        sale1.addLine(5,0,"item1", 10);
        SecretSale sale2 = new SecretSale(20);
        sales.add(sale1);
        sales.add(sale2);
        long total = 0;
        //1. Polymorphism
        for (HasPrice sale: sales){
            total += sale.getTotalPrice();
        }
        System.out.println("Total price is " + total);
        System.out.print(new ItemDescription().getDescription(sale1));

    }
}

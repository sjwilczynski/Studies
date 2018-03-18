package list3_1;

public class SecretSale implements HasPrice {
    private long price;

    SecretSale(long price){
        this.price = price;
    }

    @Override
    public long getTotalPrice() {
        return 10*price;
    }
}

package list3_3;

import java.util.Arrays;
import java.util.Comparator;

public interface ItemSorter {
    void sortItems(Item[] items);
}

class AlphabeticSorter implements ItemSorter {

    @Override
    public void sortItems(Item[] items) {
        Arrays.sort(items, Comparator.comparing(Item::getName));
    }
}

class PriceSorter implements ItemSorter {

    @Override
    public void sortItems(Item[] items) {
        Arrays.sort(items, Comparator.comparing(Item::getPrice));
    }
}

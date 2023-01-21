package hu.kovacspeterzoltan.bootcamp.store;

public interface StorePersistance {
    StoreItem loadItem(String productName);
    void saveItem(StoreItem item);
}
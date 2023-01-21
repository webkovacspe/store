package hu.kovacspeterzoltan.bootcamp.store;

public interface StoreRegister {
    int sellProductItem(String productName, int numberOfProduct);
    void buyProductItem(String productName, int numberOfProduct) throws ItemNotAvailableException;
    void createProduct(String productName);
    void setPersistanceType(StorePersistanceType persistanceType);
}
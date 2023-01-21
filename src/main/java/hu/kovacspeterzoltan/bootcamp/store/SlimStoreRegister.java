package hu.kovacspeterzoltan.bootcamp.store;

public class SlimStoreRegister implements StoreRegister {
    private StorePersistanceType persistanceType;

    private StorePersistance persistanceRepo;

    @Override
    public int sellProductItem(String productName, int numberOfProduct) {
        StoreItem item = getStoreItem(productName);

        int returnNumber = numberOfProduct;
        if (numberOfProduct <= item.numberOfProduct) {
            item.numberOfProduct -= numberOfProduct;
        }
        else {
            returnNumber = item.numberOfProduct;
            item.numberOfProduct = 0;
        }
        persistanceRepo.saveItem(item);
        return returnNumber;
    }
    @Override
    public void buyProductItem(String productName, int numberOfProduct) {
        StoreItem item = getStoreItem(productName);
        item.numberOfProduct += numberOfProduct;
        persistanceRepo.saveItem(item);
    }

    private StoreItem getStoreItem(String productName) {
        StoreItem item = persistanceRepo.loadItem(productName);
        if (item == null)
            throw new ItemNotAvailableException();
        return item;
    }

    @Override
    public void createProduct(String productName) {
        StoreItem item = new StoreItem();
        item.productName = productName;
        persistanceRepo.saveItem(item);
    }
    @Override
    public void setPersistanceType(StorePersistanceType persistanceType) {
        this.persistanceType = persistanceType;

        switch (this.persistanceType) {
            case InMemory -> persistanceRepo = new H2Repo();
            case File -> persistanceRepo = new CSVRepo();
            default -> throw new RuntimeException();
        }
    }
}
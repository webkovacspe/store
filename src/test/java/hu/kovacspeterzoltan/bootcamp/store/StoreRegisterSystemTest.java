package hu.kovacspeterzoltan.bootcamp.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoreRegisterSystemTest {

    private String secondProductName;
    private String firstProductName;
    private StoreRegister storeRegister;
    private int soldNumberOfItem;

    @BeforeEach
    void setUp() {
        secondProductName = "ProductTwo";
        firstProductName = "ProductOne";
        storeRegister = new SlimStoreRegister();
        storeRegister.setPersistanceType(StorePersistanceType.InMemory);
    }

    @Test
    void customerBuyOneItem() {
        addStoreItems();

        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 1);
        assertEquals(1, soldNumberOfItem);
    }

    @Test
    void customerBuyDifferentItem() {
        addStoreItems();

        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 1);
        assertEquals(1, soldNumberOfItem);
        soldNumberOfItem = storeRegister.sellProductItem(secondProductName, 5);
        assertEquals(5, soldNumberOfItem);
    }

    @Test
    void customerTryToBuyMoreAndMoreItem() {
        addStoreItems();

        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 25);
        assertEquals(20, soldNumberOfItem);
        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 25);
        assertEquals(0, soldNumberOfItem);
    }

    @Test
    void buyItemsIfRunOut() {
        addStoreItems();

        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 25);
        assertEquals(20, soldNumberOfItem);

        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 25);
        assertEquals(0, soldNumberOfItem);

        storeRegister.buyProductItem(firstProductName, 6);
        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 25);
        assertEquals(6, soldNumberOfItem);

    }

    @Test
    void itemIsNotExists() {
        ItemNotAvailableException thrown = Assertions.assertThrowsExactly(ItemNotAvailableException.class, () -> {
            storeRegister.sellProductItem(firstProductName, 25);
        });
    }

    @Test
    void useFileStorage() {
        storeRegister.setPersistanceType(StorePersistanceType.File);

        addStoreItems();

        soldNumberOfItem = storeRegister.sellProductItem(firstProductName, 1);
        assertEquals(1, soldNumberOfItem);
    }

    private void addStoreItems() {
        storeRegister.createProduct(firstProductName);
        storeRegister.buyProductItem(firstProductName, 20);
        storeRegister.createProduct(secondProductName);
        storeRegister.buyProductItem(secondProductName, 10);
    }
}
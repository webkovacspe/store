package hu.kovacspeterzoltan.bootcamp.store;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVRepo implements StorePersistance {
    private static String csvPath = "./productCsvRepo.csv";
    private List<StoreItem> list;

    @Override
    public StoreItem loadItem(String productName) {
        loadData();
        return null;
    }
    @Override
    public void saveItem(StoreItem item) {

    }
    private void loadData() {
        list = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(csvPath));
            String[] line;
            while ((line = reader.readNext()) != null) {
                StoreItem item = new StoreItem();
                item.numberOfProduct = Integer.parseInt(line[0]);
                item.productName = line[1];
                list.add(item);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

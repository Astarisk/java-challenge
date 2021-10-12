package astarisk.java.challenge.services;

import astarisk.java.challenge.models.Product;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService extends Service<Product> {

    @Override
    public Product findById(int id) {
        for (Product entry : entryList) {
            if (entry.getId() == id)
                return entry;
        }
        return null;
    }

    @Override
    public Product createEntry(Product entry) {
        entry.setId(getNextId());
        entryList.add(entry);
        return entry;
    }

    @Override
    public Product updateEntry(Product entry) {
        Product oldEntry = findById(entry.getId());

        if(oldEntry != null)
            oldEntry.setName(entry.getName());
            oldEntry.setDescription(entry.getDescription());
            oldEntry.setAlbum(entry.getAlbum());
        return oldEntry;
    }

    @Override
    public Product deleteEntry(int id) {
        Product deleteThis = findById(id);
        if (entryList.contains(deleteThis)) {
            entryList.remove(deleteThis);
            return deleteThis;
        }
        return null;
    }
}

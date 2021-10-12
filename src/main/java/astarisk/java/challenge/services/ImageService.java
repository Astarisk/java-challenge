package astarisk.java.challenge.services;

import astarisk.java.challenge.models.Image;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ImageService extends Service<Image> {

    @Override
    public Image findById(int id) {
        for (Image entry : entryList) {
            if (entry.getId() == id)
                return entry;
        }
        return null;
    }

    @Override
    public Image createEntry(Image entry) {
        entry.setId(getNextId());
        entryList.add(entry);
        return entry;
    }

    @Override
    public Image updateEntry(Image entry) {
        Image oldEntry = findById(entry.getId());

        if(oldEntry != null)
            oldEntry.setTitle(entry.getTitle());
            oldEntry.setDescription(entry.getDescription());
            oldEntry.setAlbums(entry.getAlbums());
        return oldEntry;
    }

    @Override
    public Image deleteEntry(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}

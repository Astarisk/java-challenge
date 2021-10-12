package astarisk.java.challenge.services;

import astarisk.java.challenge.models.Album;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlbumService extends Service<Album> {

    @Override
    public Album findById(int id) {
        for (Album entry : entryList) {
            if (entry.getId() == id)
                return entry;
        }
        return null;
    }

    @Override
    public Album createEntry(Album entry) {
        if (entry.getImages() == null)
            entry.setImages(new ArrayList<Integer>());

        entry.setId(getNextId());
        entryList.add(entry);
        return entry;
    }

    @Override
    public Album updateEntry(Album entry) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Album deleteEntry(int id) {
        // TODO Auto-generated method stub
        return null;
    }
}

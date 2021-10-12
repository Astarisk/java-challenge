package astarisk.java.challenge.services;

import java.util.ArrayList;

public abstract class Service<T> {
    protected final ArrayList<T> entryList = new ArrayList<>();
    private int counter;

    public abstract T findById(int id);
    public abstract T createEntry(T entry);
    public abstract T updateEntry(T entry);
    public abstract T deleteEntry(int id);

    protected int getNextId() {
        return counter++;
    }

    public ArrayList<T> listAll() {
        return entryList;
    }
}
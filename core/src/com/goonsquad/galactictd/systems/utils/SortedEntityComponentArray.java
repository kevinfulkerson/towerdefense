package com.goonsquad.galactictd.systems.utils;

import com.artemis.Component;
import com.artemis.ComponentMapper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedEntityComponentArray<E extends Component> implements Iterator<Integer>, Iterable<Integer> {
    private int[] sortedEntityIds;
    private int iteratingPosition;
    private int size;
    private Comparator comparator;
    private ComponentMapper mapper;

    public SortedEntityComponentArray(Comparator<E> comparator, ComponentMapper<E> mapper) {
        this.comparator = comparator;
        this.mapper = mapper;
        this.sortedEntityIds = new int[500];
        this.size = 0;
    }

    public void insert(int entityId) {
        boolean added = false;
        Component addedComponent = mapper.get(entityId);

        for (int i = 0; i < size; i++) {
            Component currentComponent = mapper.get(sortedEntityIds[i]);

            if (comparator.compare(addedComponent, currentComponent) <= 0) {
                this.insert(i, entityId);
                added = true;
                break;
            }
        }

        if (!added) {
            this.insert(size, entityId);
        }
    }

    private void insert(int index, int entityId) {
        if (sortedEntityIds.length == size) expandSorted();
        //Shift everything in the array to the right if it is after the new index to insert.
        for (int i = size; i > index; i--) {
            sortedEntityIds[i] = sortedEntityIds[i - 1];
        }
        sortedEntityIds[index] = entityId;
        size++;
    }

    public void remove(int entityId) {
        for (int i = 0; i < size; i++) {
            if (sortedEntityIds[i] == entityId) {
                removeIndex(i);
                break;
            }
        }
    }

    private void removeIndex(int index) {
        for (int i = index; i < size; i++) {
            sortedEntityIds[i] = sortedEntityIds[i + 1];
        }
        size--;
    }

    //TODO
    //Make the array expand.
    private void expandSorted() {

    }

    @Override
    public boolean hasNext() {
        return iteratingPosition + 1 < size;
    }

    @Override
    public Integer next() {
        if (iteratingPosition + 1 >= size) {
            throw new NoSuchElementException();
        }
        return this.sortedEntityIds[++iteratingPosition];
    }

    @Override
    public Iterator<Integer> iterator() {
        this.iteratingPosition = -1;
        return this;
    }
}

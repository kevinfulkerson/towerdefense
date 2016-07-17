package com.goonsquad.galactictd.systems.utils;

import com.artemis.Component;
import com.artemis.ComponentMapper;

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
        this(comparator, mapper, 20);
    }

    public SortedEntityComponentArray(Comparator<E> comparator, ComponentMapper<E> mapper, int initialCapacity) {
        this.comparator = comparator;
        this.mapper = mapper;
        this.sortedEntityIds = new int[initialCapacity];
        this.size = 0;
    }

    public void insertValue(int entityId) {
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
        System.arraycopy(sortedEntityIds, index, sortedEntityIds, index + 1, size - index);
        sortedEntityIds[index] = entityId;
        size++;
    }

    public void removeValue(int entityIdToRemove) {
        for (int i = 0; i < size; i++) {
            if (sortedEntityIds[i] == entityIdToRemove) {
                removeIndex(i);
                break;
            }
        }
    }

    private void removeIndex(int index) {
        if (sortedEntityIds.length == size) expandSorted();
        //Shifts everything in the array left that is past the index to remove.
        System.arraycopy(sortedEntityIds, index + 1, sortedEntityIds, index, size - index);
        size--;
    }

    private void expandSorted() {
        int[] expandedEntityIds = new int[sortedEntityIds.length * 2];
        System.arraycopy(sortedEntityIds, 0, expandedEntityIds, 0, sortedEntityIds.length);
        sortedEntityIds = expandedEntityIds;
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

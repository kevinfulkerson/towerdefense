package com.goonsquad.galactictd.systems.utils;

import com.artemis.Component;
import com.artemis.ComponentMapper;

import java.util.Arrays;
import java.util.Comparator;

public class SortedEntityComponentArray<E extends Component> {
    private int[] sortedEntityIds;
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

    private void expandSorted() {

    }

    public int[] getSortedEntityIds() {
        return Arrays.copyOf(sortedEntityIds, size);
    }
}

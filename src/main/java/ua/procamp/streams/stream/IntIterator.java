package ua.procamp.streams.stream;

import java.util.Iterator;

public class IntIterator implements Iterator<Integer> {
    protected Iterator<Integer> intIterator;

    public IntIterator(Iterator<Integer> iterator) {
        this.intIterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return intIterator.hasNext();
    }

    @Override
    public Integer next() {
        return intIterator.next();
    }
}

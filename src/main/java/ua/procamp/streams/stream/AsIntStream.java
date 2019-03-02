package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;
import java.util.ArrayList;
import java.util.List;


public class AsIntStream implements IntStream {
    private IntIterator streamIterator;

    private AsIntStream( IntIterator streamIterator) {
        this.streamIterator = streamIterator;
    }

    public static IntStream of(int... values) {
        List<Integer> list = new ArrayList<>();

        for(Integer val : values)
            list.add(val);

        return new AsIntStream(new IntIterator(list.iterator()));
    }

    @Override
    public Double average() {
        if (!streamIterator.hasNext())
            throw new IllegalArgumentException("The stream is empty! The exception threw an `average` method");

        long count = 0;
        double sum = 0;

        while (streamIterator.hasNext()){
            sum += streamIterator.next();
            count++;
        }
        return sum / count;
    }

    @Override
    public Integer max() {
        if (!streamIterator.hasNext())
            throw new IllegalArgumentException("The stream is empty! The exception threw a `max` method");

        Integer max = streamIterator.next();

        while (streamIterator.hasNext()){
            Integer current = streamIterator.next();
            max = (current > max) ? current : max;
        }
        return max;
    }

    @Override
    public Integer min() {
        if (!streamIterator.hasNext())
            throw new IllegalArgumentException("The stream is empty! The exception threw a `min` method");

        Integer min = streamIterator.next();

        while (streamIterator.hasNext()){
            Integer current = streamIterator.next();
            min = (current < min) ? current : min;
        }
        return min;
    }

    @Override
    public long count() {
        long count = 0L;

        while (streamIterator.hasNext()){
            streamIterator.next();
            count++;
        }
        return count;
    }

    @Override
    public Integer sum() {
        if (!streamIterator.hasNext())
            throw new IllegalArgumentException("The stream is empty! The exception threw a `sum` method");

        Integer sum = 0;

        while (streamIterator.hasNext()){
            sum += streamIterator.next();
        }
        return sum;
    }
//Make without a list
    @Override
    public IntStream filter(IntPredicate predicate) {
        streamIterator = new IntIterator(AsIntStream.this.streamIterator){
            private Integer nextItem = null;

            @Override
            public boolean hasNext() {
                return nextByPredicate(false) != null;
            }

            @Override
            public Integer next() {
                return nextByPredicate(true);
            }
//TODO
            private Integer nextByPredicate(boolean flagReset){
                Integer temp;
                if (nextItem != null){
                    if (flagReset){
                        temp = nextItem;
                        nextItem = null;
                        return temp;
                    } else {
                        return nextItem;
                    }
                }

                while (intIterator.hasNext()){
                    temp = intIterator.next();

                    if  (predicate.test(temp)){
                        nextItem = temp;
                        return nextItem;
                    }
                }
                return null;
            }
        };
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        while (streamIterator.hasNext()){
            action.accept(streamIterator.next());
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        streamIterator = new IntIterator(streamIterator){
            @Override
            public Integer next() {
                return mapper.apply(intIterator.next());
            }
        };
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        List<Integer> listStreams = new ArrayList<>();
        while (streamIterator.hasNext()){
            func.applyAsIntStream(streamIterator.next()).forEach(listStreams::add);
        }
        return new AsIntStream(new IntIterator(listStreams.iterator()));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        while (streamIterator.hasNext()){
           identity = op.apply(identity,streamIterator.next());
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        List<Integer> listInt = new ArrayList<>();
        while (streamIterator.hasNext()){
            listInt.add(streamIterator.next());
        }
        int[] resArray = new int[listInt.size()];

        for (int i = 0; i < listInt.size(); i++){
            resArray[i] = listInt.get(i);
        }
        return resArray;
    }

}

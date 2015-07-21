package uk.co.qmunity.lib.misc;

import java.util.Map.Entry;

public class Pair<K, V> implements Entry<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {

        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {

        return key;
    }

    @Override
    public V getValue() {

        return value;
    }

    @Override
    public V setValue(V value) {

        return this.value = value;
    }

    public K setKey(K key) {

        return this.key = key;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (!(obj instanceof Pair<?, ?>))
            return false;
        Pair<?, ?> p = (Pair<?, ?>) obj;

        if ((getKey() == null) != (p.getKey() == null))
            return false;
        if ((getValue() == null) != (p.getValue() == null))
            return false;

        return getKey().equals(p.getKey()) && getValue().equals(p.getValue());
    }

}

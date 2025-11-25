package model;

import model.linkedlist.LinkedList;

public class HashMapNode<K, V> {
    private final K key;
    private LinkedList<V> values;

    public HashMapNode(K key, V value) {
        this.key = key;
        values = new LinkedList<>();
        values.addFirst(value);
    }

    public K getKey() {
        return key;
    }

    public void addValue(V value) {
        values.addFirst(value);
    }

    public V removeValue(V value) {
        return values.remove(value) ? value : null;
    }

    public int getSize() {
        return values.size();
    }

    public LinkedList<V> getValues() {
        return values;
    }

    public V findValue(V value) {
        for(V v : values) {
            if(v.equals(value)) {
                return v;
            }
        }
        return null;
    }
}
package model.hashmap;

public class HashMapNode<K, V> {
    private final K key;
    private final V value;

    public HashMapNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue(){
        return value;
    }
}
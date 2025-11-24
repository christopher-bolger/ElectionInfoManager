package model;

import model.linkedlist.LinkedList;

//TODO:
// this is just linear searching with extra steps
// rethink
public class CustomHashMap<K, V>{
    private int size = 0;
    private LinkedList<HashMapNode<Integer, V>> keyValuePairs = new LinkedList<>();

    public void add(K key, V value) {
        int hash = key.hashCode();
        HashMapNode<Integer, V> node = containsKey(hash);
        if(node != null){
            node.addValue(value);
        }else{
            keyValuePairs.add(new HashMapNode<>(hash, value));
            size++;
        }
    }

    private HashMapNode<Integer, V> containsKey(int hash){
        for(HashMapNode<Integer, V> node : keyValuePairs){
            if(node.getKey() == hash){
                return node;
            }
        }
        return null;
    }
}

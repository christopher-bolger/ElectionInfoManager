package model;

import model.linkedlist.LinkedList;

import java.util.HashMap;

//TODO:
// ok I don't need a hashMap unless im planning on hashing stuff elsewhere which just seems like its over the top
// maybe i'll just stick with a hashtable or something.
// hashMapNode can still be used for it but I don't think it really needs to store its own key
public class CustomHashMap<K, V>{
    private int size = 0;
    private HashMapNode<K, V>[] map;

    public CustomHashMap() {
        map = new HashMapNode[10];
    }

    public void add(V value) {
        if(size >= map.length/2) {
            rehash();
        }

    }

    //TODO
    // getKeys method to be used here
    private void rehash() {
        HashMapNode<K, V>[] oldMap = map;
        map = new HashMapNode[map.length*2];
        for(int i = 0; i < map.length; i++){
            if(map[i] != null){
                LinkedList<V> values = map[i].getValues();
                for(V value : values) add(value);
            }
        }
    }

    private boolean containsKey(K key){
        return false;
    }
}

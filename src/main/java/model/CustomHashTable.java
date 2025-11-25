package model;

import model.linkedlist.LinkedList;

//TODO:
// ok I don't need a hashMap unless im planning on hashing stuff elsewhere which just seems like its over the top
// maybe i'll just stick with a hashtable or something.
// hashMapNode can still be used for it but I don't think it really needs to store its own key
public class CustomHashTable<V>{
    private int size = 0;
    private LinkedList<V>[] map;

    public CustomHashTable() {
        map = new LinkedList[10];
    }

    public LinkedList<V> get(int key) {
        return map[key%map.length];
    }

    public void add(V value) {
        if(size >= map.length/2)
            rehash();
        map[value.hashCode()%map.length].addFirst(value);
        size++;
    }

    public void remove(V value) {
        map[value.hashCode()%map.length].remove(value);
        size--;
    }

    private void rehash() {
        LinkedList<V>[] oldMap = map;
        map = new LinkedList[map.length + (map.length/2 + 1)];
        size = 0;
        for(int i = 0; i < map.length; i++) {
            if(!map[i].isEmpty()) {
                for(V v : map[i]) {
                    add(v);
                }
            }
        }
    }
}

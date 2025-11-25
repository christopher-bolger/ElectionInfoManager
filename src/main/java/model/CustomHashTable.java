package model;

import model.linkedlist.LinkedList;

//TODO:
// man I really suck at getting my head around this,
// need a LinkedList of Nodes that contains K & V.
// Then hashing should be done by partial fields only
// no point in using every field for a specific objects hashCode() because that literally
// defeats the purpose of this.
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

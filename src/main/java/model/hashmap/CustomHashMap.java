package model.hashmap;

import model.linkedlist.LinkedList;

// I think this covers what I need
public class CustomHashMap<K, V>{
    private int size = 0;
    private LinkedList<HashMapNode<K,V>>[] list;

    public CustomHashMap() {
        list = new LinkedList[10];
    }

    public void put(K key, V value) {
        if(size >= list.length/2)
            rehash();
        int index = key.hashCode()%list.length;
        list[index].addFirst(new HashMapNode<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = key.hashCode()%list.length;
        for(HashMapNode<K,V> node : list[index])
            if(node.getKey().equals(key))
                return node.getValue();
        return null;
    }

    public void remove(K key) {
        int index = key.hashCode()%list.length;
        for(HashMapNode<K,V> node : list[index])
            if(node.getKey().equals(key))
                if(list[index].remove(node))
                    size--;
    }

    private void rehash() {
        LinkedList<HashMapNode<K,V>>[] oldList = list;
        list = new LinkedList[list.length + (list.length/2+1)];
        size = 0;
        for(int i = 0; i < oldList.length; i++)
            if(!oldList[i].isEmpty())
                for(HashMapNode<K,V> node : oldList[i])
                    put(node.getKey(), node.getValue());
    }

    public LinkedList<K> getKeys(){
        if(size == 0)
            return null;
        LinkedList<K> keys = new LinkedList<>();
        for(int i = 0; i < list.length; i++){
            if(!list[i].isEmpty())
                for(HashMapNode<K,V> node : list[i])
                    keys.add(node.getKey());
        }
        return keys;
    }
}

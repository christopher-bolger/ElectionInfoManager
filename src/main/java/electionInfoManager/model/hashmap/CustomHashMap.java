package electionInfoManager.model.hashmap;

import electionInfoManager.model.linkedlist.LinkedList;

// I think this covers what I need
public class CustomHashMap<K, V>{
    private int size = 0;
    private LinkedList<HashMapNode<K,V>>[] list;

    public CustomHashMap() {
        list = new LinkedList[10];
        for(int i = 0; i<10; i++){
            list[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        if(size >= list.length/2)
            rehash();
        int index = Math.abs(key.hashCode())%list.length;
        list[index].addFirst(new HashMapNode<>(key, value));
        size++;
    }

    public V get(K key) {
        //System.out.println(key.hashCode());
        int index = Math.abs(key.hashCode())%list.length;
        for(HashMapNode<K,V> node : list[index])
            if(node != null && node.key.equals(key))
                return node.value;
        return null;
    }

    public boolean remove(K key) {
        int index = Math.abs(key.hashCode())%list.length;
        for(HashMapNode<K,V> node : list[index])
            if(node.key.equals(key))
                if(list[index].remove(node)) {
                    size--;
                    return true;
                }
        return false;
    }

    private void rehash() {
        LinkedList<HashMapNode<K,V>>[] oldList = list;
        list = new LinkedList[list.length + (list.length/2+1)];
        for(int i = 0; i<list.length; i++) list[i] = new LinkedList<>();
        size = 0;
        for (LinkedList<HashMapNode<K, V>> hashMapNodes : oldList)
            if (!hashMapNodes.isEmpty())
                for (HashMapNode<K, V> node : hashMapNodes)
                    put(node.key, node.value);
    }

    public LinkedList<K> getKeys(){
        if(size == 0)
            return null;
        LinkedList<K> keys = new LinkedList<>();
        for(int i = 0; i < list.length; i++){
            if(!list[i].isEmpty())
                for(HashMapNode<K,V> node : list[i])
                    keys.add(node.key);
        }
        return keys;
    }
}

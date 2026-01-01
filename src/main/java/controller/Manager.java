package controller;
import model.hashmap.CustomHashMap;
import model.linkedlist.LinkedList;

public abstract class Manager<E> {
    protected LinkedList<E> list;
    protected CustomHashMap<Integer, E> map;

    public Manager() {
        list = new LinkedList<>();
        map = new CustomHashMap<>();
    }

    public boolean add(E e){
        if(map.get(e.hashCode()) == null){
            list.add(e);
            map.put(e.hashCode(), e);
            return true;
        }
        return false;
    }

    public E remove(E e){
        E removed = map.get(e.hashCode());
        if(removed != null){
            list.remove(e);
            map.remove(e.hashCode());
            return removed;
        }
        return null;
    }

    public E find(E e){
        return map.get(e.hashCode());
    }

    public LinkedList<E> getList(){
        return list;
    }

    public abstract void sort();
}

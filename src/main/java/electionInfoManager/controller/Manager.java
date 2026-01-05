package electionInfoManager.controller;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.hashmap.CustomHashMap;
import electionInfoManager.model.linkedlist.LinkedList;

import java.util.Comparator;

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

    public void set(LinkedList<E> toAdd){
        list.addAll(toAdd);
    }

    public E find(E e){
        return map.get(e.hashCode());
    }

    public LinkedList<E> getList(){
        return list;
    }

    public CustomHashMap<Integer, E> getMap(){
        return map;
    }

    public void setMap(CustomHashMap<Integer, E> toAdd){
        if(toAdd != null)
            map = toAdd;
    }

    public abstract void sort(Comparator<E> c);

    public abstract LinkedList<E> search(String searchFilter, String text);
}

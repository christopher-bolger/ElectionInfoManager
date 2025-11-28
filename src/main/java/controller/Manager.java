package controller;
import model.hashmap.CustomHashMap;
import model.linkedlist.LinkedList;
import utility.Sort;

import java.util.Comparator;

public abstract class Manager<E> {
    private LinkedList<E> list;
    private CustomHashMap<Integer, E> map;

    public Manager() {
        list = new LinkedList<>();
        map = new CustomHashMap<>();
    }

    public void add(E e){
        list.add(e);
        map.put(e.hashCode(), e);
    }

    public E remove(E e){
        E found = list.get(e);
        if(found != null){
            list.remove(e);
            map.remove(e.hashCode());
            return found;
        }
        return null;
    }

    public void sortAsc(Comparator<E> comparator){
        Sort.mergeSort(list, comparator);
    }

    public void sortDesc(Comparator<E> comparator){
        Sort.mergeSort(list, comparator);
    }
}

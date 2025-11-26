package utility;

import model.linkedlist.LinkedList;

import java.util.Comparator;

public final class Sort{
    public static <E> LinkedList<E> sortAscending(LinkedList<E> list, Comparator<E> comparator){
        if(list.size() < 2)
            return list;
        if(list.size() == 2){
            int index = comparator.compare(list.get(0), list.get(1));
            if(index == 0){
                return list;
            }else if(index > 0){
                E old = list.get(0);
                list.set(0, list.get(1));
                list.set(1, old);
                return list;
            }else{
                return list;
            }
        }
        LinkedList<E> one = new LinkedList<>(), two = new LinkedList<>();
        int mean = 0;
        return null;
    }
}

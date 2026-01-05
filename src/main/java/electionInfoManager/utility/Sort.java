package electionInfoManager.utility;

import electionInfoManager.model.linkedlist.LinkedList;
import java.util.Comparator;

public final class Sort{
    public static <E> void mergeSort(LinkedList<E> list, Comparator<E> comparator){
        if(list.size() < 2)
            return;

        int size = list.size();
        LinkedList<E> one = new LinkedList<>(), two = new LinkedList<>();
        one.addAll(list.subList(0, size/2));
        two.addAll(list.subList(size/2, size));
        mergeSort(one, comparator);
        mergeSort(two, comparator);

        int index = 0,oneCount = 0, twoCount = 0, oneSize = one.size(), twoSize = two.size();
        while(oneCount < oneSize && twoCount < twoSize){
            E item = comparator.compare(one.get(oneCount), two.get(twoCount)) < 0 ? one.get(oneCount++) : two.get(twoCount++);
            list.set(index++, item);
        }
        while(oneCount < oneSize) list.set(index++, one.get(oneCount++));
        while(twoCount < twoSize) list.set(index++, two.get(twoCount++));
    }
}

package controller;

import model.election.Politician;
import model.hashmap.CustomHashMap;
import model.linkedlist.LinkedList;
import utility.Sort;

import java.util.Comparator;

public class PoliticianManager extends Manager<Politician> {

    public PoliticianManager() {
        super();
    }

    public boolean addPolitician(Politician politician) {
        return super.add(politician);
    }

    public boolean removePolitician(Politician politician) {
        if(map.get(politician.hashCode()) == null) {
            return false;
        }
        map.remove(politician.hashCode());
        list.remove(politician);
        return true;
    }

    public LinkedList<Politician> getPoliticianList() {
        return list;
    }

    public Politician getPolitician(int politicianHash) {
        return map.get(politicianHash);
    }

    public Politician getPolitician(String politicianName, String politicianCounty) {
        return map.get(politicianName.hashCode() + politicianCounty.hashCode());
    }

    public void reset(){
        list.clear();
        map = new CustomHashMap<>();
    }

    public void sortByAffiliation(){
        Sort.mergeSort(list, Comparator.comparing(Politician::getAffiliation));
    }

    @Override
    public void sort() {
        Sort.mergeSort(list, Comparator.comparing(Politician::getName));
    }
}

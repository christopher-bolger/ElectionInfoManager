package electionInfoManager.controller;

import electionInfoManager.model.election.Politician;
import electionInfoManager.model.hashmap.CustomHashMap;
import electionInfoManager.model.linkedlist.LinkedList;
import electionInfoManager.utility.Sort;

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

    public boolean updatePolitician(Politician current, Politician updated) {
        System.out.println("In method");
        System.out.println(list.contains(current));
        System.out.println(map.get(current.hashCode()) == current);
        if(map.get(current.hashCode()) != null && updated != null) {
            System.out.println("In if");
            Politician toUpdate = map.get(current.hashCode());
            toUpdate.setName(current.getName());
            toUpdate.setCounty(updated.getCounty());
            toUpdate.setAffiliation(updated.getAffiliation());
            toUpdate.setDOB(updated.getDOB());
            toUpdate.setPhotoURL(updated.getPhotoURL());
            System.out.println(toUpdate);
            return toUpdate.equals(updated);
        }
        return false;
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

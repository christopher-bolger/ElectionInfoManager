package controller;

import model.election.Election;
import model.election.Politician;
import model.hashmap.CustomHashMap;
import model.linkedlist.LinkedList;
import utility.Sort;
import java.util.Comparator;

public class ElectionManager extends Manager<Election> {
    CustomHashMap<Politician, LinkedList<String>> affiliations;

    public ElectionManager() {
        super();
        affiliations = new CustomHashMap<>();
    }

    public void addElection(Election election) {
        super.add(election);
    }

    public boolean addPolitician(Politician pol, Election e) {
        if(super.find(e) == e) {
            e.add(pol);
            LinkedList<String> affiliation = affiliations.get(pol);
            if(affiliation != null) {
                affiliation.add(pol.getAffiliation());
            }else
                affiliations.get(pol).add(pol.getAffiliation());
            return true;
        }
        return false;
    }

    public boolean removeElection(Election election) {
        if(super.find(election) != null) {
            super.remove(election);
            return true;
        }
        return false;
    }

    public boolean removePolitician(Politician pol, Election election) {
        if(affiliations.get(pol) != null) {
            super.find(election).remove(pol);
            affiliations.get(pol).remove(pol.getAffiliation());
            return true;
        }
        return false;
    }

    public Election getElection(Election election) {
        return super.find(election);
    }

    public LinkedList<String> getAffiliations(Politician pol) {
        LinkedList<String> noDuplicatesAffiliations = new LinkedList<>();
        int duplicatesSize = 0;
        for(String affiliation : affiliations.get(pol)) {
            if(duplicatesSize == Politician.ELECTION_PARTIES.length)
                break;
            if(!noDuplicatesAffiliations.contains(affiliation)) {
                noDuplicatesAffiliations.add(affiliation);
                duplicatesSize++;
            }
        }
        return noDuplicatesAffiliations;
    }

    @Override
    public void sort() {
        Sort.mergeSort(list, Comparator.comparing(Election::getDate));
    }

    public void sortByType(){
        Sort.mergeSort(list, Comparator.comparing(Election::getElectionType));
    }

    public void sortByLocation(){
        Sort.mergeSort(list, Comparator.comparing(Election::getElectionLocation));
    }
}

package electionInfoManager.controller;

import electionInfoManager.model.election.Election;
import electionInfoManager.model.election.Politician;
import electionInfoManager.model.hashmap.CustomHashMap;
import electionInfoManager.model.linkedlist.LinkedList;
import electionInfoManager.utility.Sort;
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
        return super.remove(election) != null;
    }

    public boolean removePolitician(Politician pol, Election election) {
        if(affiliations.get(pol) != null) {
            super.find(election).remove(pol);
            affiliations.get(pol).remove(pol.getAffiliation());
            return true;
        }
        return false;
    }

    public boolean updateElection(Election current, Election updated){
        if(map.get(current.hashCode()) != null && updated != null) {
            Election toUpdate = map.get(current.hashCode());
            toUpdate.setElectionType(updated.getElectionType());
            toUpdate.setDate(updated.getDate());
            toUpdate.setWinners(updated.getWinners());
            toUpdate.setElectionLocation(updated.getElectionLocation());
            return toUpdate.equals(updated);
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

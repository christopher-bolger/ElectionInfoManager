package model.election;

import model.hashmap.CustomHashMap;
import model.linkedlist.LinkedList;
import utility.Sort;

import java.util.Comparator;

public class Election {
    public final static String[] ELECTION_TYPES = {"General", "Local", "European", "Presidential"};
    int electionType, electionYear, totalWinners;
    String electionLocation;
    LinkedList<ElectionEntry> politicians;
    CustomHashMap<Integer, ElectionEntry> candidates;

    public Election(int type, int year, int winners, String location) {
        electionType = Math.max(type, 0);
        electionYear = Math.max(year, 0);
        totalWinners = Math.max(winners, 1);

        if(location != null && !location.isEmpty())
            electionLocation = location;
        else electionLocation = "INVALID";

        politicians = new LinkedList<>();
        candidates = new CustomHashMap<>();
    }

    public void sortByVotes(){
        Sort.mergeSort(politicians, Comparator.comparingInt(ElectionEntry::getVotes));
    }

    public void add(Politician politician){
        politicians.add(new ElectionEntry(politician));
    }

    public void add(Politician politician, int votes){
        politicians.add(new ElectionEntry(politician, votes));
    }



}

package model.election;

import model.hashmap.CustomHashMap;
import model.linkedlist.LinkedList;
import utility.Sort;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

public class Election {
    public final static String[] ELECTION_TYPES = {"General", "Local", "European", "Presidential"};
    int electionType, totalWinners;
    LocalDate date;
    String electionLocation;
    LinkedList<ElectionEntry> politicians;
    CustomHashMap<Politician, ElectionEntry> candidates;

    public Election(int type, LocalDate date, int winners, String location) {
        electionType = Math.max(type, 0);
        totalWinners = Math.max(winners, 1);

        if(date != null)
            this.date = date;
        else this.date = LocalDate.now();

        if(location != null && !location.isEmpty())
            for(int i = 0; i < Politician.COUNTIES.length; i++)
                if(Politician.COUNTIES[i].equals(location))
                    electionLocation = Politician.COUNTIES[i];
        if(electionLocation != null && electionLocation.isEmpty())
            electionLocation = "INVALID";

        politicians = new LinkedList<>();
        candidates = new CustomHashMap<>();
    }

    public void sortByVotes(){
        Sort.mergeSort(politicians, Comparator.comparingInt(ElectionEntry::getVotes));
    }

    public void sortByAffiliation(){
        Sort.mergeSort(politicians, Comparator.comparing(ElectionEntry::getAffiliation));
    }

    public LinkedList<ElectionEntry> getCandidates() {
        return politicians;
    }

    private int getQuota(){ //Droop formula
        int totalVotes = 0;
        for(ElectionEntry e : politicians)
            totalVotes += e.getVotes();
        return (totalVotes / (totalWinners + 1)) + 1;
    }

    public LinkedList<ElectionEntry> getWinners(){
        LinkedList<ElectionEntry> winners = new LinkedList<>();
        return switch(electionType) {
            case 0, 1, 2 -> {
                int quota = getQuota();
                switch(electionType){
                    case 0 -> sortByAffiliation();
                    case 1, 2 -> sortByVotes();
                }
                for (ElectionEntry e : politicians) //i'd use a while loop if I had vote preferencing but since I don't I could get stuck in the loop
                    if (e.getVotes() > quota)
                        winners.add(e);
                yield winners;
            }
            case 3 -> {
                sortByVotes();
                winners.add(politicians.getFirst());
                yield winners;
            }
            default -> null;
        };
    }

    public void add(Politician politician){
        politicians.add(new ElectionEntry(politician));
        candidates.put(politician, new ElectionEntry(politician));
    }

    public void add(Politician politician, int votes){
        politicians.add(new ElectionEntry(politician, votes));
        candidates.put(politician, new ElectionEntry(politician));
        candidates.get(politician).setVotes(votes);
    }

    public void remove(Politician politician){
        politicians.remove(candidates.get(politician));
        candidates.remove(politician);
    }

    public String getAffiliation(Politician politician){
        return candidates.get(politician).getAffiliation();
    }

    public Politician getPolitician(ElectionEntry electionEntry){
        return candidates.get(electionEntry.getPolitician()).getPolitician();
    }

    public LocalDate getDate(){
        return date;
    }

    public String getElectionType(){
        return ELECTION_TYPES[electionType];
    }

    public String getElectionLocation(){
        return electionLocation;
    }

    public int hashCode(){
        return date.getYear() + (date.getMonthValue()*100 + date.getMonthValue() )+ date.getDayOfMonth();
    }
}

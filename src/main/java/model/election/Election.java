package model.election;

import model.hashmap.CustomHashMap;
import model.linkedlist.LinkedList;
import utility.Sort;

import java.time.LocalDate;
import java.util.Comparator;

public class Election {
    public final static String[] ELECTION_TYPES = {"General", "Local", "European", "Presidential"};
    int electionType, totalWinners;
    LocalDate date;
    String electionLocation;
    LinkedList<ElectionEntry> politicians;
    CustomHashMap<Politician, ElectionEntry> candidates;

    public Election(int type, LocalDate date, int winners, String location) {
        if(type < 4)
            electionType = Math.max(type, 0);
        else electionType = type%4;
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
        Sort.mergeSort(politicians, (o1, o2) -> o2.getVotes() - o1.getVotes());
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

    public LinkedList<ElectionEntry> calculateResults(){
        if(totalWinners > politicians.size())
            return null;
        LinkedList<ElectionEntry> temp = new LinkedList<>();
        switch(electionType){
            case 0,1,2 -> { //realized because im using droop I have to do vote redistribution
                temp = getTotalOverQuota();
                LinkedList<ElectionEntry> takenVotes = new LinkedList<>();
                while(temp.size() < totalWinners){
                    sortByVotes();
                    int votes = 0;
                    for(int i = politicians.size() - 1; i >= 0; i--) {
                        ElectionEntry e = politicians.get(i);
                        if (!takenVotes.contains(e) && e.getVotes() > 0) {
                            votes = e.getVotes();
                            takenVotes.add(e);
                            break;
                        }
                    }

                    LinkedList<ElectionEntry> manipulatedList = new LinkedList<>(); // creating a list of candidates that haven't already passed the threshold
                    for(Politician key : candidates.getKeys()) {
                        ElectionEntry e = candidates.get(key);
                        if(e.getVotes() > 0)
                            manipulatedList.add(e);
                    }
                    manipulatedList.removeAll(temp);

                    int votesToAdd = votes / manipulatedList.size(); //probably will have some loss here
                    for(ElectionEntry e : manipulatedList)
                        e.addVotes(votesToAdd);

                    temp = getTotalOverQuota();
                }
            }
            case 3 -> temp.add(politicians.getFirst());
        }
        return temp;
    }

    private LinkedList<ElectionEntry> getTotalOverQuota(){
        LinkedList<ElectionEntry> overQuota = new LinkedList<>();
        int quota = getQuota();
        for(ElectionEntry e : politicians)
            if(e.getVotes() >= quota)
                overQuota.add(e);
        return overQuota;
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

package electionInfoManager.model.election;

import electionInfoManager.model.hashmap.CustomHashMap;
import electionInfoManager.model.linkedlist.LinkedList;
import electionInfoManager.utility.Sort;

import java.time.LocalDate;
import java.util.Comparator;

public class Election {
    public final static String[] ELECTION_TYPES = {"General", "Local", "European", "Presidential"};
    public final static String[] FIELDS = {"Location", "Type", "Date", "Total Winners"};
    int totalWinners;
    LocalDate date;
    String electionLocation, electionType;
    LinkedList<ElectionEntry> politicians;
    CustomHashMap<Politician, ElectionEntry> candidates;

    public Election(String type, LocalDate date, int winners, String location) {
        if(type != null && !type.isEmpty())
            for(String s : ELECTION_TYPES)
                if(type.equalsIgnoreCase(s)) {
                    electionType = s;
                    break;
                }
        if(electionType == null)
            electionType = "General";

        totalWinners = Math.max(winners, 1);

        if(date != null)
            this.date = date;
        else this.date = LocalDate.now();

        if(location != null && !location.isEmpty())
            electionLocation = location;
        if(electionLocation != null && electionLocation.isEmpty())
            electionLocation = "INVALID";

        politicians = new LinkedList<>();
        candidates = new CustomHashMap<>();
    }

    public boolean updateVotes(Politician p, int votes){
        if(candidates.get(p) != null){
            candidates.get(p).setVotes(Math.max(votes, 0));
            return true;
        }
        return false;
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
        sortByVotes();
        setPositions();
        switch(electionType){
            case "General", "Local", "European" -> { //realized because im using droop I have to do vote redistribution
                int i = 0;
                while(i < totalWinners){
                    temp.add(politicians.get(i));
                    i++;
                }
            }
            case "Presidential" -> {
                sortByVotes();
                temp.add(politicians.getFirst());
            }
        }
        return temp;
    }

    public void setPositions(){
        for(int i = 0; i < politicians.size(); i++)
            politicians.get(i).setPosition(i+1);
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

    public boolean isEmpty(){
        return politicians.isEmpty() || politicians.size() < totalWinners;
    }

    public void add(Politician politician, int votes){
        politicians.add(new ElectionEntry(politician, votes));
        candidates.put(politician, new ElectionEntry(politician));
        candidates.get(politician).setVotes(votes);
    }

    public boolean remove(Politician politician){
        return politicians.remove(candidates.get(politician)) && candidates.remove(politician);
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

    public void setDate(LocalDate date){
        if(date != null)
            this.date = date;
    }

    public Politician getPolitician(Politician p){
        if(politicians != null && !politicians.isEmpty())
            for(ElectionEntry e : politicians){
                if(e.getPolitician() == p)
                    return e.getPolitician();
            }
        return null;
    }

    public boolean containsPolitician(Politician p){
        if(p != null){
            return candidates.get(p) != null;
        }
        return false;
    }

    public String getElectionType(){
        return electionType;
    }

    public void setElectionType(String type){
        if(type != null && !type.isEmpty())
            for(String s : ELECTION_TYPES)
                if(type.equalsIgnoreCase(s)) {
                    electionType = s;
                    break;
                }
    }

    public String getElectionLocation(){
        return electionLocation;
    }

    public void setElectionLocation(String location){
        if(location != null && !location.isEmpty())
            electionLocation = location;
    }

    public int getWinners(){
        return totalWinners;
    }

    public void setWinners(int winners){
        if(winners > 0)
            totalWinners = winners;
    }

    public ElectionEntry find(ElectionEntry e){
        return candidates.get(e.getPolitician());
    }

    public int hashCode(){
        return date.getYear() + (date.getMonthValue()*100 + date.getMonthValue() )+ date.getDayOfMonth();
    }
}

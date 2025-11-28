package model;

//If election manager is going to store a k,v[] pair of politions & electionsEntries
// then logically I shouldn't need to store the politician in the entry, they are the key
//maybe my logic is flawed here
public class ElectionEntry{
    int hashCode;
    private final String affiliation;
    private int votes;

    public ElectionEntry(int hashCode, String affiliation) {
        this.hashCode = hashCode;
        this.affiliation = affiliation;
    }

    public ElectionEntry(int hashCode, String affiliation, int votes) {
        this.hashCode = hashCode;
        this.affiliation = affiliation;
        this.votes = votes;
    }

    public int getPoliticianHashCode() {
        return hashCode;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void addVotes(int votes) {
        this.votes += votes;
    }

    @Override
    public int hashCode(){
        return hashCode;
    }
}

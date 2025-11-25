package model;

public class ElectionEntry{
    private final Politician politician;
    private final String affiliation;
    private int votes;

    public ElectionEntry(Politician politician, String affiliation) {
        this.politician = politician;
        this.affiliation = politician.getAffiliation();
    }

    public ElectionEntry(Politician politician, String affiliation, int votes) {
        this.politician = politician;
        this.affiliation = politician.getAffiliation();
        this.votes = votes;
    }

    public Politician getPolitician() {
        return politician;
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
        int hash = politician != null ? politician.hashCode() : 0;
        hash += affiliation != null ? affiliation.hashCode() : 0;
        return hash;
    }
}

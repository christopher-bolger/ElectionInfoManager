package model.election;

public class ElectionEntry{
    private Politician politician;
    private final String affiliation;
    private int votes;

    public ElectionEntry(Politician politician) {
        this.politician = politician;
        this.affiliation = politician.getAffiliation();
    }

    public ElectionEntry(Politician politician, int votes) {
        this.politician = politician;
        this.affiliation = politician.getAffiliation();
        if(votes > 0)
            this.votes = votes;
        else this.votes = 0;
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
        return politician.hashCode();
    }
}

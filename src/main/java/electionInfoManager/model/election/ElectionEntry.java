package electionInfoManager.model.election;

import java.util.Objects;

public class ElectionEntry{
    private final Politician politician;
    private final String affiliation;
    private int votes = 0, position = 0;

    public ElectionEntry(Politician politician) {
        this.politician = politician;
        this.affiliation = politician.getAffiliation();
    }

    public ElectionEntry(Politician politician, int votes) {
        this.politician = politician;
        this.affiliation = politician.getAffiliation();
        this.votes = Math.abs(votes);
    }

    public String getName(){
        return politician.getName();
    }

    public Politician getPolitician() {
        return politician;
    }

    public String getCounty(){
        return politician.getCounty();
    }

    public String getAffiliation() {
        return affiliation;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        if(votes > 0) {
            this.votes = votes;
        }
    }

    public void setPosition(int pos){
        if(pos > 0)
            position = pos;
    }

    public int getPosition(){
        return position;
    }

    public void addVotes(int votes) {
        if(votes > 0)
            this.votes += votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectionEntry that = (ElectionEntry) o;
        return Objects.equals(politician, that.politician);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(politician);
    }
}

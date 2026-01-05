package electionInfoManager.model.election;

import electionInfoManager.utility.Utilities;
import java.time.LocalDate;
import java.util.Objects;

public class Politician{
    public static final String[] COUNTIES = {
            "Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork",
            "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway",
            "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick",
            "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly",
            "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford",
            "Westmeath", "Wexford", "Wicklow"
    };
    public final static String[] ELECTION_PARTIES = {"Independent", "Fianna Fail", "Fianna Gael", "Sinn Fein", "Labour Party", "People Before Profit", "Aontu", "Green Party", "Social Democrats", "Independent Ireland"};
    public final static String[] FIELDS = {"Name", "Affiliation", "County", "PhotoURL", "DOB"};
    private String name, affiliation, county, photoURL;
    private LocalDate DOB;

    public Politician(String name, String affiliation, String county, String photoURL, LocalDate DOB) {
        if(name != null && !name.isEmpty()) {
            if (name.length() > 35)
                this.name = name.substring(0, 35);
            else
                this.name = name;
        }else this.name = "INVALID";

        if(affiliation != null && !affiliation.isEmpty())
            for(String e : ELECTION_PARTIES)
                if(e.equals(affiliation)) {
                    this.affiliation = e;
                    break;
                }
        if(this.affiliation == null)
            this.affiliation = "INVALID";

        if(county != null && !county.isEmpty()) {
            for(String c : COUNTIES)
                if(county.equalsIgnoreCase(c)) {
                    this.county = c;
                    break;
                }
            if(this.county == null)
                this.county = "INVALID";
        }

        if(Utilities.isWebsiteURL(photoURL))
            this.photoURL = photoURL;
        else
            this.photoURL = "INVALID";

        LocalDate now = LocalDate.now();
        if(DOB != null && DOB.getYear() > now.getYear() - 100)
            this.DOB = DOB;
        else
            this.DOB = now;
    }

    public String getName() {
        return name;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getCounty() {
        return county;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setName(String name) {
        if(name != null && !name.isEmpty()) {
            if (name.length() > 35)
                this.name = name.substring(0, 35);
            else
                this.name = name;
        }
    }

    public void setAffiliation(String affiliation) {
        if(affiliation != null && !affiliation.isEmpty()) {
            if(affiliation.length() > 50)
                this.affiliation = affiliation.substring(0, 50);
            else
                this.affiliation = affiliation;
        }
    }

    public void setCounty(String county) {
        if(county != null && !county.isEmpty()) {
            for(String c : COUNTIES)
                if(county.equalsIgnoreCase(c)) {
                    this.county = c;
                    break;
                }
        }
    }

    public void setPhotoURL(String photoURL) {
        if(Utilities.isWebsiteURL(photoURL))
            this.photoURL = photoURL;
    }

    public void setDOB(LocalDate DOB) {
        LocalDate now = LocalDate.now();
        if(DOB != null && DOB.getYear() > now.getYear() - 100)
            this.DOB = DOB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Politician that = (Politician) o;
        return Objects.equals(name, that.name) && Objects.equals(county, that.county);
    }

    @Override
    public int hashCode(){
        int hashCode = name != null ? name.hashCode() : 0;
        hashCode += county != null ? county.hashCode() : 0;
        return hashCode;
    }

    @Override
    public String toString(){
        return "Name: " + name + "\t" +
                " Affiliation: " + affiliation + "\t" +
                " County: " + county + "\t" +
                " DOB: " + DOB + "\t" +
                " PhotoURL: " + photoURL + "\n";
    }
}

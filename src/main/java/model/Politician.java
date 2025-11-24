package model;

import utility.Utilities;
import java.time.LocalDate;

public class Politician {
    public static final String[] COUNTIES = {
            "Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork",
            "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway",
            "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick",
            "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly",
            "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford",
            "Westmeath", "Wexford", "Wicklow"
    };

    private String name, affiliation, county, photoURL;
    private LocalDate DOB;

    public Politician(String name, String affiliation, String county, String photoURL, LocalDate DOB) {
        if(name != null && !name.isEmpty()) {
            if (name.length() > 35)
                this.name = name.substring(0, 35);
            else
                this.name = name;
        }else this.name = "INVALID";

        if(affiliation != null && !affiliation.isEmpty()) {
            if(affiliation.length() > 50)
                this.affiliation = affiliation.substring(0, 50);
            else
                this.affiliation = affiliation;
        }else
            this.affiliation = "INVALID";

        if(county != null && !county.isEmpty()) {
            for(String c : COUNTIES)
                if(county.equals(c)) {
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
                if(county.equals(c)) {
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
}

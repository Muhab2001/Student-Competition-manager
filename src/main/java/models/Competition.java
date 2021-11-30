package models;

import java.util.ArrayList;
import java.util.Date;

public class Competition {

    public Date dueDate;
    public boolean isOpen = true;
    public String name;
    public int teamSize;
    public String websiteLink;
    public ArrayList<Team> teams;
    public int id;

    public Competition(int id) {
        this.id = id;
    }

    public Competition(int id, Date dueDate, String name, int teamSize, String websiteLink, ArrayList<Team> teams) {
        this.id = id;
        this.dueDate = dueDate;
        this.teamSize = teamSize;
        this.teams = teams;
        this.websiteLink = websiteLink;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "isOpen=" + isOpen +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

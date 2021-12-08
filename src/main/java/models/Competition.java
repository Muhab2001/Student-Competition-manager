package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Competition implements Comparable<Competition> {


    public String dueDate;
    public boolean isOpen = true;
    public String name;
    public int teamSize;
    public String websiteLink;
    public ArrayList<Team> teams;
    public int index;

    public Competition(String dueDate, String name, int teamSize, String websiteLink, ArrayList<Team> teams, int index) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(dueDate.strip(), formatter);
        this.isOpen = LocalDate.now().compareTo(date) >= 0;
        this.dueDate = dueDate;
        this.teamSize  =teamSize;
        this.teams = teams;
        this.websiteLink = websiteLink;
        this.name = name;
        this.index = index;
    }

    public Competition(String dueDate, String name, int teamSize, String websiteLink, int index) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(dueDate.strip(), formatter);
        this.isOpen = LocalDate.now().compareTo(date) >= 0;
        this.dueDate = dueDate;
        this.teamSize  =teamSize;
        this.teams = new ArrayList<>();
        this.websiteLink = websiteLink;
        this.name = name;
        this.index = index;

    }

    public Team getTeam(int index){
        return null;
    }

    // this method will be used to compare teams before and after a competition edit
    @Override
    public int compareTo(Competition o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Competition{" +
                "dueDate=" + dueDate +
                "\n, isOpen=" + isOpen +
                "\n, name='" + name + '\'' +
                "\n, teamSize=" + teamSize +
                "\n, websiteLink='" + websiteLink + '\'' +
                "\n, teams=" + teams.toString() +
                "\n, index=" + index +
                '}';
    }
}

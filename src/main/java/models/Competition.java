package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Competition {

    public Date dueDate;
    public boolean isOpen = true;
    public String name;
    public int teamSize;
    public String websiteLink;
    public ArrayList<Team> teams;

    public Competition(Date dueDate, String name, int teamSize, String websiteLink, ArrayList<Team> teams) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyy");
        LocalDate date = LocalDate.parse(dueDate.toString(), formatter);
        this.isOpen = LocalDate.now().compareTo(date) > 0;this.dueDate = dueDate;
        this.teamSize  =teamSize;
        this.teams = teams;
        this.websiteLink = websiteLink;
        this.name = name;
    }

    public Team getTeam(int index){
        return null;
    }
}

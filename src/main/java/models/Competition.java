package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * class to hold uniform competitions data
 */
public class Competition implements Comparable<Competition>, Cloneable {


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
        this.isOpen = (LocalDate.now()).compareTo(date) <= 0;
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
        this.isOpen = (LocalDate.now()).compareTo(date) <= 0;
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
       boolean condition1 = name.compareTo(o.name) == 0;
       boolean condition2 = dueDate.compareTo(o.dueDate) == 0;
       boolean condition3 = teamSize == o.teamSize;
       boolean condition4 = Objects.equals(websiteLink, o.websiteLink);
       boolean condition5 = index == o.index;
       boolean condition6 = true;
       // checking equal teams
        if(condition3){
            for (int i = 0; i < teams.size(); i++) {
                if(!teams.get(i).equals(o.teams.get(i)))
                    return -1;
            }
        }
        // checking equal info
       if(condition1 && condition2 && condition3 && condition4 && condition5)
           return 1;

       return -1;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "dueDate=" + dueDate +
                "\n, isOpen=" + isOpen +
                "\n, name='" + name + '\'' +
                "\n, teamSize=" + teamSize +
                "\n, websiteLink='" + websiteLink + '\'' +
                "\n, teams=\n" + teams.toString() +
                "\n\n, index=" + index +
                '}';
    }

    @Override
    public Competition clone() {
        Competition clone = new Competition(this.dueDate, this.name, this.teamSize, this.websiteLink, this.index);
        for(Team team: this.teams){
            clone.teams.add(team.clone());
        }

        return clone;
    }
}

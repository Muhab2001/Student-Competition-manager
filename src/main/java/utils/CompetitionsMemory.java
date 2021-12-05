package utils;

import models.Competition;
import org.apache.bcel.generic.RETURN;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CompetitionsMemory {

    public static CompetitionsMemory INSTANCE = new CompetitionsMemory();

    private CompetitionsMemory(){}

    public ArrayList<Competition> competitions = new ArrayList<>();

    public ArrayList<Competition> getCompetitions() {
        return this.competitions;
    }

    public Competition getCompetition(int competitionId) {
        return competitions.get(competitionId);
    }

    public void addCompetition(Competition competition) {
        competitions.add(competition);
    }

    public void editCompetition(int competitionId, String name, String websiteLink, int teamSize, String dueDate) {
        Competition currentCompetition = competitions.get(competitionId);
        currentCompetition.name = name;
        currentCompetition.websiteLink = websiteLink;
        currentCompetition.teamSize = teamSize;
        currentCompetition.dueDate = dueDate;
    }

    public void editCompetitionByID(int competitionId, Competition newCompetitionState) {
        this.competitions.set(competitionId, newCompetitionState);
    }

    public void deleteCompetition(int competitionId) {
        Competition targetCompetition = getCompetition(competitionId);
        this.competitions.remove(targetCompetition);
    }

    @Override
    public String toString() {
        return competitions.toString();
    }
}
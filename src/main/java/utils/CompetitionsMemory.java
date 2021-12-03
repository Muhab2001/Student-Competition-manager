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

    public void editCompetition(int competitionId, Competition newCompetitionState) {
        Competition targetCompetition = getCompetition(competitionId);
        int targetIndex = this.competitions.indexOf(targetCompetition);

        this.competitions.remove(targetIndex);
        this.competitions.add(targetIndex, newCompetitionState);
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
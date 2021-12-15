package utils;

import models.Competition;
import models.User;

import pages.CompetitionController;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * a class to store and mutate existing competitions via interfacing with controllers and `ExcelStorage`
 */
public class CompetitionsMemory {

    public static CompetitionsMemory INSTANCE = new CompetitionsMemory();

    public static User CURRENT_USER;

    private CompetitionsMemory(){}

    public static ArrayList<Competition> competitions = new ArrayList<>();

    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    public static Competition getCompetition(int competitionId) {
        return competitions.get(competitionId);
    }

    public void addCompetition(Competition competition) {
        competitions.add(competition);
    }



    public static void editCompetition(Competition competition) {
        competitions.set(competition.index, competition);
    }

    public void deleteCompetition(int competitionId) {
        Competition targetCompetition = getCompetition(competitionId);
        competitions.remove(targetCompetition);
    }

    @Override
    public String toString() {
        return competitions.toString();
    }
}
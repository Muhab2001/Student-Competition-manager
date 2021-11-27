package utils;

import models.Competition;

import java.util.ArrayList;

public class CompetitionsMemory {

    ArrayList<Competition> competitions;
    public static CompetitionsMemory INSTANCE = new CompetitionsMemory();

    private CompetitionsMemory(){
        competitions = ExcelStorage.getAllCompetitions();
    }

    public void addComeptition(Competition competition){

    }

    public void deleteCompetition(int competitionId){

    }

    public void editCompetition(int competitionId, Competition newState){

    }

    public Competition getCompetition(int competitionId){
        return null;
    }

}

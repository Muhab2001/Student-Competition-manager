import models.Competition;

import java.util.ArrayList;
import java.util.NoSuchElementException;

class CompetitionMemory {
    private ArrayList<Competition> competitions = new ArrayList<>();

    public ArrayList<Competition> getCompetitions() {
        return this.competitions;
    }

    public Competition getCompetition(int competitionId) {
        Competition targetCompetition = null;
        int index = 0;
        boolean isFound = false;
        while (!isFound && index < this.competitions.size()) {
            Competition currentCompetition = this.competitions.get(index);
            if (currentCompetition.id == competitionId) {
                isFound = true;
                targetCompetition = currentCompetition;
            } else index++;
        }

        if (targetCompetition == null)
            throw new NoSuchElementException("The competition with id " + competitionId + " does not exist");
        return targetCompetition;
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
        int targetIndex = this.competitions.indexOf(targetCompetition);
        this.competitions.remove(targetIndex);
    }
}
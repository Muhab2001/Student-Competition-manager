package utils;

import models.Competition;
import models.Student;
import models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExcelStorageTest {

    private Competition competition1;
    private Competition competition2;

    @BeforeEach
    void setUp() {
        String[] names1 = {"Muhab", "Ahmed", "Basel", "Mubarak", "Abdulaziz", ""};
        String[] names2 = {"Muhab Abubaker", "Ahmed Badghaish", "Basel Al nassr", "Mubarak Badgaish", "Abdulaziz bin Agian", "Kermit the frog"};
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        Team team1 = new Team(0, 6);
        Team team2 = new Team(1, 6);
        team1.rank = 1;
        for (int i = 0; i < 5; i++) {
            team1.students.add(new Student(i, "20194557" + i, names1[i], "SWE/CS"));

        }
        team1.students.add(new Student(5));
        for(int i = 0; i < 6; i++){
            team2.students.add(new Student(i, "20194557" + i, names2[i], "SWE/CS"));
        }

        ArrayList<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        competition1 = new Competition(LocalDate.now().format(dateTimeFormatter), "testing competition #1", 6, "http://cheeks", teams, 0);
        competition2 = new Competition(LocalDate.now().format(dateTimeFormatter), "testing competition #2", 6, "http://cheeks", teams, 1);
        ArrayList<Competition> competitions = new ArrayList<>();
        competitions.add(competition1);
        competitions.add(competition2);

        ExcelStorage.saveChanges(competitions);
    }

    @Test
    void getAllCompetitions() {
        ArrayList<Competition> comps = ExcelStorage.getAllCompetitions();

        assertAll(() -> assertEquals(comps.get(0).toString(), competition1.toString()),
                () -> assertEquals(comps.get(1).toString(), competition2.toString()));
    }


}
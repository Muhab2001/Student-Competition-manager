package models;

import java.util.ArrayList;

public class Team {
    int id;
    ArrayList<Student> students;

    public Team(int id, ArrayList<Student> students){
        this.id = id;
        this.students = students;
    }
}

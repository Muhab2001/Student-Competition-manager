package models;

import java.util.ArrayList;

public class Team implements Comparable<Team>{
    int index;
    ArrayList<Student> students;
    int teamSize;
    public Team(int id, ArrayList<Student> students, int teamSize){
        this.index = id;
        this.students = students;
        this.teamSize = teamSize;
    }

    // this method will be used to compare teams before and after a competition edit
    @Override
    public int compareTo(Team o) {
        if(o.students.size() != students.size()){
            return -1; // unequal number
        }
        for(int i = 0; i < teamSize; i++){
            if(o.students.get(i).compareTo(this.students.get(i)) != 0){
                return 1; // different information
            }
        }

        return 0; // equal information
    }
}

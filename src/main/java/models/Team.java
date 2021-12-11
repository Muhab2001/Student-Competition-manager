package models;

import java.util.ArrayList;

public class Team implements Comparable<Team>, Cloneable{
    public int index;
    public ArrayList<Student> students;
    public int teamSize;
    public int rank = -1;

    public Team(int id, ArrayList<Student> students, int teamSize){
        this.index = id;
        this.students = students;
        this.teamSize = teamSize;
    }

    public Team(int id, int teamSize){
        this.index = id;
        this.teamSize = teamSize;
        this.students = new ArrayList<>(teamSize);
    }

    // this method will be used to compare teams before and after a competition edit
    @Override
    public int compareTo(Team o) {
        if(o.students.size() != students.size() || this.index != o.index || this.teamSize != o.teamSize){
            return -1; // unequal number
        }
        for(int i = 0; i < teamSize; i++){
            if(o.students.get(i).compareTo(this.students.get(i)) != 0){
                return 1; // different information
            }
        }

        return 0; // equal information
    }

    @Override
    public String toString() {
        return "Team{" +
                "index=" + index +
                ", students=\n" + students.toString() +
                "\n, teamSize=" + teamSize +
                "rank="+ rank +
                '}' + "\n-------------------------------------------\n";
    }

    @Override
    public Team clone() {

            Team clone = new Team(index, teamSize);
            for(Student student: this.students){
                clone.students.add(new Student(student.index, student.id, student.name, student.major));
            }
            clone.rank = this.rank;
            return clone;

    }
}

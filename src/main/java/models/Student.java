package models;

public class Student implements Comparable<Student>{

    public String name;
    public String id;
    public int index;

    public Student(String name, String id){
        this.name = name;
        this.id = id;
    }

    // this method will be used to compare teams before and after a competition edit
    @Override
    public int compareTo(Student o) {
        return name.compareTo(o.name);
    }
}

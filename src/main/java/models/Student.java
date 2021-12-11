package models;

public class Student implements Comparable<Student>{

    public String name;
    public String major;
    public Integer index;
    public String id;


    public Student(int index, String id, String name, String major){
        this.name = name;
        this.id = id;
        this.index = index;
        this.major = major;
    }

    public Student(int index){
        this.name = "";
        this.id = "";
        this.index = index;
        this.major = "";
    }

    // this method will be used to compare teams before and after a competition edit
    @Override
    public int compareTo(Student o) {
        boolean condition1 = name.equals(o.name);
        boolean condition2 = major.equals(o.major);
        boolean condition3 = index.equals(o.index);
        boolean condition4 = id.equals(o.id);

        if(condition1 && condition2 && condition3 && condition4)
            return 1;
        return -1;

    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", major='" + major + '\'' +
                ", index=" + index +
                ", id=" + id +
                '}' + "\n";
    }
}

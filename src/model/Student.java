package model;

public class Student extends Member {

    public Student(int id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxIssueLimit() {
        return 2;
    }

    @Override
    public int getMaxIssueDays() {
        return 3;
    }
}
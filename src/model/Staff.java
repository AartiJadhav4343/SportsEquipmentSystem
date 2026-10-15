package model;

public class Staff extends Member {

    public Staff(int id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxIssueLimit() {
        return 5;
    }

    @Override
    public int getMaxIssueDays() {
        return 7;
    }
}
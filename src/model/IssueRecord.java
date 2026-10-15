package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IssueRecord {
    private static int counter = 1;

    private int recordId;
    private Member member;
    private Equipment equipment;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public IssueRecord(Member member, Equipment equipment) {
        this.recordId = counter++;
        this.member = member;
        this.equipment = equipment;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(member.getMaxIssueDays());
    }

    public int getRecordId() { return recordId; }
    public Member getMember() { return member; }
    public Equipment getEquipment() { return equipment; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return returnDate != null; }

    public void markReturned() {
        this.returnDate = LocalDate.now();
    }

    public long getLateDays() {
        LocalDate end = (returnDate != null) ? returnDate : LocalDate.now();
        long days = ChronoUnit.DAYS.between(dueDate, end);
        return Math.max(days, 0);
    }

    @Override
    public String toString() {
        return "Record#" + recordId + " | " + member.getName() + " | " +
               equipment.getName() + " | Due: " + dueDate +
               (isReturned() ? " | Returned: " + returnDate : " | NOT RETURNED");
    }
}
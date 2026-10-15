package service;

import exception.*;
import model.*;

import java.time.LocalDate;
import java.util.*;

public class IssueService {
    public static final int FINE_PER_DAY = 10; // Rs. 10 per late day

    private final EquipmentService equipmentService;
    private final MemberService memberService;
    private final List<IssueRecord> records = new ArrayList<>();

    public IssueService(EquipmentService equipmentService, MemberService memberService) {
        this.equipmentService = equipmentService;
        this.memberService = memberService;
    }

    public synchronized IssueRecord issueEquipment(int memberId, int equipmentId)
            throws MemberNotFoundException, EquipmentNotFoundException,
                   EquipmentNotAvailableException, IssueLimitExceededException {

        Member member = memberService.getMember(memberId);
        Equipment equipment = equipmentService.getEquipment(equipmentId);

        if (!equipment.isAvailable()) {
            throw new EquipmentNotAvailableException(
                    equipment.getName() + " is currently not available.");
        }

        int activeCount = countActiveIssues(memberId);
        if (activeCount >= member.getMaxIssueLimit()) {
            throw new IssueLimitExceededException(
                    member.getName() + " has reached the limit of "
                    + member.getMaxIssueLimit() + " items.");
        }

        equipment.decreaseAvailable();
        IssueRecord record = new IssueRecord(member, equipment);
        records.add(record);
        return record;
    }

    // returns fine amount
    public synchronized long returnEquipment(int recordId) {
        for (IssueRecord r : records) {
            if (r.getRecordId() == recordId) {
                if (r.isReturned()) {
                    throw new IllegalStateException("Record " + recordId + " is already returned.");
                }
                r.markReturned();
                r.getEquipment().increaseAvailable();
                return r.getLateDays() * FINE_PER_DAY;
            }
        }
        throw new IllegalArgumentException("Record ID " + recordId + " not found.");
    }

    private int countActiveIssues(int memberId) {
        int count = 0;
        for (IssueRecord r : records) {
            if (r.getMember().getMemberId() == memberId && !r.isReturned()) {
                count++;
            }
        }
        return count;
    }

    public synchronized List<IssueRecord> getIssuedRecords() {
        List<IssueRecord> result = new ArrayList<>();
        for (IssueRecord r : records) {
            if (!r.isReturned()) {
                result.add(r);
            }
        }
        return result;
    }

    public synchronized List<IssueRecord> getMemberHistory(int memberId) {
        List<IssueRecord> result = new ArrayList<>();
        for (IssueRecord r : records) {
            if (r.getMember().getMemberId() == memberId) {
                result.add(r);
            }
        }
        return result;
    }

    public synchronized List<IssueRecord> getOverdueRecords() {
        List<IssueRecord> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (IssueRecord r : records) {
            if (!r.isReturned() && today.isAfter(r.getDueDate())) {
                result.add(r);
            }
        }
        return result;
    }
}
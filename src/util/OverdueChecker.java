package util;

import model.IssueRecord;
import service.IssueService;

import java.util.List;

public class OverdueChecker extends Thread {
    private final IssueService issueService;
    private volatile boolean running = true;

    public OverdueChecker(IssueService issueService) {
        this.issueService = issueService;
        setDaemon(true); // program band hone par ye thread bhi band ho jayega
    }

    public void stopChecker() {
        running = false;
        interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(30000); // har 30 second me check
            } catch (InterruptedException e) {
                break;
            }
            List<IssueRecord> overdue = issueService.getOverdueRecords();
            if (!overdue.isEmpty()) {
                System.out.println("\n[ALERT] Overdue items: " + overdue.size());
                for (IssueRecord r : overdue) {
                    System.out.println("   " + r + " | Late days: " + r.getLateDays());
                }
            }
        }
    }
}
package service;

import exception.MemberNotFoundException;
import model.*;

import java.util.*;

public class MemberService {
    private final Map<Integer, Member> memberMap = new TreeMap<>();

    public void registerMember(Member m) {
        if (memberMap.containsKey(m.getMemberId())) {
            throw new IllegalArgumentException("Member ID " + m.getMemberId() + " already exists.");
        }
        memberMap.put(m.getMemberId(), m);
    }

    public Member getMember(int id) throws MemberNotFoundException {
        Member m = memberMap.get(id);
        if (m == null) {
            throw new MemberNotFoundException("Member ID " + id + " not found.");
        }
        return m;
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(memberMap.values());
    }

    public List<Member> getStudents() {
        List<Member> result = new ArrayList<>();
        for (Member m : memberMap.values()) {
            if (m instanceof Student) {
                result.add(m);
            }
        }
        return result;
    }

    public List<Member> getStaff() {
        List<Member> result = new ArrayList<>();
        for (Member m : memberMap.values()) {
            if (m instanceof Staff) {
                result.add(m);
            }
        }
        return result;
    }
}
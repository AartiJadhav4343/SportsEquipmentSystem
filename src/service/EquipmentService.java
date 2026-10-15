package service;

import exception.EquipmentNotFoundException;
import model.Equipment;

import java.util.*;

public class EquipmentService {
    private final Map<Integer, Equipment> equipmentMap = new HashMap<>();

    public void addEquipment(Equipment e) {
        if (equipmentMap.containsKey(e.getId())) {
            throw new IllegalArgumentException("Equipment ID " + e.getId() + " already exists.");
        }
        equipmentMap.put(e.getId(), e);
    }

    public void removeEquipment(int id) throws EquipmentNotFoundException {
        if (equipmentMap.remove(id) == null) {
            throw new EquipmentNotFoundException("Equipment ID " + id + " not found.");
        }
    }

    public Equipment getEquipment(int id) throws EquipmentNotFoundException {
        Equipment e = equipmentMap.get(id);
        if (e == null) {
            throw new EquipmentNotFoundException("Equipment ID " + id + " not found.");
        }
        return e;
    }

    public List<Equipment> searchByName(String keyword) {
        List<Equipment> result = new ArrayList<>();
        for (Equipment e : equipmentMap.values()) {
            if (e.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Equipment> getAllEquipment() {
        return new ArrayList<>(equipmentMap.values());
    }

    public List<Equipment> getAvailableEquipment() {
        List<Equipment> result = new ArrayList<>();
        for (Equipment e : equipmentMap.values()) {
            if (e.isAvailable()) {
                result.add(e);
            }
        }
        return result;
    }
}
package com.example.stage.service;

import com.example.stage.dao.EquipmentRepository;
import com.example.stage.dao.FeederHistoryRepository;
import com.example.stage.entity.Equipment;
import com.example.stage.entity.FeederHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository repository;


    public Equipment save(Equipment equipment) {
        return repository.save(equipment);
    }

    public List<Equipment> findAll() {
        return repository.findAll();
    }



    public List<Object[]> getEquipmentAndTotalRejection() {
        return repository.findEquipmentAndTotalRejection();
    }

    public List<Object[]> getTop3EquipmentAndTotalRejection() {
        return repository.findTop3EquipmentAndTotalRejection().stream()
                .limit(3) // Limiter à 3 résultats
                .toList();
    }

    public List<Map<String, Object>> getTop3EquipmentAndTotalRejection1() {
        List<Object[]> results = repository.findTop3EquipmentByRejection();

        return results.stream()
                .limit(3) // Limit to top 3
                .map(result -> {
                    String equipmentName = (String) result[0];
                    Long totalComponentsFed = ((Number) result[1]).longValue();
                    Long totalRejection = ((Number) result[2]).longValue();

                    double rejectionPercentage = totalComponentsFed > 0 ?
                            ((double) totalRejection / totalComponentsFed * 100) : 0.0;

                    Map<String, Object> equipmentData = new HashMap<>();
                    equipmentData.put("equipmentName", equipmentName);
                    equipmentData.put("totalComponentsFed", totalComponentsFed);
                    equipmentData.put("totalRejection", totalRejection);
                    equipmentData.put("rejectionPercentage", rejectionPercentage);

                    return equipmentData;
                })
                .collect(Collectors.toList());
    }


}

package com.example.stage.dao;

import com.example.stage.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    @Query("SELECT e.equipmentName, SUM(f.pickupMiss +  f.recognitionError) AS totalRejection FROM Equipment e JOIN FeederHistory fh ON e.equipmentId = fh.equipment.equipmentId JOIN FeederCounts f ON fh.feederId = f.feederId GROUP BY e.equipmentName")
    List<Object[]> findEquipmentAndTotalRejection();

    @Query("SELECT e.equipmentName, SUM(f.pickupMiss + f.recognitionError) AS totalRejection " +
            "FROM Equipment e " +
            "JOIN FeederHistory fh ON e.equipmentId = fh.equipment.equipmentId " +
            "JOIN FeederCounts f ON fh.feederId = f.feederId " +
            "GROUP BY e.equipmentName " +
            "ORDER BY totalRejection DESC")
    List<Object[]> findTop3EquipmentAndTotalRejection();


    @Query("SELECT e.equipmentName, SUM(f.totalComponentsFed) AS totalComponentsFed, SUM(f.pickupMiss + f.recognitionError) AS totalRejection " +
            "FROM Equipment e " +
            "JOIN FeederHistory fh ON e.equipmentId = fh.equipment.equipmentId " +
            "JOIN FeederCounts f ON fh.feederId = f.feederId " +
            "GROUP BY e.equipmentName " +
            "ORDER BY (SUM(f.pickupMiss + f.recognitionError) / SUM(f.totalComponentsFed) * 100) DESC")
    List<Object[]> findTop3EquipmentByRejection();

}

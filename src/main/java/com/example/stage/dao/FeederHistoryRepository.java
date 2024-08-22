package com.example.stage.dao;

import com.example.stage.entity.FeederHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeederHistoryRepository extends JpaRepository<FeederHistory, Long> {

    @Query(value = "SELECT fh.expected_pn AS expectedPn, " +
            "(SUM(f.pickup_miss + f.recognition_error) / SUM(f.total_components_fed) * 100) AS rejectionRate " +
            "FROM equipment e " +
            "JOIN feeder_history fh ON e.equipment_id = fh.equipment_id " +
            "JOIN feeder_counts f ON fh.feeder_id = f.feeder_id " +
            "GROUP BY fh.expected_pn " +
            "ORDER BY rejectionRate DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3FeederHistoryAndCountsByRejection();


    @Query("SELECT fh, p.prix " +
            "FROM FeederHistory fh " +
            "LEFT JOIN Parts p ON fh.expectedPn = p.expectedPn")
    List<Object[]> findFeederHistoryWithPrice();


    @Query("SELECT fh, p.prix " +
            "FROM FeederHistory fh " +
            "JOIN Parts p ON fh.expectedPn = p.expectedPn " +
            "WHERE fh.overrideReason IS NOT NULL " +
            "ORDER BY p.prix DESC")
    List<Object[]> findTop3RejectedComponentsByPrice();
}



package com.example.stage.dao;

import com.example.stage.entity.FeederCounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface FeederCountsRepository extends JpaRepository<FeederCounts, Long> {
    Optional<FeederCounts> findById(Long feederId);

    @Query("SELECT SUM(fc.totalComponentsFed) FROM FeederCounts fc")
    Long sumTotalComponentsFed();

    @Query("select SUM(fc.placementCount) from FeederCounts fc")
    Long sumPlacementCount();
    @Query("SELECT COALESCE(SUM(fc.totalPickupMiss), 0) FROM FeederCounts fc")
    Long sumTotalPickupMiss();

    @Query("SELECT COALESCE(SUM(fc.totalRecognitionError), 0) FROM FeederCounts fc")
    Long sumTotalRecognitionError();

    @Query("SELECT fc FROM FeederCounts fc ORDER BY (fc.pickupMiss + fc.recognitionError) DESC")

    List<FeederCounts> findTop3ByRejections();

    List<FeederCounts> findByUpdateTimeBetween(Long startTime, Long endTime);




}





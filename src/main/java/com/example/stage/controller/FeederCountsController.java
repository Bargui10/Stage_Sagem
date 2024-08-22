package com.example.stage.controller;

import com.example.stage.dao.FeederCountsRepository;
import com.example.stage.entity.FeederCounts;
import com.example.stage.service.FeederCountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/feeder-counts")
public class FeederCountsController {

    private static final Logger logger = LoggerFactory.getLogger(FeederCountsController.class);

    @Autowired
    private FeederCountsService feederCountsService;
    private FeederCountsRepository feederCountsRepository;


    @GetMapping("/performance-rate")
    public ResponseEntity<String> getPerformanceRate() {
        String performanceRate = feederCountsService.calculatePerformanceRate();
        return ResponseEntity.ok(performanceRate);
    }

    @GetMapping("/performance2")
    public ResponseEntity<Double> getPerformance2() {
        double performance2 = feederCountsService.calculatePerformance2();
        return ResponseEntity.ok(performance2);
    }

    @GetMapping("/total-errors")
    public ResponseEntity<Long> getTotalErrors() {
        Long totalErrors = feederCountsService.sumTotalErrors();
        return ResponseEntity.ok(totalErrors);
    }

    @GetMapping("/pick-up-miss")
    public ResponseEntity<Long> getPickUpMiss() {
        Long pickUpMiss = feederCountsService.sumTotalPickUpMiss();
        return ResponseEntity.ok(pickUpMiss);
    }

    @GetMapping("/recognition")
    public ResponseEntity<Long> getRecognition() {
        Long recognition = feederCountsService.sumTotalRecognition();
        return ResponseEntity.ok(recognition);
    }

    @GetMapping("/formatted-update-times")
    public List<String> getFormattedUpdateTimes() {
        return feederCountsService.getFormattedUpdateTimes();
    }

    @GetMapping("/top3-rejections")
    public List<FeederCounts> getTop3EquipmentsWithRejections() {
        return feederCountsService.getTop3EquipmentsWithRejections();
    }

    @GetMapping("/hourly-performance-rates")
    public ResponseEntity<Map<String, Double>> getHourlyPerformanceRates() {
        Map<String, Double> performanceRates = feederCountsService.getHourlyPerformanceRates();
        return ResponseEntity.ok(performanceRates);
    }

    @GetMapping("/performance-by-shift-hourly")
    public ResponseEntity<Map<String, Double>> getPerformanceByShiftHourly() {
        Map<String, Double> performanceRates = feederCountsService.getPerformanceByShiftHourly();
        return ResponseEntity.ok(performanceRates);
    }

    @GetMapping("/performance-by-hour")
    public ResponseEntity<Map<String, Map<String, Map<String, Double>>>> getPerformanceRatesByHour() {
        Map<String, Map<String, Map<String, Double>>> performanceRates = feederCountsService.getPerformanceRatesByHour();
        return ResponseEntity.ok(performanceRates);
    }

    @GetMapping("/performance-last-day")
    public ResponseEntity<Map<String, Map<String, Map<String, Double>>>> getPerformanceRatesByHour2() {
        Map<String, Map<String, Map<String, Double>>> performanceRates = feederCountsService.getPerformanceRatesByHour2();
        return ResponseEntity.ok(performanceRates);
    }
    @GetMapping("/by-hour-and-shift")
    public ResponseEntity<Map<String, Map<String, Double>>> getPerformanceByHourAndShift() {
        Map<String, Map<String, Double>> performanceRates = feederCountsService.getPerformanceByHourAndShift();
        return ResponseEntity.ok(performanceRates);
    }

    @GetMapping("/current")
    public ResponseEntity<String> getCurrentDateTime() {
        String currentDateTime = feederCountsService.getCurrentDateTime();
        return ResponseEntity.ok(currentDateTime);
    }


    @GetMapping("/performance-last-three-shifts")
    public ResponseEntity<Map<String, Map<String, Double>>> getPerformanceRatesLastThreeShifts() {
        try {
            Map<String, Map<String, Double>> performanceRates = feederCountsService.getPerformanceRatesLastThreeShifts();
            return ResponseEntity.ok(performanceRates);
        } catch (Exception e) {
            // Handle the exception and return an appropriate response
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/performance-previous-shift")
    public Map<String, Double> getPerformanceRateOfPreviousShift() {
        return feederCountsService.getPerformanceRateOfPreviousShift();
    }




    @GetMapping("/performance-current-shift-hourly")
    public ResponseEntity<Map<String, Double>> getPerformanceCurrentShiftHourly() {
        Map<String, Double> hourlyPerformance = feederCountsService.getHourlyPerformanceForCurrentShift();
        return ResponseEntity.ok(hourlyPerformance);
    }

}


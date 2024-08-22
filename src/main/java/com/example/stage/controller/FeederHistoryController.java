package com.example.stage.controller;

import com.example.stage.service.FeederHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/feeder-history")
public class FeederHistoryController {
    @Autowired
    private FeederHistoryService service;

    @GetMapping("/all")
    public List<Map<String, Object>> getFeederHistoryAndCounts() {
        return service.getFeederHistoryAndCounts();
    }

    @GetMapping("/top-3-rejection")
    public ResponseEntity<List<Map<String, Object>>> getTop3Rejections() {
        List<Map<String, Object>> topRejections = service.getFeederHistoryAndCounts();
        return ResponseEntity.ok(topRejections);
    }


    @GetMapping("/with-price")
    public List<com.example.stage.dto.FeederHistoryDTO> getFeederHistoriesWithPrice() {
        return service.getAllFeederHistoriesWithPrice();
    }


    @GetMapping("/top3-rejected-by-price")
    public List<com.example.stage.dto.FeederHistoryDTO> getTop3RejectedComponentsByPrice() {
        return service.getTop3RejectedComponentsByPrice();
    }

}

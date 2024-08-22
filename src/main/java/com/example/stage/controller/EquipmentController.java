package com.example.stage.controller;

import com.example.stage.entity.Equipment;
import com.example.stage.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService service;

    @PostMapping
    public Equipment createEquipment(@RequestBody Equipment equipment) {
        return service.save(equipment);
    }

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return service.findAll();
    }

    @GetMapping("/equipment-rejections")
    public List<Object[]> getEquipmentRejections() {

        return service.getEquipmentAndTotalRejection();
    }
    @GetMapping("/top3-equipment-rejections")
    public List<Object[]> getTop3EquipmentRejections() {

        return service.getTop3EquipmentAndTotalRejection();
    }

    @GetMapping("/top-3-rejection")
    public ResponseEntity<List<Map<String, Object>>> getTop3EquipmentAndTotalRejection() {
        List<Map<String, Object>> top3Equipment = service.getTop3EquipmentAndTotalRejection1();
        return ResponseEntity.ok(top3Equipment);
    }





}

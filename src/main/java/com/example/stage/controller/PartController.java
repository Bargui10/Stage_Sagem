package com.example.stage.controller;

import com.example.stage.entity.Parts;
import com.example.stage.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/parts")
public class PartController {

    @Autowired
    private PartService partService;

    @GetMapping("/all")
    public ResponseEntity<List<Parts>> getAllParts() {
        List<Parts> parts = partService.getAllParts();
        return ResponseEntity.ok(parts);
    }
}

package com.example.stage.service;

import com.example.stage.dao.PartRepository;
import com.example.stage.entity.Parts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.util.List;

@Service
public class PartService {

    @Autowired
    private PartRepository partRepository;

    public List<Parts> getAllParts() {
        return partRepository.findAll();
    }



}

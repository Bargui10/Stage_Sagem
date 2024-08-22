package com.example.stage.service;

import com.example.stage.dao.FeederHistoryRepository;
import com.example.stage.entity.FeederHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeederHistoryService {

    @Autowired
    private FeederHistoryRepository feederHistoryRepository;

    @Autowired
    private PartService partService;

    public List<Map<String, Object>> getFeederHistoryAndCounts() {
        List<Object[]> results = feederHistoryRepository.findTop3FeederHistoryAndCountsByRejection();

        return results.stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("expectedPn", result[0]);
                    map.put("rejectionRate", result[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }


    public List<com.example.stage.dto.FeederHistoryDTO> getAllFeederHistoriesWithPrice() {
        List<Object[]> results = feederHistoryRepository.findFeederHistoryWithPrice();
        return results.stream().map(result -> {
            com.example.stage.dto.FeederHistoryDTO dto = new com.example.stage.dto.FeederHistoryDTO();
            FeederHistory history = (FeederHistory) result[0];
            Double price = (Double) result[1];

            dto.setFeederHistoryId(history.getFeederHistoryId());
            dto.setExpectedPn(history.getExpectedPn());
            dto.setPrice(price);

            return dto;
        }).collect(Collectors.toList());
    }




    public List<com.example.stage.dto.FeederHistoryDTO> getTop3RejectedComponentsByPrice() {
        List<Object[]> results = feederHistoryRepository.findTop3RejectedComponentsByPrice();
        return results.stream()
                .map(result -> {
                    com.example.stage.dto.FeederHistoryDTO dto = new com.example.stage.dto.FeederHistoryDTO();
                    FeederHistory history = (FeederHistory) result[0];
                    Double price = (Double) result[1];

                    dto.setFeederHistoryId(history.getFeederHistoryId());
                    dto.setExpectedPn(history.getExpectedPn());
                    dto.setPrice(price);

                    return dto;
                })
                .limit(3) // Limiter aux top 3
                .collect(Collectors.toList());
    }

}

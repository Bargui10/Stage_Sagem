package com.example.stage.service;

import com.example.stage.dao.FeederCountsRepository;
import com.example.stage.entity.FeederCounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeederCountsService {

    @Autowired
    private FeederCountsRepository feederCountsRepository;

    public String calculatePerformanceRate() {
        Long totalComponentsFed = feederCountsRepository.sumTotalComponentsFed();
        Long sumPlacementCount = feederCountsRepository.sumPlacementCount();
        Long totalErrors = sumTotalErrors();

        if (totalComponentsFed == 0) {
            return "0%";
        }

        double performanceRate = (double) totalErrors / totalComponentsFed * 100;
        return String.format("%.2f%%", performanceRate);
    }

    public double calculatePerformance2() {
        Long totalComponentsFed = feederCountsRepository.sumTotalComponentsFed();
        Long sumPlacementCount = feederCountsRepository.sumPlacementCount();

        if (totalComponentsFed == 0) {
            return 0.0;
        }

        double performance2 = (double) sumPlacementCount / totalComponentsFed * 100;
        return 100 - performance2;
    }

    public Long sumTotalPickUpMiss() {
        return feederCountsRepository.sumTotalPickupMiss();
    }

    public Long sumTotalRecognition() {
        return feederCountsRepository.sumTotalRecognitionError();
    }

    public Long sumTotalErrors() {
        Long totalPickupMiss = feederCountsRepository.sumTotalPickupMiss();
        Long totalRecognitionError = feederCountsRepository.sumTotalRecognitionError();
        return totalPickupMiss + totalRecognitionError;
    }

    public List<String> getFormattedUpdateTimes() {
        List<FeederCounts> feederCountsList = feederCountsRepository.findAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return feederCountsList.stream()
                .map(feederCount -> {
                    Long timestamp = feederCount.getUpdateTime();
                    if (timestamp != null) {
                        Date date = new Date(timestamp * 1000);
                        return dateFormat.format(date);
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public Map<String, Map<String, Map<String, Double>>> getPerformanceRatesByHour() {
        List<FeederCounts> feederCountsList = feederCountsRepository.findAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Map<String, Map<String, Double>>> hourlyPerformanceMap = new HashMap<>();

        for (FeederCounts feederCount : feederCountsList) {
            Long timestamp = feederCount.getUpdateTime();
            if (timestamp != null) {
                Date date = new Date(timestamp * 1000);
                String formattedDate = dateFormat.format(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String dayKey = formattedDate.substring(0, 10);

                hourlyPerformanceMap.putIfAbsent(dayKey, new HashMap<>());
                hourlyPerformanceMap.get(dayKey).putIfAbsent("06-14", new HashMap<>());
                hourlyPerformanceMap.get(dayKey).putIfAbsent("14-22", new HashMap<>());
                hourlyPerformanceMap.get(dayKey).putIfAbsent("22-06", new HashMap<>());

                double performanceRate = calculatePerformance2();

                String shift = getShiftForHour(hour);
                hourlyPerformanceMap.get(dayKey).get(shift).put(formattedDate, performanceRate);
            }
        }

        return hourlyPerformanceMap;
    }


    private String getShiftForHour(int hour) {
        if (hour >= 6 && hour < 14) return "06-14";
        if (hour >= 14 && hour < 22) return "14-22";
        return "22-06";
    }

    public List<FeederCounts> getTop3EquipmentsWithRejections() {
        return feederCountsRepository.findTop3ByRejections();
    }

    public Map<String, Double> getHourlyPerformanceRates() {
        List<FeederCounts> feederCountsList = feederCountsRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
        if (now.getHour() < 6) {
            startOfDay = startOfDay.minusDays(1);
        }

        LocalDateTime endOfDay = startOfDay.plusDays(1);
        long startTimestamp = startOfDay.toEpochSecond(ZoneOffset.UTC);
        long endTimestamp = endOfDay.toEpochSecond(ZoneOffset.UTC);

        Map<Integer, Long> hourlyCounts = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            hourlyCounts.put(i, 0L);
        }

        for (FeederCounts feederCount : feederCountsList) {
            long timestamp = feederCount.getUpdateTime();
            if (timestamp >= startTimestamp && timestamp <= endTimestamp) {
                LocalDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
                int hour = dateTime.getHour();
                hourlyCounts.put(hour, hourlyCounts.get(hour) + feederCount.getPlacementCount());
            }
        }

        Map<String, Double> performanceRates = new HashMap<>();
        for (Map.Entry<Integer, Long> entry : hourlyCounts.entrySet()) {
            int hour = entry.getKey();
            long count = entry.getValue();
            performanceRates.put(String.format("%02d:00", hour), (count / (double) (endTimestamp - startTimestamp)) * 100);
        }

        return performanceRates;
    }

    public Map<String, Double> getPerformanceByShiftHourly() {
        Map<String, Double> performanceByShift = new HashMap<>();

        int currentHour = LocalTime.now().getHour();
        String currentShift = getShiftForHour(currentHour);

        double performanceRate = calculatePerformanceRateForShift(currentShift);

        performanceByShift.put(currentShift, performanceRate);
        return performanceByShift;
    }

    private double calculatePerformanceRateForShift(String shift) {
        List<FeederCounts> feederCountsList = feederCountsRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfShift = now.withHour(getShiftStartHour(shift)).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfShift = startOfShift.plusHours(8); // 8-hour shift duration
        long startTimestamp = startOfShift.toEpochSecond(ZoneOffset.UTC);
        long endTimestamp = endOfShift.toEpochSecond(ZoneOffset.UTC);

        long totalPlacementCount = 0;
        long totalComponentsFed = 0;

        for (FeederCounts feederCount : feederCountsList) {
            long timestamp = feederCount.getUpdateTime();
            if (timestamp >= startTimestamp && timestamp <= endTimestamp) {
                totalPlacementCount += feederCount.getPlacementCount();
                totalComponentsFed += feederCount.getTotalComponentsFed();
            }
        }

        if (totalComponentsFed == 0) {
            return 0.0;
        }

        return 100 - (double) totalPlacementCount / totalComponentsFed * 100;
    }

    private int getShiftStartHour(String shift) {
        switch (shift) {
            case "06-14": return 6;
            case "14-22": return 14;
            case "22-06": return 22;
            default: return 0;
        }
    }

    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


    public Map<String, Map<String, Double>> getPerformanceByHourAndShift() {
        List<FeederCounts> feederCountsList = feederCountsRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        Map<String, Map<String, Double>> shiftPerformanceMap = new HashMap<>();
        shiftPerformanceMap.put("06-14", createEmptyHourlyMap(6, 14));
        shiftPerformanceMap.put("14-22", createEmptyHourlyMap(14, 22));
        shiftPerformanceMap.put("22-06", createEmptyHourlyMap(22, 24, 0, 6));

        Long totalComponentsFed = feederCountsRepository.sumTotalComponentsFed();
        Long sumPlacementCount = feederCountsRepository.sumPlacementCount();

        if (totalComponentsFed == 0) {
            // If no components are fed, return empty maps
            return shiftPerformanceMap;
        }

        // Process each feederCount entry
        for (FeederCounts feederCount : feederCountsList) {
            Long timestamp = feederCount.getUpdateTime();
            if (timestamp != null) {
                LocalDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.UTC).toLocalDateTime();
                if (dateTime.isAfter(startOfDay.minusDays(1)) && dateTime.isBefore(endOfDay)) {
                    int hour = dateTime.getHour();
                    String shift = getShift(dateTime);
                    Map<String, Double> performanceMap = shiftPerformanceMap.get(shift);

                    if (performanceMap != null) {
                        double hourPerformanceRate = Double.parseDouble(calculatePerformanceRate());
                        String hourKey = (hour < 10 ? "0" : "") + hour + ":00";
                        performanceMap.merge(hourKey, hourPerformanceRate, Double::sum);
                    }
                }
            }
        }

        return shiftPerformanceMap;
    }



    private Map<String, Double> createEmptyHourlyMap(int startHour, int endHour) {
        Map<String, Double> hourlyMap = new HashMap<>();
        for (int hour = startHour; hour < endHour; hour++) {
            hourlyMap.put((hour < 10 ? "0" : "") + hour + ":00", 0.0);
        }
        return hourlyMap;
    }

    private Map<String, Double> createEmptyHourlyMap(int startHour, int endHour, int startHour2, int endHour2) {
        Map<String, Double> hourlyMap = new HashMap<>();
        for (int hour = startHour; hour < endHour; hour++) {
            hourlyMap.put((hour < 10 ? "0" : "") + hour + ":00", 0.0);
        }
        for (int hour = startHour2; hour < endHour2; hour++) {
            hourlyMap.put((hour < 10 ? "0" : "") + hour + ":00", 0.0);
        }
        return hourlyMap;
    }

    private String getShift(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        if (hour >= 6 && hour < 14) {
            return "06-14";
        } else if (hour >= 14 && hour < 22) {
            return "14-22";
        } else {
            return "22-06";
        }
    }

    public Map<String, Map<String, Map<String, Double>>> getPerformanceRatesByHour2() {
        List<FeederCounts> feederCountsList = feederCountsRepository.findAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Map<String, Map<String, Map<String, Double>>> hourlyPerformanceMap = new HashMap<>();
        Map<String, Map<String, Double>> latestDayPerformanceMap = new HashMap<>();

        String latestDay = null;

        for (FeederCounts feederCount : feederCountsList) {
            Long timestamp = feederCount.getUpdateTime();
            if (timestamp != null) {
                Date date = new Date(timestamp * 1000);
                String formattedDate = dateFormat.format(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String dayKey = formattedDate.substring(0, 10);

                if (latestDay == null || dayKey.compareTo(latestDay) > 0) {
                    latestDay = dayKey;
                }

                hourlyPerformanceMap.putIfAbsent(dayKey, new HashMap<>());
                hourlyPerformanceMap.get(dayKey).putIfAbsent("06-14", new HashMap<>());
                hourlyPerformanceMap.get(dayKey).putIfAbsent("14-22", new HashMap<>());
                hourlyPerformanceMap.get(dayKey).putIfAbsent("22-06", new HashMap<>());

                double performanceRate = calculatePerformance2();

                String shift = getShiftForHour(hour);
                hourlyPerformanceMap.get(dayKey).get(shift).put(formattedDate, performanceRate);
            }
        }

        if (latestDay != null) {
            latestDayPerformanceMap = hourlyPerformanceMap.get(latestDay);
        }

        return Map.of(latestDay, latestDayPerformanceMap);
    }

    public Map<String, Map<String, Double>> getPerformanceRatesLastThreeShifts() {
        try {
            Map<String, LocalTime[]> shifts = Map.of(
                    "06-14", new LocalTime[]{LocalTime.of(6, 0), LocalTime.of(14, 0)},
                    "14-22", new LocalTime[]{LocalTime.of(14, 0), LocalTime.of(22, 0)},
                    "22-06", new LocalTime[]{LocalTime.of(22, 0), LocalTime.of(6, 0)}
            );

            List<FeederCounts> feederCountsList = feederCountsRepository.findAll();
            Map<String, Map<String, Double>> shiftPerformanceMap = new HashMap<>();

            for (String shift : shifts.keySet()) {
                shiftPerformanceMap.put(shift, new HashMap<>());
            }

            for (FeederCounts feederCount : feederCountsList) {
                Long timestamp = feederCount.getUpdateTime();
                if (timestamp != null) {
                    LocalDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.UTC).toLocalDateTime();
                    String shift = getShiftForDateTime(dateTime);

                    if (shiftPerformanceMap.containsKey(shift)) {
                        String hourKey = (dateTime.getHour() < 10 ? "0" : "") + dateTime.getHour() + ":00";
                        double performanceRate = calculatePerformance2();

                        shiftPerformanceMap.get(shift).put(hourKey, performanceRate);
                    }
                }
            }

            return shiftPerformanceMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve performance rates", e);
        }
    }

    private String getShiftForDateTime(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        if (hour >= 6 && hour < 14) return "06-14";
        if (hour >= 14 && hour < 22) return "14-22";
        return "22-06";
    }

    private String getCurrentShift(LocalDateTime now) {
        int hour = now.getHour();
        if (hour >= 6 && hour < 14) {
            return "06-14";
        } else if (hour >= 14 && hour < 22) {
            return "14-22";
        } else {
            return "22-06";
        }
    }



    private double calculatePerformanceRateForShift(String shift, LocalDateTime now) {
        LocalDateTime startOfShift;
        LocalDateTime endOfShift;

        switch (shift) {
            case "06-14":
                startOfShift = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
                endOfShift = startOfShift.plusHours(8);
                break;
            case "14-22":
                startOfShift = now.withHour(14).withMinute(0).withSecond(0).withNano(0);
                endOfShift = startOfShift.plusHours(8);
                break;
            case "22-06":
                startOfShift = now.withHour(22).withMinute(0).withSecond(0).withNano(0);
                endOfShift = startOfShift.plusHours(8);
                break;
            default:
                throw new IllegalArgumentException("Unknown shift: " + shift);
        }

        List<FeederCounts> feederCountsList = feederCountsRepository.findAll();

        long startTimestamp = startOfShift.toEpochSecond(ZoneOffset.UTC);
        long endTimestamp = endOfShift.toEpochSecond(ZoneOffset.UTC);

        long totalPlacementCount = 0;
        long totalComponentsFed = 0;

        for (FeederCounts feederCount : feederCountsList) {
            long timestamp = feederCount.getUpdateTime();
            if (timestamp >= startTimestamp && timestamp <= endTimestamp) {
                totalPlacementCount += feederCount.getPlacementCount();
                totalComponentsFed += feederCount.getTotalComponentsFed();
            }
        }

        if (totalComponentsFed == 0) {
            return 0.0;
        }

        return 100 - (double) totalPlacementCount / totalComponentsFed * 100;
    }

    public Map<String, Double> getPerformanceRateOfPreviousShift() {
        LocalDateTime now = LocalDateTime.now();
        String currentShift = getCurrentShift(now);
        String previousShift = getPreviousShift(currentShift);

        LocalDateTime previousShiftDate;
        if (currentShift.equals("06-14")) {
            // Le shift précédent est 22h-06h de la nuit précédente
            previousShiftDate = now.minusDays(1); // Aller à la veille
        } else {
            previousShiftDate = now;
        }

        double performanceRate = calculatePerformanceRateForShift(previousShift, previousShiftDate);

        Map<String, Double> result = new HashMap<>();
        result.put(previousShift, performanceRate);
        return result;
    }

    private String getPreviousShift(String currentShift) {
        switch (currentShift) {
            case "06-14":
                return "22-06";
            case "14-22":
                return "06-14";
            case "22-06":
                return "14-22";
            default:
                throw new IllegalArgumentException("Unknown shift: " + currentShift);
        }
    }




    public Map<String, Double> getHourlyPerformanceForCurrentShift() {
        Map<String, Double> hourlyPerformance = new LinkedHashMap<>();

        LocalTime now = LocalTime.now();
        String currentShift = getShiftForHour1(now.getHour());

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime shiftStart = nowDateTime.withHour(getShiftStartHour1(currentShift))
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        LocalDateTime shiftEnd;
        if ("22-6".equals(currentShift)) {
            shiftEnd = shiftStart.plusDays(1).withHour(6);
        } else {
            shiftEnd = shiftStart.plusHours(8);
        }

        List<FeederCounts> feederCountsList = feederCountsRepository.findByUpdateTimeBetween(
                shiftStart.toEpochSecond(ZoneOffset.UTC),
                shiftEnd.toEpochSecond(ZoneOffset.UTC)
        );

        int shiftHours = currentShift.equals("22-6") ? 9 : 8;
        for (int hour = 0; hour < shiftHours; hour++) {
            LocalDateTime hourStart = shiftStart.plusHours(hour);
            LocalDateTime hourEnd = hourStart.plusHours(1);

            double performanceRate = calculatePerformanceRateForPeriod(
                    hourStart.toEpochSecond(ZoneOffset.UTC),
                    hourEnd.toEpochSecond(ZoneOffset.UTC),
                    feederCountsList
            );

            hourlyPerformance.put(String.format("%02d:00", hourStart.getHour()), performanceRate);
        }

        return hourlyPerformance;
    }

    private double calculatePerformanceRateForPeriod(long startTimestamp, long endTimestamp, List<FeederCounts> feederCountsList) {
        long totalPlacementCount = 0;
        long totalComponentsFed = 0;

        for (FeederCounts feederCount : feederCountsList) {
            long timestamp = feederCount.getUpdateTime();
            if (timestamp >= startTimestamp && timestamp < endTimestamp) {
                totalPlacementCount += feederCount.getPlacementCount();
                totalComponentsFed += feederCount.getTotalComponentsFed();
            }
        }

        if (totalComponentsFed == 0) {
            return 0.0;
        }

        return 100 - (double) totalPlacementCount / totalComponentsFed * 100;
    }

    private String getShiftForHour1(int hour) {
        if (hour >= 6 && hour < 14) {
            return "6-14";
        } else if (hour >= 14 && hour < 22) {
            return "14-22";
        } else {
            return "22-6";
        }
    }

    private int getShiftStartHour1(String shift) {
        switch (shift) {
            case "6-14": return 6;
            case "14-22": return 14;
            case "22-6": return 22;
            default: throw new IllegalArgumentException("Unknown shift: " + shift);
        }
    }
}










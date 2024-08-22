package com.example.stage.entity;

import javax.persistence.*;

@Entity
@Table(name = "feeder_counts")
public class FeederCounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feeder_id")
    private Long feederId;


    @Column(name = "subslot")
    private Long subslot;

    @Column(name = "components_fed")
    private Long componentsFed;

    @Column(name = "total_components_fed")
    private Long totalComponentsFed;

    @Column(name = "placement_count")
    private Long placementCount;

    @Column(name = "total_placement_count")
    private Long totalPlacementCount;

    @Column(name = "pickup_miss")
    private Long pickupMiss;

    @Column(name = "total_pickup_miss")
    private Long totalPickupMiss;

    @Column(name = "pickup_error")
    private Long pickupError;

    @Column(name = "total_pickup_error")
    private Long totalPickupError;

    @Column(name = "shape_error")
    private Long shapeError;

    @Column(name = "total_shape_error")
    private Long totalShapeError;

    @Column(name = "recognition_error")
    private Long recognitionError;

    @Column(name = "total_recognition_error")
    private Long totalRecognitionError;

    @Column(name = "update_time")
    private Long updateTime;


    public Long getFeederId() {
        return feederId;
    }

    public void setFeederId(Long feederId) {
        this.feederId = feederId;
    }

    public Long getSubslot() {
        return subslot;
    }

    public void setSubslot(Long subslot) {
        this.subslot = subslot;
    }

    public Long getComponentsFed() {
        return componentsFed;
    }

    public void setComponentsFed(Long componentsFed) {
        this.componentsFed = componentsFed;
    }

    public Long getUpdateTime() {
        return updateTime;
    }


    public Long getTotalComponentsFed() {
        return totalComponentsFed;
    }

    public Long getPlacementCount() {
        return placementCount;
    }

    public Long getTotalPlacementCount() {
        return totalPlacementCount;
    }

    public Long getPickupMiss() {
        return pickupMiss;
    }

    public Long getTotalPickupMiss() {
        return totalPickupMiss;
    }

    public Long getPickupError() {
        return pickupError;
    }

    public Long getTotalPickupError() {
        return totalPickupError;
    }

    public Long getShapeError() {
        return shapeError;
    }

    public Long getTotalShapeError() {
        return totalShapeError;
    }

    public Long getRecognitionError() {
        return recognitionError;
    }

    public Long getTotalRecognitionError() {
        return totalRecognitionError;
    }
    // Add other getters and setters as needed

}

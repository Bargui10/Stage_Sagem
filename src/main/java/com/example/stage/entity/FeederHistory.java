package com.example.stage.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "feeder_history")
public class FeederHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feeder_history_id")
    private Integer feederHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(name = "carriage_no", nullable = false)
    private BigDecimal carriageNo;

    @Column(name = "slot", nullable = false)
    private BigDecimal slot;

    @Column(name = "subslot", nullable = false)
    private BigDecimal subslot;

    @Column(name = "time_on", nullable = false)
    private BigDecimal timeOn;

    @Column(name = "time_off", nullable = false)
    private BigDecimal timeOff;

    @Column(name = "reel_id", nullable = false)
    private Integer reelId;

    @Column(name = "operation_type")
    private BigDecimal operationType;

    @Column(name = "feeder_id")
    private BigDecimal feederId;

    @Column(name = "operator_id")
    private BigDecimal operatorId;

    @Column(name = "expected_pn")
    private String expectedPn;

    @Column(name = "pu_number", length = 20)
    private String puNumber;

    @Column(name = "material_name", length = 20)
    private String materialName;

    @Column(name = "comparison_id")
    private String comparisonId;

    @Column(name = "override_reason", nullable = false)
    private Integer overrideReason;

    @Column(name = "pac_part_no", length = 30)
    private String pacPartNo;

    @Column(name = "pac_extra_data", length = 30)
    private String pacExtraData;

    @Column(name = "pac_evaluation", nullable = false)
    private BigDecimal pacEvaluation;

    @Column(name = "mount_type", nullable = false)
    private Integer mountType;

    @Column(name = "mount_quantity", nullable = false)
    private Integer mountQuantity;

    @Column(name = "other_reel_id")
    private Integer otherReelId;

    @Column(name = "other_reel_qty")
    private Integer otherReelQty;

    @Column(name = "unmount_reason")
    private Integer unmountReason;

    @Column(name = "main_rflt_on")
    private Integer mainRfltOn;

    @Column(name = "main_rslt_on")
    private Integer mainRsltOn;

    @Column(name = "main_tsm_on")
    private String mainTsmOn;

    @Column(name = "main_rflt_off")
    private Integer mainRfltOff;

    @Column(name = "main_rslt_off")
    private Integer mainRsltOff;

    @Column(name = "main_tsm_off")
    private String mainTsmOff;

    @Column(name = "splice_rflt_on")
    private Integer spliceRfltOn;

    @Column(name = "splice_rslt_on")
    private Integer spliceRsltOn;

    @Column(name = "splice_tsm_on")
    private String spliceTsmOn;

    @Column(name = "splice_rflt_off")
    private Integer spliceRfltOff;

    @Column(name = "splice_rslt_off")
    private Integer spliceRsltOff;

    @Column(name = "splice_tsm_off")
    private String spliceTsmOff;


    // Getters and Setters

    public Integer getFeederHistoryId() {
        return feederHistoryId;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public BigDecimal getCarriageNo() {
        return carriageNo;
    }

    public BigDecimal getSlot() {
        return slot;
    }

    public BigDecimal getSubslot() {
        return subslot;
    }

    public BigDecimal getTimeOn() {
        return timeOn;
    }

    public BigDecimal getTimeOff() {
        return timeOff;
    }

    public Integer getReelId() {
        return reelId;
    }

    public BigDecimal getOperationType() {
        return operationType;
    }

    public BigDecimal getFeederId() {
        return feederId;
    }

    public BigDecimal getOperatorId() {
        return operatorId;
    }


    public String getExpectedPn() {
        return expectedPn;
    }

    public void setExpectedPn(String expectedPn) {
        this.expectedPn = expectedPn;
    }

    public String getPuNumber() {
        return puNumber;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getComparisonId() {
        return comparisonId;
    }

    public Integer getOverrideReason() {
        return overrideReason;
    }

    public String getPacPartNo() {
        return pacPartNo;
    }

    public String getPacExtraData() {
        return pacExtraData;
    }

    public BigDecimal getPacEvaluation() {
        return pacEvaluation;
    }

    public Integer getMountType() {
        return mountType;
    }

    public Integer getMountQuantity() {
        return mountQuantity;
    }

    public Integer getOtherReelId() {
        return otherReelId;
    }

    public Integer getOtherReelQty() {
        return otherReelQty;
    }

    public Integer getUnmountReason() {
        return unmountReason;
    }

    public Integer getMainRfltOn() {
        return mainRfltOn;
    }

    public Integer getMainRsltOn() {
        return mainRsltOn;
    }

    public String getMainTsmOn() {
        return mainTsmOn;
    }

    public Integer getMainRfltOff() {
        return mainRfltOff;
    }

    public Integer getMainRsltOff() {
        return mainRsltOff;
    }

    public String getMainTsmOff() {
        return mainTsmOff;
    }

    public Integer getSpliceRfltOn() {
        return spliceRfltOn;
    }

    public Integer getSpliceRsltOn() {
        return spliceRsltOn;
    }

    public String getSpliceTsmOn() {
        return spliceTsmOn;
    }

    public Integer getSpliceRfltOff() {
        return spliceRfltOff;
    }

    public Integer getSpliceRsltOff() {
        return spliceRsltOff;
    }

    public String getSpliceTsmOff() {
        return spliceTsmOff;
    }
}

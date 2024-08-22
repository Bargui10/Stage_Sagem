package com.example.stage.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Integer equipmentId;

    @Column(name = "equipment_name", nullable = false, length = 255)
    private String equipmentName;

    @Column(name = "equipment_type", nullable = false)
    private Long equipmentType;

    @Column(name = "icon_filename", nullable = false, length = 255)
    private String iconFilename;

    @Column(name = "valid_flag", nullable = false, length = 1)
    private String validFlag;

    @Column(name = "equipment_abbr", nullable = false, length = 5)
    private String equipmentAbbr;

    @Column(name = "equip_model_id", nullable = false)
    private Long equipModelId;

    @Column(name = "barcode_source", nullable = false, length = 100)
    private String barcodeSource;

    @Column(name = "pmd_priority_group_id", nullable = false)
    private Integer pmdPriorityGroupId;

    @Column(name = "machine_serial", length = 40)
    private String machineSerial;

    @Column(name = "equipment_group_id")
    private Integer equipmentGroupId;

    @Column(name = "wo_key_designation", length = 20)
    private String woKeyDesignation;

    @Column(name = "magazine_load")
    private Integer magazineLoad;

    @Column(name = "board_interlock")
    private Integer boardInterlock;

    @Column(name = "key_equipment", nullable = false)
    private Integer keyEquipment;

    @Column(name = "board_id_source")
    private Integer boardIdSource;

    @Column(name = "used_lane", length = 1)
    private String usedLane;

    @Column(name = "squeegee_count")
    private Integer squeegeeCount;

    // Ajouter d'autres colonnes et annotations si nécessaire

    // Getters and setters
    // Assurez-vous d'ajouter les getters et setters pour chaque attribut
    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    // Ajouter les autres getters et setters ici

}

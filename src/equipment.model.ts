// src/app/models/equipment.model.ts

export interface Equipment {
    equipmentId: number;
    equipmentName: string;
    equipmentType: number;
    iconFilename: string;
    validFlag: string;
    equipmentAbbr: string;
    equipModelId: number;
    barcodeSource: string;
    pmdPriorityGroupId: number;
    machineSerial?: string; // Marqué comme optionnel car il peut être nul
    equipmentGroupId?: number; // Marqué comme optionnel
    woKeyDesignation?: string; // Marqué comme optionnel
    magazineLoad?: number; // Marqué comme optionnel
    boardInterlock?: number; // Marqué comme optionnel
    keyEquipment: number;
    boardIdSource?: number; // Marqué comme optionnel
    usedLane?: string; // Marqué comme optionnel
    squeegeeCount?: number; // Marqué comme optionnel
}

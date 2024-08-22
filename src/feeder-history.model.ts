import { Equipment } from "./equipment.model";

// feeder-history.model.ts
export interface FeederHistory {
    feederHistoryId: number;
    equipment: Equipment; // Utilisez le modèle Equipment défini précédemment
    carriageNo: number;
    slot: number;
    subslot: number;
    timeOn: number;
    timeOff: number;
    reelId: number;
    operationType: number;
    feederId: number;
    operatorId: number;
    expectedPn: string;
    puNumber: string;
    materialName: string;
    comparisonId: string;
    overrideReason: number;
    pacPartNo: string;
    pacExtraData: string;
    pacEvaluation: number;
    mountType: number;
    mountQuantity: number;
    otherReelId: number;
    otherReelQty: number;
    unmountReason: number;
    mainRfltOn: number;
    mainRsltOn: number;
    mainTsmOn: string;
    mainRfltOff: number;
    mainRsltOff: number;
    mainTsmOff: string;
    spliceRfltOn: number;
    spliceRsltOn: number;
    spliceTsmOn: string;
    spliceRfltOff: number;
    spliceRsltOff: number;
    spliceTsmOff: string;
  }
  
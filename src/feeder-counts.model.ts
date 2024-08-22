import { Equipment } from "./equipment.model";

// feeder-counts.model.ts
export interface FeederCounts {
    getFeederCountsData(): unknown;
    feederId: number;
    equipment: Equipment; // Utilisez le modèle Equipment défini précédemment
    subslot: number;
    componentsFed: number;
    totalComponentsFed: number;
    placementCount: number;
    totalPlacementCount: number;
    pickupMiss: number;
    totalPickupMiss: number;
    pickupError: number;
    totalPickupError: number;
    shapeError: number;
    totalShapeError: number;
    recognitionError: number;
    totalRecognitionError: number;
    updateTime: number;
  }
  
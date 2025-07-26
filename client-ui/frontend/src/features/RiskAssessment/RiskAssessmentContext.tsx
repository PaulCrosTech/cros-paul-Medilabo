import {createContext, type Dispatch, type SetStateAction} from "react";

export type RiskAssessmentContextType = {
    riskAssessmentRefresh: boolean;
    setRiskAssessmentRefresh: Dispatch<SetStateAction<boolean>>;
}

const RiskAssessmentContext =
    createContext<RiskAssessmentContextType>({} as RiskAssessmentContextType);

export default RiskAssessmentContext;
import {type ReactNode, useState} from "react";
import RiskAssessmentContext from "./RiskAssessmentContext.tsx";

type Props = { children: ReactNode }


export default function RiskAssessmentProvider({children}: Props) {
    
    const [riskAssessmentRefresh, setRiskAssessmentRefresh] = useState(true);
    const context = {riskAssessmentRefresh, setRiskAssessmentRefresh};

    return (
        <RiskAssessmentContext value={context}>
            {children}
        </RiskAssessmentContext>
    );
}
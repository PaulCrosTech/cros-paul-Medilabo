import {getRiskScoreByPatientId} from "../../services/ApiRiskAssessment.tsx";
import {useContext, useEffect, useState} from "react";
import {useParams} from "react-router";
import LoadingComponent from "../../shared/components/LoadingComponent.tsx";
import RiskAssessmentContext from "./RiskAssessmentContext.tsx";
import Image from 'react-bootstrap/Image';


function RiskAssessment() {
    const {paramPatientId} = useParams();
    const patientId: number = Number(paramPatientId);
    const [loading, setLoading] = useState<'loading' | 'failed' | 'loaded'>('loading');
    const [riskScore, setRiskScore] = useState<string | null>(null);

    const {riskAssessmentRefresh, setRiskAssessmentRefresh} = useContext(RiskAssessmentContext);

    useEffect(() => {
        if (!riskAssessmentRefresh) {
            return;
        }
        getRiskScoreByPatientId(patientId)
            .then(
                (response) => {
                    setRiskScore(response.data);
                    setLoading('loaded');
                }
            )
            .catch(() => {
                setLoading('failed');
            })
            .finally(
                () => {
                    setRiskAssessmentRefresh(false);
                }
            );
    }, [patientId, riskAssessmentRefresh, setRiskAssessmentRefresh]);

    if (loading === 'loading' || loading === 'failed') {
        return (
            <>
                <LoadingComponent error={(loading === 'failed')}/>
            </>
        );
    }
    return (
        <>
            <div className="row d-flex justify-content-center">
                <Image
                    src={`/image/${riskScore}.png`}
                    style={{
                        width: '15em',
                        height: 'auto',
                        borderRadius: '10px',
                        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                        transition: 'transform 0.3s'
                    }}
                    alt="Risk Assessment"
                />
            </div>
        </>
    )
}

export default RiskAssessment;
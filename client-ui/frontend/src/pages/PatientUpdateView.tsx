import PatientUpdate from "../features/Patient/PatientUpdate.tsx";
import RiskAssessment from "../features/RiskAssessment/RiskAssessment.tsx";
import NoteList from "../features/Note/NoteList.tsx";
import RiskAssessmentProvider from "../features/RiskAssessment/RiskAssessmentProvider.tsx";

function PatientUpdateView() {

    return (
        <>
            <RiskAssessmentProvider>
                <div className={"row"}>

                    <div className={"col-8"}>
                        <PatientUpdate/>
                    </div>
                    <div className={"col-4 align-self-center"}>
                        <RiskAssessment/>
                    </div>
                </div>
                <div className={"row"}>
                    <div className={"col-12"}>
                        <NoteList/>
                    </div>
                </div>
            </RiskAssessmentProvider>
        </>
    );
}

export default PatientUpdateView;
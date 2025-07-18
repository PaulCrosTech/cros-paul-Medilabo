import PatientUpdate from "../features/Patient/PatientUpdate.tsx";
import RiskAssessment from "../features/RiskAssessment/RiskAssessment.tsx";
import NoteList from "../features/Note/NoteList.tsx";

function PatientUpdateView() {

    return (
        <>
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
        </>
    );
}

export default PatientUpdateView;
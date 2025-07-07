import {useParams} from 'react-router';

function PatientUpdate() {
    const {patientId} = useParams();
    return (
        <div>
            <h1>Patient Update {patientId}</h1>
            <p>This is the patient update page.</p>
        </div>
    );
}

export default PatientUpdate;
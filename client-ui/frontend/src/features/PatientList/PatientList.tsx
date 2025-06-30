import {useState, useEffect} from "react";
import Table from "react-bootstrap/Table";
import DeleteButton from "../../shared/components/DeleteButton.tsx";
import ModalConfirmation from "../../shared/components/ModalConfirmation";
import Patient from "../../domain/Patient";
import {apiGetPatients} from "../../services/ApiGetPatients.ts";
import AddButton from "../../shared/components/AddButton.tsx";

function PatientList() {

    const [patients, setPatients] = useState<Patient[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        apiGetPatients()
            .then(setPatients)
            .catch(err => setError(err.message));
    }, []);

    const [showModal, setShowModal] = useState(false);
    const [selectedPatientId, setSelectedPatientId] = useState<number | null>(null);

    const handleTableDeleteButton = (patientId: number) => {
        setSelectedPatientId(patientId);
        setShowModal(true);
    };

    const handleModalCancel = () => {
        setShowModal(false);
        setSelectedPatientId(null);
    };

    const handleModalDelete = () => {
        console.log("handleModalDeleteButton called with id: " + selectedPatientId);
        setShowModal(false);
        setSelectedPatientId(null);
    };

    if (error !== null) {
        return (
            <h1>
                Erreur lors du chargement des patients : {error}
            </h1>
        );
    }


    return (
        <div>
            <h1>Liste des patients</h1>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Prénom</th>
                    <th>Nom</th>
                    <th>Sexe</th>
                    <th>Date de naissance</th>
                    <th>Adresse</th>
                    <th>Téléphone</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {patients.map(patient => (
                    <tr key={patient.patientId}>
                        <td>{patient.patientId}</td>
                        <td>{patient.firstName}</td>
                        <td>{patient.lastName}</td>
                        <td>{patient.gender}</td>
                        <td>{patient.birthDate}</td>
                        <td>{patient.address}</td>
                        <td>{patient.phoneNumber}</td>
                        <td><DeleteButton onClick={() => handleTableDeleteButton(patient.patientId)}/>
                        </td>
                    </tr>
                ))}
                </tbody>
                <tfoot>
                <tr>
                    <td colSpan={8}><AddButton showText={true} onClick={() => console.log("Création Patient")}/></td>
                </tr>

                </tfoot>
            </Table>
            <ModalConfirmation
                show={showModal}
                onHide={handleModalCancel}
                onConfirm={handleModalDelete}
                modalColor={"danger"}
                title="Confirmation"
                body={
                    <>
                        Êtes-vous sûr de vouloir supprimer <br/> le patient :
                        <strong className={"p-1"}>
                            {patients.find(p => p.patientId === selectedPatientId)?.firstName ?? ""}
                            {patients.find(p => p.patientId === selectedPatientId)?.lastName ?? ""}
                        </strong>
                        ?
                    </>
                }
                confirmButtonText="Supprimer"
                confirmButtonVariant="danger"
                cancelButtonText="Annuler"
                cancelButtonVariant="secondary"
            />
        </div>
    );
}

export default PatientList;
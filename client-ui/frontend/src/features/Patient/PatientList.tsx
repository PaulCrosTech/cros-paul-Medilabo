import {useEffect, useState} from "react";
import Table from "react-bootstrap/Table";
import DeleteButton from "../../shared/components/DeleteButton.tsx";
import ModalConfirmation from "../../shared/components/ModalConfirmation";
import {deletePatient, getPatients} from "../../services/ApiPatient.tsx";
import AddButton from "../../shared/components/AddButton.tsx";
import type Patient from "../../domain/Patient.tsx";
import WaitingAnimation from "../../shared/components/WaitingAnimation.tsx";
import AlertMessage from "../../shared/components/AlertMessage.tsx";
import {useNavigate} from "react-router";


function PatientList() {

    const [patients, setPatients] = useState<Patient[]>([]);
    const [loading, setLoading] = useState(true);

    const [alertDelete, setAlertDelete] = useState<{ message: string; isError: boolean } | null>(null);
    const [alertPatientListError, setAlertPatientListError] = useState<boolean>(false);


    const [showModal, setShowModal] = useState(false);
    const [selectedPatientId, setSelectedPatientId] = useState<number | null>(null);

    const handleTableDeleteButton = (patientId: number) => {
        setSelectedPatientId(patientId);
        setShowModal(true);
    };

    const handleRowClick = (patientId: number) => {
        navigate(`/patients/${patientId}`);
    };

    const handleModalCancel = () => {
        setShowModal(false);
        setSelectedPatientId(null);
    };

    const handleModalDelete = async () => {
        console.log("handleModalDeleteButton called with id: " + selectedPatientId);
        try {
            if (selectedPatientId !== null) {
                // Appel à l'API pour supprimer le patient
                await deletePatient(selectedPatientId);
                // Recharger la liste des patients après suppression
                const res = await getPatients();
                setPatients(res.data);
                setAlertDelete({message: "La patient a été supprimé", isError: false});
            }
        } catch (e) {
            console.log(e instanceof Error ? e.message : "Erreur inconnue")
            setAlertDelete({message: "Une erreur est survenue lors de la suppression du patient", isError: true});
        }
        setShowModal(false);
        setSelectedPatientId(null);
    };


    useEffect(() => {
        const fetchPatients = async () => {
            try {
                const res = await getPatients();
                setPatients(res.data);
            } catch (e: unknown) {
                setAlertPatientListError(true);
                console.log(e instanceof Error ? e.message : "Erreur inconnue");
            }
            setLoading(false);
        };
        fetchPatients().then(() => {
        });
    }, []);

    const navigate = useNavigate();

    if (loading) {
        return (
            <>
                <WaitingAnimation/>
            </>
        );
    }

    return (
        <div>
            {alertPatientListError && (
                <AlertMessage
                    alertColor="danger"
                    message="Une erreur est survenue lors du chargement de la liste des patients. Veuillez réessayer plus tard."
                />
            )}
            {alertDelete !== null && (
                <AlertMessage
                    alertColor={alertDelete.isError ? "danger" : "success"}
                    message={alertDelete.message}
                />
            )}
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
                    <tr key={patient.patientId} style={{cursor: 'pointer'}}
                        onClick={() => handleRowClick(patient.patientId)}>
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
                    <td colSpan={8} className={"text-center"}>
                        <AddButton showText={true} onClick={() => navigate("/patients/create")}/>
                    </td>
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

import {useContext, useEffect, useState} from "react";
import Table from "react-bootstrap/Table";
import DeleteButton from "../../shared/components/DeleteButton.tsx";
import ModalConfirmation from "../../shared/components/ModalConfirmation";
import {deletePatient, getPatients} from "../../services/ApiPatient.tsx";
import AddButton from "../../shared/components/AddButton.tsx";
import type Patient from "../../domain/Patient.tsx";
import {useNavigate, useLocation} from "react-router";
import {faUserPlus} from '@fortawesome/free-solid-svg-icons'
import GlobalAlertContext from "../../shared/components/globalAlert/GlobalAlertContext.tsx";
import LoadingComponent from "../../shared/components/LoadingComponent.tsx";

function PatientList() {

    const {state} = useLocation();
    const {setGlobalAlert} = useContext(GlobalAlertContext);
    const navigate = useNavigate();

    const [patients, setPatients] = useState<Patient[]>([]);

    const [loading, setLoading] = useState<'loading' | 'failed' | 'loaded'>('loading');

    const [showModal, setShowModal] = useState(false);
    const [selectedPatientId, setSelectedPatientId] = useState<number | null>(null);

    const handleRowClick = (patientId: number) => {
        navigate(`/patients/${patientId}`);
    };
    const handleTableDeleteButton = (patientId: number) => {
        setSelectedPatientId(patientId);
        setShowModal(true);
    };

    const handleModalCancel = () => {
        setShowModal(false);
        setSelectedPatientId(null);
    };

    const handleModalDelete = async () => {

        if (selectedPatientId === null) {
            return;
        }

        deletePatient(selectedPatientId)
            .then(() => {
                getPatients()
                    .then((res) => {
                            setPatients(res.data);
                            setGlobalAlert({
                                message: <>Le patient a été supprimé.</>,
                                variant: "success",
                                show: true
                            });
                        }
                    )
                    .catch(() => {
                        setLoading('failed');
                    });
            })
            .catch(() => {
                    setGlobalAlert({
                        message: <>Une erreur est survenue lors de la suppression du patient.<br/>Veuillez réessayer plus
                            tard.</>,
                        variant: "danger",
                        show: true
                    });
                }
            )
            .finally(() => {
                setShowModal(false);
                setSelectedPatientId(null);
                setLoading('loaded');
            });
    };

    useEffect(() => {
        getPatients()
            .then(
                (response) => {
                    setPatients(response.data);
                    setLoading('loaded');
                }
            )
            .catch(() => {
                setLoading('failed');
            })
            .finally(() => {
                if (state?.alertCreate) {
                    setGlobalAlert({
                        message: state.alertCreate,
                        variant: "success",
                        show: true
                    });
                }
            });
    }, [setGlobalAlert, state]);

    if (loading === 'loading' || loading === 'failed') {
        return (
            <>
                <LoadingComponent error={(loading === 'failed')}/>
            </>
        );
    }

    return (
        <div>
            <h1 className={"text-center"}>Liste des patients</h1>
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
                        <AddButton icon={faUserPlus} showText={true} onClick={() => navigate("/patients/create")}/>
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

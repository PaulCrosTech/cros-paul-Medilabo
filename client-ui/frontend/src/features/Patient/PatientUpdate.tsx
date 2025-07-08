import {useParams} from 'react-router';
import {useEffect, useState} from "react";
import {getPatientById} from "../../services/ApiPatient.tsx";
import type Patient from "../../domain/Patient.tsx";
import WaitingAnimation from "../../shared/components/WaitingAnimation.tsx";
import AlertMessage from "../../shared/components/AlertMessage.tsx";
import {Form, Row, Col} from "react-bootstrap";
import UpdateButton from "../../shared/components/UpdateButton.tsx";

function PatientUpdate() {
    const {patientId} = useParams();
    const id: number = Number(patientId);

    const [patient, setPatient] = useState<Patient>();
    const [loading, setLoading] = useState(true);
    const [alertPatientListError, setAlertPatientListError] = useState<boolean>(false);

    useEffect(() => {
        const fetchPatientsById = async () => {
            try {
                const res = await getPatientById(id);
                setPatient(res.data);
            } catch (e: unknown) {
                setAlertPatientListError(true);
                console.log(e instanceof Error ? e.message : "Erreur inconnue.");
            }
            setLoading(false);
        };
        fetchPatientsById().then(() => {
        });
    }, [patientId, id]);

    if (loading) {
        return (
            <>
                <WaitingAnimation/>
            </>
        );
    } else if (alertPatientListError || patient === undefined) {
        return (
            <AlertMessage
                alertColor="danger"
                message="Une erreur est survenue lors du chargement de la fiche du patient. Veuillez réessayer plus tard."
            />
        );
    }

    return (
        <>
            <h1 className="text-center mb-5">Fiche patient</h1>

            <Form>
                <Row className="mb-3">
                    <Form.Group as={Col} className="mb-3" controlId="formBasicFirstName">
                        <Form.Label>Prénom</Form.Label>
                        <Form.Control
                            type="text"
                            name="firstName"
                            defaultValue={patient.firstName}
                            placeholder="Prénom"
                        />
                    </Form.Group>

                    <Form.Group as={Col} className="mb-3" controlId="formBasicLastName">
                        <Form.Label>Nom</Form.Label>
                        <Form.Control
                            type="text"
                            name="lastName"
                            defaultValue={patient.lastName}
                            placeholder="Nom"
                        />
                    </Form.Group>
                </Row>

                <Form.Group className="mb-3" controlId="formBasicAddress">
                    <Form.Label>Adresse</Form.Label>
                    <Form.Control
                        type="text"
                        name="address"
                        defaultValue={patient.address}
                        placeholder="Addresse"
                    />
                </Form.Group>

                <Row className="mb-3">
                    <Form.Group as={Col} className="mb-3" controlId="formBasicBirthDate">
                        <Form.Label>Date de naissance</Form.Label>
                        <Form.Control
                            type="date"
                            name="birthDate"
                            defaultValue={patient.birthDate}
                            placeholder="Date de naissance"
                        />
                    </Form.Group>

                    <Form.Group as={Col} className="mb-3" controlId="formBasicGender">
                        <Form.Label>Genre</Form.Label>
                        <Form.Select name="gender" defaultValue={patient.gender}>cd cli
                            <option value="M">Masculin</option>
                            <option value="F">Féminin</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group as={Col} className="mb-3" controlId="formBasicPhoneNumber">
                        <Form.Label>Téléphone</Form.Label>
                        <Form.Control
                            type="text"
                            name="phoneNumber"
                            defaultValue={patient.phoneNumber}
                            placeholder="Téléphone"
                        />
                    </Form.Group>
                </Row>
                <Row className="mb-3 text-center">
                    <Col>
                        <UpdateButton onClick={() => console.log("Mettre à jour le patient")} showText="true"/>
                    </Col>
                </Row>
            </Form>
        </>
    );
}

export default PatientUpdate;
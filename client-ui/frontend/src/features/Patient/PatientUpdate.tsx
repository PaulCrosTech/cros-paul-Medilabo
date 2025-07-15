import {useParams} from 'react-router';
import React, {useEffect, useState} from "react";
import {getPatientById, updatePatient} from "../../services/ApiPatient.tsx";
import Patient from "../../domain/Patient.tsx";
import WaitingAnimation from "../../shared/components/WaitingAnimation.tsx";
import AlertMessage from "../../shared/components/AlertMessage.tsx";
import {Form, Row, Col} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPen} from "@fortawesome/free-solid-svg-icons";
import type {Gender} from "../../domain/Gender.tsx";

function PatientUpdate() {
    const {patientId} = useParams();
    const id: number = Number(patientId);

    const [patient, setPatient] = useState<Patient>();
    const [loading, setLoading] = useState(true);
    const [alertPatientListError, setAlertPatientListError] = useState<boolean>(false);
    const [alertUpdate, setAlertUpdate] = useState<{ message: React.ReactNode; isError: boolean } | null>(null);
    const [formValidation, setFormValidation] = useState(false);

    function handleSubmit(e: React.FormEvent<HTMLFormElement>) {

        e.preventDefault();
        setAlertUpdate(null);
        const form = e.currentTarget;
        const formData = new FormData(form);

        if (form.checkValidity() === false) {
            e.stopPropagation();
            setFormValidation(true);
            return;
        }

        const p: Patient = new Patient(
            id,
            String(formData.get("address") ?? ""),
            String(formData.get("birthDate") ?? ""),
            String(formData.get("firstName") ?? ""),
            String(formData.get("lastName") ?? ""),
            formData.get("gender") as Gender,
            String(formData.get("phoneNumber") ?? "")
        );

        updatePatient(p)
            .then(() => {
                setAlertUpdate({message: "La patient a été mis à jour.", isError: false});
            })
            .catch(() => {
                setAlertUpdate({
                    message: (
                        <>
                            Une erreur est survenue lors de la mise à jour du patient.<br/>
                            Veuillez réessayer plus tard.
                        </>
                    ),
                    isError: true
                });

            })
    }

    useEffect(() => {
        getPatientById(id)
            .then(function (response) {
                setPatient(response.data);
            })
            .catch(
                () => setAlertPatientListError(true)
            )
            .finally(() =>
                setLoading(false)
            )
    }, [patientId, id]);

    if (loading) {
        return (
            <>
                <WaitingAnimation/>
            </>
        );
    }
    if (alertPatientListError || patient === undefined) {
        return (
            <AlertMessage
                alertColor="danger"
                message={
                    <>
                        Une erreur est survenue lors du chargement de la fiche du patient.
                        <br/>Veuillez réessayer plus tard.
                    </>
                }
            />
        );
    }

    return (
        <>
            {alertUpdate !== null && (
                <AlertMessage
                    alertColor={alertUpdate.isError ? "danger" : "success"}
                    message={alertUpdate.message}
                />
            )}
            <h1 className="text-center mb-5">Fiche patient</h1>

            <Form method={"post"} onSubmit={handleSubmit} noValidate validated={formValidation}>
                <Row className="mb-3">
                    <Form.Group as={Col} className="mb-3" controlId="formFirstName">
                        <Form.Label>Prénom</Form.Label>
                        <Form.Control
                            type="text"
                            name="firstName"
                            defaultValue={patient.firstName}
                            placeholder="Prénom"
                            required
                            maxLength={45}
                        />
                        <Form.Control.Feedback type="invalid">
                            Le prénom est requis.
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group as={Col} className="mb-3" controlId="formLastName">
                        <Form.Label>Nom</Form.Label>
                        <Form.Control
                            type="text"
                            name="lastName"
                            defaultValue={patient.lastName}
                            placeholder="Nom"
                            required
                            maxLength={45}
                        />
                        <Form.Control.Feedback type="invalid">
                            Le nom est requis.
                        </Form.Control.Feedback>
                    </Form.Group>
                </Row>

                <Form.Group className="mb-3" controlId="formAddress">
                    <Form.Label>Adresse</Form.Label>
                    <Form.Control
                        type="text"
                        name="address"
                        defaultValue={patient.address}
                        placeholder="Addresse"
                        maxLength={100}
                    />
                </Form.Group>

                <Row className="mb-3">
                    <Form.Group as={Col} className="mb-3" controlId="formBirthDate">
                        <Form.Label>Date de naissance</Form.Label>
                        <Form.Control
                            type="date"
                            name="birthDate"
                            defaultValue={patient.birthDate}
                            placeholder="Date de naissance"
                            max={new Date().toISOString().split("T")[0]}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            La date de naissance est requise, et doit être dans le passé.
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group as={Col} className="mb-3" controlId="formGender">
                        <Form.Label>Genre</Form.Label>
                        <Form.Select name="gender" defaultValue={patient.gender}>
                            <option value="M">Masculin</option>
                            <option value="F">Féminin</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group as={Col} className="mb-3" controlId="formPhoneNumber">
                        <Form.Label>Téléphone</Form.Label>
                        <Form.Control
                            type="text"
                            name="phoneNumber"
                            defaultValue={patient.phoneNumber}
                            placeholder="Téléphone"
                            maxLength={12}
                            pattern="(^$)|(^\d{3}-\d{3}-\d{4}$)"
                        />
                        <Form.Control.Feedback type="invalid">
                            Le format doit être XXX-XXX-XXXX
                        </Form.Control.Feedback>
                    </Form.Group>
                </Row>
                <Row className="mb-3 text-center">
                    <Col>
                        <button type="submit"
                                className="btn btn-warning text-white">
                            <FontAwesomeIcon icon={faPen}/>
                            <span className="ms-1"> Mettre à jour</span>
                        </button>
                    </Col>
                </Row>
            </Form>
        </>
    );
}

export default PatientUpdate;
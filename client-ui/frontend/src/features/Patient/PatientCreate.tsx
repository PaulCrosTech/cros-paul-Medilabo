import {Col, Form, Row} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUserPlus} from '@fortawesome/free-solid-svg-icons'
import React, {useContext, useEffect, useState} from "react";
import Patient from "../../domain/Patient.tsx";
import type {Gender} from "../../domain/Gender.tsx";
import {createPatient} from "../../services/ApiPatient.tsx";
import {useNavigate} from "react-router";
import GlobalAlertContext from "../../shared/components/globalAlert/GlobalAlertContext.tsx";

function PatientCreate() {

    const {setGlobalAlert} = useContext(GlobalAlertContext);

    const [formValidation, setFormValidation] = useState(false);
    const navigate = useNavigate();

    function handleSubmit(e: React.FormEvent<HTMLFormElement>) {

        e.preventDefault();
        setGlobalAlert({show: false});
        const form = e.currentTarget;
        const formData = new FormData(form);

        if (form.checkValidity() === false) {
            e.stopPropagation();
            setFormValidation(true);
            return;
        }

        const firstName: string = String(formData.get("firstName") ?? "");
        const lastName: string = String(formData.get("lastName") ?? "");
        const p: Patient = new Patient(
            1,
            String(formData.get("address") ?? ""),
            String(formData.get("birthDate") ?? ""),
            firstName,
            lastName,
            formData.get("gender") as Gender,
            String(formData.get("phoneNumber") ?? "")
        );

        createPatient(p)
            .then(() => {
                navigate("/patients", {
                    state: {
                        alertCreate: `Le patient ${firstName} ${lastName} a été créé avec succès.`
                    }
                });
            })
            .catch(() => {
                    setGlobalAlert({
                        message: <>Une erreur est survenue lors de la création du patient.</>,
                        variant: "danger",
                        show: true
                    });
                }
            )
    }

    useEffect(() => {
        setGlobalAlert({show: false});
    }, [setGlobalAlert]);

    return (
        <>
            <h1 className="text-center mb-5">Fiche patient</h1>

            <Form method={"post"} onSubmit={handleSubmit} noValidate validated={formValidation}>
                <Row className="mb-3">
                    <Form.Group as={Col} className="mb-3" controlId="formFirstName">
                        <Form.Label>Prénom</Form.Label>
                        <Form.Control
                            type="text"
                            name="firstName"
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
                        <Form.Select name="gender">
                            <option value="M">Masculin</option>
                            <option value="F">Féminin</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group as={Col} className="mb-3" controlId="formPhoneNumber">
                        <Form.Label>Téléphone</Form.Label>
                        <Form.Control
                            type="text"
                            name="phoneNumber"
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
                                className="btn btn-success text-white">
                            <FontAwesomeIcon icon={faUserPlus}/>
                            <span className="ms-1"> Créer</span>
                        </button>
                    </Col>
                </Row>
            </Form>
        </>
    );
}

export default PatientCreate;
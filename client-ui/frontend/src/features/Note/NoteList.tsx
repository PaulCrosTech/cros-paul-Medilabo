import Table from "react-bootstrap/Table";
import DeleteButton from "../../shared/components/DeleteButton.tsx";
import {createNote, deleteById, getByPatientId} from "../../services/ApiNote.tsx";
import {useParams} from 'react-router';
import Note from "../../domain/Note.tsx";
import {faNotesMedical} from "@fortawesome/free-solid-svg-icons";
import ModalConfirmation from "../../shared/components/ModalConfirmation.tsx";
import React, {useContext, useEffect, useState} from "react";
import {Form} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import GlobalAlertContext from "../../shared/components/globalAlert/GlobalAlertContext.tsx";
import LoadingComponent from "../../shared/components/LoadingComponent.tsx";
import RiskAssessmentContext from "../RiskAssessment/RiskAssessmentContext.tsx";

function fetchNotes(
    patientId: number,
    setNotes: (notes: Note[]) => void,
    setLoading: (status: 'loading' | 'failed' | 'loaded')
        => void) {

    getByPatientId(patientId)
        .then((response) => {
            const formattedNotes = response.data.map((item: Note) =>
                new Note(item.id, item.patientId, item.note, item.createdAt)
            );
            setNotes(formattedNotes);
            setLoading('loaded');
        })
        .catch(
            () => setLoading('failed')
        )
}


function NoteList() {

    const {paramPatientId} = useParams();
    const patientId: number = Number(paramPatientId);
    const {setRiskAssessmentRefresh} = useContext(RiskAssessmentContext);
    const [notes, setNotes] = useState<Note[]>([]);

    const {setGlobalAlert} = useContext(GlobalAlertContext);
    const [loading, setLoading] = useState<'loading' | 'failed' | 'loaded'>('loading');

    const [formValidation, setFormValidation] = useState(false);

    const [showModal, setShowModal] = useState(false);
    const [selectedNoteId, setSelectedNoteId] = useState<string | null>(null);
    const handleTableDeleteButton = (noteId: string) => {
        setSelectedNoteId(noteId);
        setShowModal(true);
    };
    const handleModalCancel = () => {
        setShowModal(false);
        setSelectedNoteId(null);
    };
    const handleModalDelete = async () => {

        if (selectedNoteId === null) {
            return;
        }

        deleteById(selectedNoteId)
            .then(() => {
                setGlobalAlert({
                    message: <>La note a été supprimée.</>,
                    variant: "success",
                    show: true
                });
            })
            .catch(() => {
                    setGlobalAlert({
                        message: <>Une erreur est survenue lors de la suppression de la note.</>,
                        variant: "danger",
                        show: true
                    });
                }
            )
            .finally(() => {
                fetchNotes(patientId, setNotes, setLoading);
                setShowModal(false);
                setSelectedNoteId(null);
                setRiskAssessmentRefresh(true);
            });
    };

    function handleTableAddButton(e: React.FormEvent<HTMLFormElement>) {

        e.preventDefault();
        setGlobalAlert({show: false});
        const form = e.currentTarget;
        const formData = new FormData(form);

        if (form.checkValidity() === false) {
            e.stopPropagation();
            setFormValidation(true);
            return;
        }

        createNote(patientId, formData.get("noteContent") as string)
            .then(() => {

                setGlobalAlert({
                    message: <>La note a été sauvegardée.</>,
                    variant: "success",
                    show: true
                });
                fetchNotes(patientId, setNotes, setLoading);
                setFormValidation(false);
                setRiskAssessmentRefresh(true);
                form.reset();
            })
            .catch(() => {
                setGlobalAlert({
                    message: <>Une erreur est survenue lors de la sauvegarde de la note.</>,
                    variant: "danger",
                    show: true
                });
            })
    }


    useEffect(() => {
        fetchNotes(patientId, setNotes, setLoading);
    }, [paramPatientId, patientId]);

    if (loading === 'loading' || loading === 'failed') {
        return (
            <>
                <LoadingComponent error={(loading === 'failed')}/>
            </>
        );
    }

    return (
        <>
            <h1 className={"text-center"}>Notes</h1>

            <Table striped bordered hover>
                <thead>
                <tr className={"text-center"}>
                    <th>Note</th>
                    <th>Date création</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {notes.map(note => {
                    return (
                        <tr key={note.id}>
                            <td className={"col-9"} style={{whiteSpace: "pre-wrap"}}>
                                {note.note}
                            </td>
                            <td className={"col-2 text-center"}><i>{note.getCreatedAtFormatted()}</i></td>
                            <td className={"col-1 text-center align-content-center"}>
                                <DeleteButton onClick={() => handleTableDeleteButton(note.id)}/>
                            </td>
                        </tr>
                    );
                })}
                </tbody>
            </Table>
            <Form method={"post"} onSubmit={handleTableAddButton} noValidate validated={formValidation}>
                <Table bordered>
                    <tfoot>
                    <tr>
                        <td className={"col-9"} colSpan={2}>
                            <Form.Group controlId="formNoteContent">
                                <Form.Control
                                    as="textarea"
                                    name="noteContent"
                                    required
                                    placeholder="Saisissez le contenu de la note"
                                    width={"100%"}
                                    height={"100%"}
                                    style={{height: "100px"}}
                                />
                                <Form.Control.Feedback type="invalid">
                                    Le contenu de la note est requis.
                                </Form.Control.Feedback>
                            </Form.Group>
                        </td>
                        <td className={"col-1 text-center align-content-center"}>
                            <button type="submit"
                                    className="btn btn-success text-white">
                                <FontAwesomeIcon icon={faNotesMedical}/>
                            </button>
                        </td>
                    </tr>
                    </tfoot>
                </Table>
            </Form>

            <ModalConfirmation
                show={showModal}
                onHide={handleModalCancel}
                onConfirm={handleModalDelete}
                modalColor={"danger"}
                title="Confirmation"
                body={
                    <>
                        Êtes-vous sûr de vouloir supprimer cette note ?
                    </>
                }
                confirmButtonText="Supprimer"
                confirmButtonVariant="danger"
                cancelButtonText="Annuler"
                cancelButtonVariant="secondary"
            />
        </>
    );
}

export default NoteList;

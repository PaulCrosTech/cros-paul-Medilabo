import React from 'react';
import {Modal, Button} from 'react-bootstrap';

type ModalConfirmationProps = {
    show: boolean;
    onHide: () => void;
    onConfirm: () => void;

    modalSize?: 'sm' | 'lg' | 'xl';
    modalColor?: 'primary' | 'success' | 'danger' | 'warning' | 'info';

    title?: string;
    body?: React.ReactNode;

    cancelButtonText?: string;
    cancelButtonVariant?: string;

    confirmButtonText?: string;
    confirmButtonVariant?: string;

};

const ModalConfirmation: React.FC<ModalConfirmationProps> = ({
                                                                 show,
                                                                 onHide,
                                                                 onConfirm,
                                                                 modalSize = 'lg',
                                                                 modalColor = 'primary',
                                                                 title = 'Confirmation',
                                                                 body = 'Êtes-vous sûr de vouloir continuer ?',
                                                                 confirmButtonText = 'Ok',
                                                                 confirmButtonVariant = 'success',
                                                                 cancelButtonText = 'Annuler',
                                                                 cancelButtonVariant = 'secondary'
                                                             }) => {

    return (
        <Modal show={show} onHide={onHide} centered size={modalSize}>
            <Modal.Header closeButton closeVariant="white" className={`bg-${modalColor} text-white`}>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {body}
            </Modal.Body>
            <Modal.Footer>
                <Button variant={cancelButtonVariant} onClick={onHide}>
                    {cancelButtonText}
                </Button>
                <Button variant={confirmButtonVariant} onClick={onConfirm}>
                    {confirmButtonText}
                </Button>
            </Modal.Footer>
        </Modal>
    );
};


export default ModalConfirmation;
import {Alert} from "react-bootstrap";
import React from "react";

type AlertMessageProps = {
    alertColor?: "success" | "danger" | "warning" | "info";
    message: React.ReactNode;
}

function AlertMessage(
    {
        alertColor = 'success',
        message = '',
    }: AlertMessageProps) {
    return (
        <Alert variant={alertColor} dismissible>
            {message}
        </Alert>
    );
}

export default AlertMessage;
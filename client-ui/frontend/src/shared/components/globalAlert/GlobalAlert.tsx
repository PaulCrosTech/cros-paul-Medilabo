import {Alert} from "react-bootstrap";
import GlobalAlertContext from "./GlobalAlertContext.tsx";
import {useContext} from "react";


function GlobalAlert() {

    const {globalAlert} = useContext(GlobalAlertContext);

    if (!globalAlert.show) {
        return <></>;
    }

    return (
        <Alert variant={globalAlert.variant} dismissible className={"text-center"}>
            {globalAlert.message}
        </Alert>
    );
}

export default GlobalAlert;
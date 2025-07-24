import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faExclamationTriangle} from '@fortawesome/free-solid-svg-icons';

type LoadingComponentProps = {
    error?: boolean;
}

function LoadingComponent({error = false}: LoadingComponentProps) {
    const message = error ? "Une erreur est survenue." : "Chargement en cours ...";
    const variant = error ? "danger" : "primary";

    return (
        <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
            <Button variant={variant} disabled size={"lg"} className="d-flex align-items-center">
                {error ? (
                    <i>
                        <FontAwesomeIcon icon={faExclamationTriangle} aria-hidden="true"/>
                    </i>
                ) : (
                    <Spinner
                        as="span"
                        animation="border"
                        role="status"
                        aria-hidden="true"
                    />
                )}
                <span className="ms-3 fs-5">{message}</span>
            </Button>
        </div>
    );
}

export default LoadingComponent;
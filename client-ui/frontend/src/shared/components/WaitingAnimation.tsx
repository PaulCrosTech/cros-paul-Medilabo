import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';


type WaitingAnimationProps = {
    message?: string;
}

function WaitingAnimation({message}: WaitingAnimationProps) {
    if (!message) {
        message = "Chargement ...";
    }

    return (
        <div style={{display: "flex", justifyContent: "center", alignItems: "center", height: "100vh"}}>
            <Button variant="primary" disabled size={"lg"} className="d-flex align-items-center">
                <Spinner
                    as="span"
                    animation="border"
                    role="status"
                    aria-hidden="true"
                />
                <span className="ms-3 fs-5">{message}</span>
            </Button>
        </div>
    );
}

export default WaitingAnimation;
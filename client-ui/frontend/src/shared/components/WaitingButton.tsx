import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';


type WaitingButtonProps = {
    message?: string;
}

function WaitingButton({message}: WaitingButtonProps) {
    if (!message) {
        message = "Chargement ...";
    }

    return (
        <Button variant="primary" disabled size={"lg"} className="d-flex align-items-center">
            <Spinner
                as="span"
                animation="border"
                role="status"
                aria-hidden="true"
            />
            <span className="ms-3 fs-5">{message}</span>
        </Button>
    );
}

export default WaitingButton;
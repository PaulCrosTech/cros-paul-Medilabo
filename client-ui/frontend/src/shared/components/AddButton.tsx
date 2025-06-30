import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faUserPlus} from '@fortawesome/free-solid-svg-icons'
import * as React from "react";

type AddButtonProps = {
    onClick: () => void;
    showText?: boolean;
}

const AddButton: React.FC<AddButtonProps> = ({onClick, showText}) => {
    if (!showText) {
        showText = false;
    }
    return (
        <button
            onClick={onClick}
            className="btn btn-success">
            <FontAwesomeIcon icon={faUserPlus}/>
            {showText && <span className="ms-1"> Créer</span>}
        </button>
    );
}

export default AddButton;
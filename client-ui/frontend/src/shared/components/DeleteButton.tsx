import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faTrash} from '@fortawesome/free-solid-svg-icons'
import * as React from "react";

type ButtonDeleteProps = {
    onClick: () => void;
    showText?: boolean;
}

const DeleteButton: React.FC<ButtonDeleteProps> = ({onClick, showText = false}) => {
    const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.stopPropagation();
        onClick();
    };
    return (
        <button
            onClick={handleClick}
            className="btn btn-danger">
            <FontAwesomeIcon icon={faTrash}/>
            {showText && <span className="ms-1"> Supprimer</span>}
        </button>
    );
}

export default DeleteButton;
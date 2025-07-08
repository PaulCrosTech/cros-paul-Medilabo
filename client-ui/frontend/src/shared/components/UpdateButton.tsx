import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faPen} from '@fortawesome/free-solid-svg-icons'
import * as React from "react";

type ButtonUpdateProps = {
    onClick: () => void;
    showText?: boolean;
}

const UpdateButton: React.FC<ButtonUpdateProps> = ({onClick, showText = false}) => {
    const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.stopPropagation();
        onClick();
    };
    return (
        <button
            onClick={handleClick}
            className="btn btn-warning text-white">
            <FontAwesomeIcon icon={faPen}/>
            {showText && <span className="ms-1"> Mettre à jour</span>}
        </button>
    );
}

export default UpdateButton;
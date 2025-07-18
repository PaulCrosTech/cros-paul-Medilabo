import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faPlus} from '@fortawesome/free-solid-svg-icons'
import * as React from "react";
import type {IconProp} from "@fortawesome/fontawesome-svg-core";

type AddButtonProps = {
    onClick: () => void;
    showText?: boolean;
    icon?: IconProp
}

const AddButton: React.FC<AddButtonProps> = ({onClick, showText = false, icon = faPlus}) => {
    return (
        <button
            onClick={onClick}
            className="btn btn-success">
            <FontAwesomeIcon icon={icon}/>
            {showText && <span className="ms-1"> Créer</span>}
        </button>
    );
}

export default AddButton;
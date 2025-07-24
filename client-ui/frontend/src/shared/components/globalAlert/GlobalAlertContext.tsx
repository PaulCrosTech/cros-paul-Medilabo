import React, {createContext, type Dispatch, type SetStateAction} from 'react';

export type GlobalAlertContextType = {
    globalAlert: {
        show: boolean;
        message?: React.ReactNode;
        variant?: 'success' | 'danger' | 'warning' | 'info';
    };
    setGlobalAlert: Dispatch<SetStateAction<GlobalAlertContextType['globalAlert']>>;
};


const GlobalAlertContext = createContext<GlobalAlertContextType>({} as GlobalAlertContextType);

export default GlobalAlertContext;
import {type ReactNode, useState} from "react";
import GlobalAlertContext, {type GlobalAlertContextType} from "./GlobalAlertContext.tsx";

type Props = { children: ReactNode }

export default function GlobalAlertProvider({children}: Props) {


    const [globalAlert, setGlobalAlert] = useState<GlobalAlertContextType['globalAlert']>({
        message: '',
        show: false,
        variant: 'success',
    });
    const context: GlobalAlertContextType = {globalAlert, setGlobalAlert};

    return (
        <GlobalAlertContext value={context}>
            {children}
        </GlobalAlertContext>
    );
}
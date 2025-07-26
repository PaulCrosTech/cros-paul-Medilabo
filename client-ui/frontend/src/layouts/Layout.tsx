import {Outlet, useLocation} from 'react-router';
import {Header} from "../shared/components/Header.tsx";
import GlobalAlert from "../shared/components/globalAlert/GlobalAlert.tsx";
import {useContext, useEffect} from "react";
import GlobalAlertContext from "../shared/components/globalAlert/GlobalAlertContext.tsx";

function Layout() {
    const location = useLocation();
    const {setGlobalAlert} = useContext(GlobalAlertContext);

    useEffect(() => {
        setGlobalAlert({show: false});
    }, [location, setGlobalAlert]);
    return (
        <main>
            <Header/>
            <div className="container p-3">
                <GlobalAlert/>
                <Outlet/>
            </div>
        </main>
    );
}

export default Layout;
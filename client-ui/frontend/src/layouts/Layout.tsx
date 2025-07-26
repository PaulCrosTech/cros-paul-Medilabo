import {Outlet} from 'react-router';
import {Header} from "../shared/components/Header.tsx";
import GlobalAlertProvider from "../shared/components/globalAlert/GlobalAlertProvider.tsx";
import GlobalAlert from "../shared/components/globalAlert/GlobalAlert.tsx";

function Layout() {

    return (
        <main>
            <GlobalAlertProvider>
                <Header/>
                <div className="container p-3">
                    <GlobalAlert/>
                    <Outlet/>
                </div>
            </GlobalAlertProvider>
        </main>
    );
}

export default Layout;
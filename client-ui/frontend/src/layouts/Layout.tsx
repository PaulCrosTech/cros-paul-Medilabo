import {Outlet} from 'react-router';
import {Header} from "../shared/components/Header.tsx";


function Layout() {
    return (
        <main>
            <Header/>
            <div className="container p-3">
                <Outlet/>
            </div>
        </main>
    );
}

export default Layout;
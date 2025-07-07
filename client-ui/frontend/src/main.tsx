import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css';


import {BrowserRouter, Routes, Route} from "react-router";

import {Header} from "./shared/components/Header.tsx";
import NotFoundView from "./pages/NotFoundView.tsx";
import HomeView from "./pages/HomeView.tsx";
import PatientListView from "./pages/PatientListView.tsx";
import PatientCreateView from "./pages/PatientCreateView.tsx";


createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={<HomeView/>}></Route>
                <Route path="/patients" element={<PatientListView/>}></Route>
                <Route path="/patients/create" element={<PatientCreateView/>}></Route>
                <Route path="*" element={<NotFoundView/>}></Route>
            </Routes>
        </BrowserRouter>
    </StrictMode>,
)

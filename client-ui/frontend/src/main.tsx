import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import 'bootstrap/dist/css/bootstrap.min.css';


import {BrowserRouter, Routes, Route} from "react-router";

import NotFoundView from "./pages/NotFoundView.tsx";
import HomeView from "./pages/HomeView.tsx";
import PatientListView from "./pages/PatientListView.tsx";
import PatientCreateView from "./pages/PatientCreateView.tsx";
import Layout from "./layouts/Layout.tsx";
import PatientUpdateView from "./pages/PatientUpdateView.tsx";


createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>
                <Route element={<Layout/>}>
                    <Route path="/" element={<HomeView/>}></Route>
                    <Route path="/patients" element={<PatientListView/>}></Route>
                    <Route path="/patients/create" element={<PatientCreateView/>}></Route>
                    <Route path="/patients/:paramPatientId" element={<PatientUpdateView/>}></Route>
                    <Route path="*" element={<NotFoundView/>}></Route>
                </Route>
            </Routes>
        </BrowserRouter>
    </StrictMode>
)

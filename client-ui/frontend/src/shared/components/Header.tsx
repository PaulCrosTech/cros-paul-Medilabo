import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import {NavLink, useLocation} from "react-router";


export function Header() {
    const location = useLocation();
    const isPatientsActive = location.pathname.startsWith("/patients");
    return (
        <Navbar collapseOnSelect expand="lg" variant={"light"} bg={"light"}>
            <Container>
                <Navbar.Brand href="/" className="d-flex align-items-center">
                    <img src="/image/logo.png" alt="Logo" width="60" className="me-2"/>
                    <h1 className="h4 mb-0 text-dark">Medilabo</h1>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Navbar.Text className="ms-auto">
                        <NavLink to="/" className="header-nav-link">Accueil</NavLink>
                        <NavLink
                            to="/patients"
                            className={
                                isPatientsActive
                                    ? "header-nav-link active"
                                    : "header-nav-link"
                            }
                        >
                            Patients
                        </NavLink>
                    </Navbar.Text>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}
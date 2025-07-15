import {Link} from "react-router";

function NotFoundView() {
    return (
        <div className="container mt-5 text-center">
            <h1 className="text-center">404 - Page Not Found</h1>
            <p className="text-center">The page you are looking for does not exist.</p>
            <Link to={"/"}>Home Page</Link>
        </div>
    );
}

export default NotFoundView;
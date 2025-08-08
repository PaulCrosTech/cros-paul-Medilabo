import axios from "axios";

const axiosInstance = axios.create({
    baseURL: 'http://localhost:9001/',
    headers: {
        'Authorization': 'Basic ' + btoa(import.meta.env.MS_GATEWAY_SECURITY_USER + ':' + import.meta.env.MS_GATEWAY_SECURITY_PASSWORD),
        'Content-Type': 'application/json',
        'X-API-VERSION': '1',
    }
});

export default axiosInstance;
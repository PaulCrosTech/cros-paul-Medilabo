import axios from "axios";

const axiosInstance = axios.create({
    baseURL: 'http://localhost:9001/',
    headers: {
        'Authorization': 'Basic ' + btoa('User@1' + ':' + 'Password@1'),
        'Content-Type': 'application/json',
        'X-API-VERSION': '1',
    }
});

export default axiosInstance;
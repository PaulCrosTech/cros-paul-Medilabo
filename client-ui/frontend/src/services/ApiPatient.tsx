import axios from "axios";

const axiosInstance = axios.create({
    baseURL: 'http://localhost:9001/',
    headers: {
        'Authorization': 'Basic ' + btoa('User@1' + ':' + 'Password@1'),
        'Content-Type': 'application/json',
        'X-API-VERSION': '1',
    }
});

export const getPatients = () => axiosInstance.get("/patients");
export const getPatientById = (patientId: number) => axiosInstance.get(`/patients/${patientId}`);
export const deletePatient = (patientId: number) => axiosInstance.delete(`/patients/${patientId}`);
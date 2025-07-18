import axiosInstance from "./Api.tsx";
import type Patient from "../domain/Patient.tsx";

export const getPatients = () => axiosInstance.get("/patients");
export const getPatientById = (patientId: number) => axiosInstance.get(`/patients/${patientId}`);
export const deletePatient = (patientId: number) => axiosInstance.delete(`/patients/${patientId}`);
export const updatePatient = (patient: Patient) => {
    return axiosInstance.put(`/patients/${patient.patientId}`, patient);
}
export const createPatient = (patient: Patient) => {
    return axiosInstance.post("/patients", patient);
}
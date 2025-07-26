import axiosInstance from "./Api.tsx";

export const getRisksLabels = () => axiosInstance.get("/risks");
export const getRiskScoreByPatientId = (patientId: number) => axiosInstance.get(`/risks/${patientId}`);

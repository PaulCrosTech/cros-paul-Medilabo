import axiosInstance from "./Api.tsx";

export const getByPatientId = (patientId: number) => axiosInstance.get(`/notes/patient/${patientId}`);
export const deleteById = (noteId: string) => axiosInstance.delete(`/notes/${noteId}`);
export const createNote = (patientId: number, note: string) => {
    return axiosInstance.post("/notes", {
        patientId: patientId,
        note: note
    });
}
export const updateNote = (noteId: string, noteContent: string) => {
    return axiosInstance.put(`/notes/${noteId}`, noteContent);
}
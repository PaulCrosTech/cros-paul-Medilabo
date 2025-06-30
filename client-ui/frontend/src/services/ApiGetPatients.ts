import Patient from "../domain/Patient.tsx";

export async function apiGetPatients(): Promise<Patient[]> {
    const username = 'User@1';
    const password = 'Password@1';
    const basicAuth = 'Basic ' + btoa(username + ':' + password);

    const response = await fetch('http://localhost:9001/patients', {
        headers: {
            'Authorization': basicAuth,
            'X-API-VERSION': '1',
        }
    });

    if (!response.ok) {
        throw new Error('Erreur lors du chargement des patients');
    }

    const data = await response.json();
    return data.map(
        (p: Patient) => new Patient(p.patientId, p.address, p.birthDate, p.lastName, p.firstName, p.gender, p.phoneNumber)
    );
}
export default class Note {
    id: string;
    patientId: number;
    note: string;
    createdAt: Date;

    constructor(
        id: string,
        patientId: number,
        note: string,
        createdAt: Date
    ) {
        this.id = id;
        this.patientId = patientId;
        this.note = note;
        this.createdAt = new Date(createdAt);
    }

    getCreatedAtFormatted(): string {
        return this.createdAt.toLocaleDateString("fr-FR", {
            year: "numeric",
            month: "numeric",
            day: "numeric",
            hour: "2-digit",
            minute: "2-digit"
        });
    }

}
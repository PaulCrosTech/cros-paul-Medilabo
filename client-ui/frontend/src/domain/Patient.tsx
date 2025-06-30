import type {Gender} from './Gender';

export default class Patient {
    patientId: number;
    address: string;
    birthDate: string;
    firstName: string;
    lastName: string;
    gender: Gender;
    phoneNumber: string;

    constructor(
        patientId: number,
        address: string,
        birthDate: string,
        firstName: string,
        lastName: string,
        gender: Gender,
        phoneNumber: string
    ) {
        this.patientId = patientId;
        this.address = address;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

}
package Logic;

import java.util.*;
import java.io.*;

public class Rendezvous implements Serializable {
    private final Patient patient;
    private final Doctor doctor;
    private final Date dateTime;

    public Rendezvous(Patient patient, Doctor doctor, Date dateTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Rendezvous{patient=" + patient + ", doctor=" + doctor + ", dateTime=" + dateTime + '}';
    }
}
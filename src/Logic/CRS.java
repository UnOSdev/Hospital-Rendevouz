package Logic;

import java.util.*;
import java.io.*;

public class CRS implements Serializable {
    private final HashMap<Long, Patient> patients = new HashMap<>();
    private final LinkedList<Rendezvous> rendezvous = new LinkedList<>();
    private final HashMap<Integer, Hospital> hospitals = new HashMap<>();

    public boolean makeRendezvous(long patientId, int hospitalId, int sectionId, int diplomaId, Date desiredDate) throws IDException {
        Patient patient = patients.get(patientId);
        if (patient == null) throw new IDException("Patient not found");

        Hospital hospital = hospitals.get(hospitalId);
        if (hospital == null) throw new IDException("Hospital not found");

        Section section = hospital.getSection(sectionId);
        Doctor doctor = section.getDoctor(diplomaId);

        boolean success = doctor.getSchedule().addRendezvous(patient, doctor, desiredDate);
        if (success) {
            Rendezvous rv = new Rendezvous(patient, doctor, desiredDate);
            rendezvous.add(rv);
        }
        return success;
    }

    public void saveTablesToDisk(String fullPath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            oos.writeObject(this);
        }
    }

    public static CRS loadTablesFromDisk(String fullPath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
            return (CRS) ois.readObject();
        }
    }

    public HashMap<Long, Patient> getPatients() {
        return patients;
    }

    public LinkedList<Rendezvous> getRendezvous() {
        return rendezvous;
    }

    public HashMap<Integer, Hospital> listHospitals() {
        return hospitals;
    }
}

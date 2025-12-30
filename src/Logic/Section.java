package Logic;

import java.util.LinkedList;
import java.io.*;

public class Section implements Serializable {
    private final int id;
    private String name;

    private final LinkedList<Doctor> doctors = new LinkedList<>();

    public Section(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addDoctor(Doctor doctor) throws DuplicateInfoException {
        for (Doctor d : doctors) {
            if (d.getDiplomaId() == doctor.getDiplomaId()) {
                throw new DuplicateInfoException("Duplicate Doctor Diploma ID");
            }
        }
        doctors.add(doctor);
    }

    public Doctor getDoctor(int diplomaId) throws IDException {
        return doctors.stream()
                .filter(d -> d.getDiplomaId() == diplomaId)
                .findFirst()
                .orElseThrow(() -> new IDException("Doctor not found"));
    }

    public LinkedList<Doctor> listDoctors() {
        return doctors;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return this.name + "(" + this.id + ")";
    }
}
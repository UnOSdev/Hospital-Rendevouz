package Logic;

import java.util.*;
import java.io.*;

public class Schedule implements Serializable {
    private final LinkedList<Rendezvous> sessions = new LinkedList<>();
    private final int maxPatientPerDay;

    public Schedule(int maxPatientPerDay) {
        this.maxPatientPerDay = maxPatientPerDay;
    }

    public boolean addRendezvous(Patient patient, Doctor doctor, Date desiredDate) {
        long count = sessions.stream()
                .filter(r -> r.getDateTime().equals(desiredDate) && r.getDoctor().equals(doctor))
                .count();

        if (count >= maxPatientPerDay) {
            return false; // Reached max patients for the doctor on the given day
        }

        for(Rendezvous rend : sessions){
            if (rend.getDateTime().getTime() == desiredDate.getTime()){
                return false;
            } 
        }

        Rendezvous rendezvous = new Rendezvous(patient, doctor, desiredDate);
        sessions.add(rendezvous);
        return true;
    }

    public LinkedList<Rendezvous> getSessions() {
        return sessions;
    }
}

import java.util.*;


import java.io.*;

import org.junit.*;

import Logic.*;
public class HospitalManagementTests {

    // Test Person class
    @Test
    public void testPersonCreation() {
        Person person = new Patient("John Doe", 123456789L);
        Assert.assertTrue(person.getName().equals("John Doe") == true);
        Assert.assertEquals(123456789L, person.getNationalId());
    }

    // Test Patient class
    @Test
    public void testPatientCreation() {
        Patient patient = new Patient("Jane Doe", 987654321L);
        Assert.assertEquals("Jane Doe", patient.getName());
        Assert.assertEquals(987654321L, patient.getNationalId());
    }

    // Test Doctor class
    @Test
    public void testDoctorCreation() {
        Doctor doctor = new Doctor("Dr. Smith", 123123123L, 56789);
        Assert.assertEquals("Dr. Smith", doctor.getName());
        Assert.assertEquals(123123123L, doctor.getNationalId());
        Assert.assertEquals(56789, doctor.getDiplomaId());
    }

    @Test
    public void testDoctorSchedule() {
        Doctor doctor = new Doctor("Dr. Smith", 123123123L, 56789);
        Schedule schedule = doctor.getSchedule();
        Assert.assertNotNull(schedule);
    }

    // Test Rendezvous class
    @Test
    public void testRendezvousCreation() {
        Patient patient = new Patient("Jane Doe", 987654321L);
        Doctor doctor = new Doctor("Dr. Smith", 123123123L, 56789);
        Date date = new Date();
        Rendezvous rendezvous = new Rendezvous(patient, doctor, date);

        Assert.assertEquals(patient, rendezvous.getPatient());
        Assert.assertEquals(doctor, rendezvous.getDoctor());
        Assert.assertEquals(date, rendezvous.getDateTime());
    }

    // Test Schedule class
    @Test
    public void testScheduleAddRendezvous() {
        Doctor doctor = new Doctor("Dr. Smith", 123123123L, 56789);
        Patient patient = new Patient("Jane Doe", 987654321L);
        Schedule schedule = new Schedule(2);
        Date date = new Date();

        boolean added = schedule.addRendezvous(patient, doctor, date);
        Assert.assertTrue(added);

        // Test adding duplicate rendezvous on the same day
        boolean addedAgain = schedule.addRendezvous(patient, doctor, date);
        Assert.assertFalse(addedAgain);

        // Test exceeding the max patients per day
        Patient anotherPatient = new Patient("John Smith", 555555555L);
        boolean addedThird = schedule.addRendezvous(anotherPatient, doctor, date);
        Assert.assertFalse(addedThird);
    }

    @Test
    public void testScheduleGetSessions() {
        Schedule schedule = new Schedule(2);
        Assert.assertNotNull(schedule.getSessions());
    }

    // Test Section class
    @Test
    public void testSectionAddDoctor() throws Exception {
        Section section = new Section(1, "Cardiology");
        Doctor doctor = new Doctor("Dr. Smith", 123123123L, 56789);

        section.addDoctor(doctor);
        Assert.assertEquals(1, section.listDoctors().size());
    }

    @Test
    public void testSectionGetDoctor() throws Exception {
        Section section = new Section(1, "Cardiology");
        Doctor doctor = new Doctor("Dr. Smith", 123123123L, 56789);
        section.addDoctor(doctor);

        Doctor fetchedDoctor = section.getDoctor(56789);
        Assert.assertEquals(doctor, fetchedDoctor);
    }

    // Test Hospital class
    @Test
    public void testHospitalAddSection() throws Exception {
        Hospital hospital = new Hospital(1, "City Hospital");
        Section section = new Section(1, "Cardiology");

        hospital.addSection(section);
        Assert.assertEquals(section, hospital.getSection(1));
    }

    // Test CRS class
    @Test
    public void testCRSMakeRendezvous() throws Exception {
        CRS crs = new CRS();
        Patient patient = new Patient("Jane Doe", 987654321L);
        crs.getPatients().put(patient.getNationalId(), patient);

        Hospital hospital = new Hospital(1, "City Hospital");
        crs.listHospitals().put(1, hospital);

        Section section = new Section(1, "Cardiology");
        hospital.addSection(section);

        Doctor doctor = new Doctor("Dr. Smith", 123123123L, 56789);
        section.addDoctor(doctor);

        Date date = new Date();
        boolean success = crs.makeRendezvous(987654321L, 1, 1, 56789, date);
        Assert.assertTrue(success);

        // Test duplicate rendezvous exceeding max patients
        Schedule schedule = doctor.getSchedule();
        schedule.addRendezvous(patient, doctor, date);
        boolean exceeded = crs.makeRendezvous(987654321L, 1, 1, 56789, date);
        Assert.assertFalse(exceeded);
    }

    @Test
    public void testCRSSaveAndLoad() throws Exception {
        CRS crs = new CRS();
        String filePath = "test_crs.obj";

        // Save
        crs.saveTablesToDisk(filePath);
        File file = new File(filePath);
        Assert.assertTrue(file.exists());

        // Load
        CRS loadedCRS = CRS.loadTablesFromDisk(filePath);
        Assert.assertNotNull(loadedCRS);

        // Cleanup
        file.delete();
    }
}

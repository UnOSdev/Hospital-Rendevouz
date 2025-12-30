package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

import Logic.*;

public class PatientMode extends JFrame implements ActionListener {

    private JComboBox<Hospital> combo_hospitals = new JComboBox<>();
    private JComboBox<Section> combo_sections = new JComboBox<>();
    private JComboBox<Doctor> combo_doctors = new JComboBox<>();
    private JComboBox<String> combo_schedule_date = new JComboBox<>();
    private JComboBox<String> combo_schedule_HM = new JComboBox<>();
    private JTextField field_name = new JTextField();
    private JTextField field_ID = new JTextField();
    private JButton button_rendezvous = new JButton("Make an appointment");

    public PatientMode() {
        super();
        this.setSize(General.window_size);
        this.setBackground(General.platinum);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        ImageIcon image = new ImageIcon("./resources/images/logo_icon.png");
        this.setIconImage(image.getImage());
        this.setLayout(null);

        JLabel icon = new JLabel();
        icon.setBounds(548, 40, 183, 179);
        icon.setIcon(new ImageIcon(image.getImage().getScaledInstance(183, 179, java.awt.Image.SCALE_SMOOTH)));

        setupComponents();
        this.add(icon);
        loadData();
        this.setVisible(true);
    }

    private void setupComponents() {
        Font header_font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font normal_font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);

        JLabel text_name = new JLabel("Name: ");
        JLabel text_ID = new JLabel("ID: ");
        JLabel text_hospital = new JLabel("Hospital:");
        JLabel text_section = new JLabel("Section: ");
        JLabel text_doctor = new JLabel("Doctor: ");
        JLabel text_schedule_date = new JLabel("Date (day/month): ");
        JLabel text_schedule_HM = new JLabel("Day time: ");

        text_name.setBounds(300, 220, 100, 30);
        text_name.setFont(header_font);
        field_name.setBounds(410, 220, 300, 30);
        field_name.setFont(normal_font);

        text_ID.setBounds(300, 260, 100, 30);
        text_ID.setFont(header_font);
        field_ID.setBounds(410, 260, 300, 30);
        field_ID.setFont(normal_font);

        text_hospital.setBounds(300, 300, 300, 30);
        text_hospital.setFont(header_font);
        combo_hospitals.setBounds(300, 330, 300, 30);
        combo_hospitals.setFont(normal_font);
        combo_hospitals.addActionListener(this);

        text_section.setBounds(300, 370, 400, 30);
        text_section.setFont(header_font);
        combo_sections.setBounds(300, 400, 300, 30);
        combo_sections.setFont(normal_font);
        combo_sections.addActionListener(this);

        text_doctor.setBounds(300, 440, 300, 30);
        text_doctor.setFont(header_font);
        combo_doctors.setBounds(300, 470, 300, 30);
        combo_doctors.setFont(normal_font);

        text_schedule_date.setBounds(300, 510, 300, 30);
        text_schedule_date.setFont(header_font);
        combo_schedule_date.setBounds(300, 540, 300, 30);
        combo_schedule_date.setFont(normal_font);

        text_schedule_HM.setBounds(300, 580, 300, 30);
        text_schedule_HM.setFont(header_font);
        combo_schedule_HM.setBounds(300, 610, 300, 30);
        combo_schedule_HM.setFont(normal_font);

        button_rendezvous.setBounds(400, 680, 300, 30);
        button_rendezvous.setFont(normal_font);
        button_rendezvous.addActionListener(this);

        this.add(text_name);
        this.add(text_ID);
        this.add(field_name);
        this.add(field_ID);

        this.add(text_hospital);
        this.add(text_section);
        this.add(text_doctor);
        this.add(text_schedule_date);
        this.add(text_schedule_HM);

        this.add(combo_hospitals);
        this.add(combo_sections);
        this.add(combo_doctors);
        this.add(combo_schedule_date);
        this.add(combo_schedule_HM);

        this.add(button_rendezvous);
    }

    private void loadData() {
        try {
            General.crs = CRS.loadTablesFromDisk(General.def_objs_path);
        } catch (Exception e) {
            System.out.println("No objects file could be read: " + e.getMessage());
            General.crs = new CRS();
            return;
        }
        // Load hospitals into combo_hospitals
        for (Hospital hospital : General.crs.listHospitals().values()) {
            combo_hospitals.addItem(hospital);
        }

        // Populate schedule options (example: next 30 days and time slots)
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 30; i++) {
            combo_schedule_date.addItem(String.format("%02d/%02d", calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH) + 1));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        for (int hour = 9; hour <= 17; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                combo_schedule_HM.addItem(String.format("%02d:%02d", hour, minute));
            }
        }
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button_rendezvous) {
        // Collect and validate user input
        String name = field_name.getText().trim();
        String idString = field_ID.getText().trim();
        Hospital hospital = (Hospital) combo_hospitals.getSelectedItem();
        Section section = (Section) combo_sections.getSelectedItem();
        Doctor doctor = (Doctor) combo_doctors.getSelectedItem();
        String date = (String) combo_schedule_date.getSelectedItem();
        String time = (String) combo_schedule_HM.getSelectedItem();

        if (name.isEmpty() || idString.isEmpty() || hospital == null || section == null || doctor == null || date == null || time == null) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            long id = Long.parseLong(idString);
            // Parse date and time
            String[] dateParts = date.split("/");
            String[] timeParts = time.split(":");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date desiredDate = calendar.getTime();

            // Create or find patient
            Patient patient = General.crs.getPatients().computeIfAbsent(id, pid -> new Patient(name, pid));

            // Schedule rendezvous
            boolean success = General.crs.makeRendezvous(id, hospital.getId(), section.getId(), doctor.getDiplomaId(), desiredDate);
            if (!success) {
                JOptionPane.showMessageDialog(this, "This slot is full or an error occurred. Please try another time.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Appointment successfully scheduled and saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                try {
                    General.crs.saveTablesToDisk(General.def_objs_path);
                    System.out.println("SAVED!");
                } catch (Exception ex) {
                    System.out.println("ERROR: Could not save objects! " + ex.getMessage());
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IDException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else if (e.getSource() == combo_hospitals) {
        // Update sections based on selected hospital
        combo_sections.removeAllItems();
        combo_doctors.removeAllItems();
        Hospital hospital = (Hospital) combo_hospitals.getSelectedItem();
        if (hospital != null) {
            for (Section section : hospital.listSections()) {
                combo_sections.addItem(section);
            }
        }
    } else if (e.getSource() == combo_sections) {
        // Update doctors based on selected section
        combo_doctors.removeAllItems();
        Section section = (Section) combo_sections.getSelectedItem();
        if (section != null) {
            for (Doctor doctor : section.listDoctors()) {
                combo_doctors.addItem(doctor);
            }
        }
    }
}

}

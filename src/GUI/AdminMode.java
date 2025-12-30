package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.*;
import Logic.*;

public class AdminMode extends JFrame implements ActionListener {
    JButton button_hospital = new JButton("Add hospital");
    JButton button_section = new JButton("Add section");
    JButton button_doctor = new JButton("Add doctor");
    
    JComboBox<Hospital> combo_hospitals = new JComboBox<Hospital>();
    JComboBox<Section> combo_sections = new JComboBox<Section>();
    JComboBox<Doctor> combo_doctors = new JComboBox<Doctor>();

    public AdminMode(){
        this.setSize(General.window_size);
        this.setBackground(General.platinum);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        
        combo_hospitals.setEnabled(false);
        combo_sections.setEnabled(false);
        combo_doctors.setEnabled(false);

        loadData();

        ImageIcon image = new ImageIcon("./resources/images/logo_icon.png");
        this.setIconImage(image.getImage());
        this.setLayout(null);

        JLabel icon = new JLabel();
        icon.setBounds(548, 40, 183, 179);
        icon.setIcon(new ImageIcon(image.getImage().getScaledInstance(183, 179, java.awt.Image.SCALE_SMOOTH)));

        Font header_font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        Font normal_font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);

        combo_hospitals.setBounds(300, 300, 300, 30);
        combo_hospitals.setFont(normal_font);
        combo_hospitals.addActionListener(this);
        button_hospital.setBounds(300, 330, 300, 30);
        button_hospital.setFont(header_font);
        button_hospital.addActionListener(this);

        combo_sections.setBounds(300, 400, 300, 30);
        combo_sections.setFont(normal_font);
        combo_sections.addActionListener(this);
        button_section.setBounds(300, 430, 300, 30);
        button_section.setFont(header_font);
        button_section.addActionListener(this);

        combo_doctors.setBounds(300, 500, 300, 30);
        combo_doctors.setFont(normal_font);
        combo_doctors.addActionListener(this);
        button_doctor.setBounds(300, 530, 300, 30);
        button_doctor.setFont(header_font);
        button_doctor.addActionListener(this);

        this.add(button_hospital);
        this.add(button_section);
        this.add(button_doctor);

        this.add(combo_hospitals);
        this.add(combo_sections);
        this.add(combo_doctors);

        this.add(icon);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button_hospital) {
            String hosp_name = JOptionPane.showInputDialog("Enter new hospital name: ");
            if (hosp_name != null && !hosp_name.trim().isEmpty()) {
                Hospital hosp = new Hospital(UUID.randomUUID().hashCode(), hosp_name);
                General.crs.listHospitals().put(hosp.getId(), hosp);
                this.combo_hospitals.setEnabled(true);
                this.combo_hospitals.addItem(hosp);
                System.out.println("Added hospital: " + hosp_name);
            }
        } else if (e.getSource() == this.button_section) {
            Hospital hospital = (Hospital) this.combo_hospitals.getSelectedItem();
            if (hospital == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "You should select a hospital first!", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String sect_name = JOptionPane.showInputDialog("Enter new section name: ");
            if (sect_name != null && !sect_name.trim().isEmpty()) {
                Section section = new Section(UUID.randomUUID().hashCode(), sect_name);
                hospital.addSection(section);
                refreshSections(hospital);
                System.out.println("Added section: " + sect_name);
            }
        } else if (e.getSource() == this.button_doctor) {
            Section section = (Section) this.combo_sections.getSelectedItem();
            if (section == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "You should select a section first!", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String doct_name = JOptionPane.showInputDialog("Enter new doctor name: ");
            if (doct_name != null && !doct_name.trim().isEmpty()) {
                Doctor doctor = new Doctor(doct_name, UUID.randomUUID().hashCode(), UUID.randomUUID().hashCode());
                section.addDoctor(doctor);
                refreshDoctors(section);
                System.out.println("Added doctor: " + doct_name);
            }
        } else if (e.getSource() == this.combo_hospitals) {
            Hospital hosp = (Hospital) this.combo_hospitals.getSelectedItem();
            if (hosp != null) {
                refreshSections(hosp);
            }
        } else if (e.getSource() == this.combo_sections) {
            Section sect = (Section) this.combo_sections.getSelectedItem();
            if (sect != null) {
                refreshDoctors(sect);
            }else{
                this.combo_doctors.setEnabled(false);
            }
        }

        try {
            General.crs.saveTablesToDisk(General.def_objs_path);
        } catch (Exception ex) {
            System.out.println("ERROR: Could not save objects! " + ex.getMessage());
        }
    }

    private void refreshSections(Hospital hospital) {
        // Clear and repopulate the sections combo box
        this.combo_sections.removeAllItems();
        this.combo_doctors.removeAllItems(); // Clear doctors since section changed
        if (hospital != null) {
            this.combo_sections.setEnabled(true);
            for (Section sect : hospital.listSections()) {
                this.combo_sections.addItem(sect);
            }
        } else {
            this.combo_sections.setEnabled(false);
        }
    }

    private void refreshDoctors(Section section) {
        // Clear and repopulate the doctors combo box
        this.combo_doctors.removeAllItems();
        this.combo_doctors.setEnabled(true);
        for (Doctor doct : section.listDoctors()) {
            this.combo_doctors.addItem(doct);
        }
    }

    private void loadData() {
        try {
            General.crs = CRS.loadTablesFromDisk(General.def_objs_path);
        } catch (Exception e) {
            System.out.println("No objects file could be read: " + e.getMessage());
            General.crs = new CRS();
            return;
        }
    
        this.combo_hospitals.removeAllItems();
        this.combo_sections.removeAllItems();
        this.combo_doctors.removeAllItems();
    
        for (Hospital hosp : General.crs.listHospitals().values()) {
            this.combo_hospitals.addItem(hosp);
        }
    
        if (this.combo_hospitals.getItemCount() > 0) {
            this.combo_hospitals.setEnabled(true);
            Hospital firstHospital = (Hospital) this.combo_hospitals.getSelectedItem();
            if (firstHospital != null) {
                refreshSections(firstHospital);
            }
        }
    }
}

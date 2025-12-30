package GUI;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


public class ModeSelect extends JFrame implements ActionListener {

    private JButton patient_button;
    private JButton admin_button;
    private JButton terminal_button;
    public ModeSelect(String title){
        super(title);
        init();
    }
    private void init(){
        this.setSize(General.window_size);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(General.platinum);
        
        ImageIcon image = new ImageIcon("./resources/images/logo_icon.png");
        this.setIconImage(image.getImage());
        this.setLayout(null);

        JLabel icon = new JLabel();
        icon.setBounds(548, 47, 183, 179);
        icon.setIcon(new ImageIcon(image.getImage().getScaledInstance(183, 179, java.awt.Image.SCALE_SMOOTH)));

        JLabel head_text = new JLabel("Welcome to Ubeyda's Healthcare");
        head_text.setFont(new Font("Arial", Font.PLAIN, 30));
        head_text.setBounds(385, 240, 615, 68);

        JPanel mainPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(General.lite_gray);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK); // Border color
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        mainPanel.setBounds(300, 300, 650, 600);
        //NOTE: STOP WASTING TIME ALREADY!!!
        //STOP CARING ABOUT PRECISION, THE TEACHER WILL LITERALLY NOT CARE!
        //DO NOT USE NETBEANS, DO IT BY HANDS, IT'S MUCH EASIER. (DON'T GIVE A SINGLE F ABOUT PRECISION)
        //YOU STILL HAVE ONE HOMEWORK AND FINALS, YOU DOOCHBAG!

        JLabel choose_text = new JLabel("Choose Mode");
        choose_text.setFont(new Font("Arial", Font.PLAIN, 26));
        choose_text.setBounds(540, 350, 180, 30);
    

        patient_button = new JButton();
        patient_button.setBounds(320, 450, 300, 400);
        patient_button.setBackground(General.platinum);
        patient_button.addActionListener(this);
        patient_button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        patient_button.setText("Patient");
        patient_button.setFocusPainted(false);
        patient_button.setFont(new Font("default", Font.PLAIN, 20));
        patient_button.setVerticalTextPosition(JButton.TOP);
        patient_button.setHorizontalTextPosition(JButton.CENTER);
        patient_button.setIcon(new ImageIcon("./resources/images/patient.png"));
        patient_button.setVerticalAlignment(JButton.CENTER);
        patient_button.setHorizontalAlignment(JButton.CENTER);

        admin_button = new JButton();
        admin_button.setBounds(630, 450, 300, 400);
        admin_button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        admin_button.setBackground(General.platinum);
        admin_button.addActionListener(this);
        admin_button.setFocusPainted(false);
        admin_button.setText("Admin");
        admin_button.setFont(new Font("default", Font.PLAIN, 20));
        admin_button.setVerticalTextPosition(JButton.TOP);
        admin_button.setHorizontalTextPosition(JButton.CENTER);
        admin_button.setIcon(new ImageIcon("./resources/images/admin.png"));
        admin_button.setVerticalAlignment(JButton.CENTER);
        admin_button.setHorizontalAlignment(JButton.CENTER);


        
        this.add(admin_button);
        this.add(patient_button);
        this.add(choose_text);
        this.add(icon);
        this.add(head_text);
        this.add(mainPanel);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == patient_button){
            this.dispose();
            PatientMode mode = new PatientMode();
        }else if(e.getSource() == admin_button){
            this.dispose();
            AdminMode mode = new AdminMode();
        }
    }

    
}
/*
 * static class RoundedBorder implements Border {//May use in future
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK); // Border color
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
 */
Hospital Rendezvous System - Java Application

A hospital appointment management system with Java Swing GUI, supporting both administrative management and patient booking with persistent data storage.
Features

    Dual Interface: Admin mode for managing hospitals/departments/doctors, and patient mode for booking appointments

    Data Persistence: Automatic save/load using Java serialization

    Testing Suite: JUnit tests for core logic classes

    Schedule Management: Doctors have individual schedules with patient limits

Project Structure

    GUI/: Graphical interfaces (AdminMode, PatientMode, ModeSelect)

    Logic/: Business logic (Hospital, Section, Doctor, Patient, CRS, etc.)

    Main.java: Application entry point with GUI/test mode selection

Usage

    Admin Mode: Add hospitals → Add sections → Add doctors

    Patient Mode: Enter details → Select hospital/section/doctor → Choose date/time → Book appointment

Data Storage

Data is automatically saved to ./resources/objects/data.objs using Java serialization. The system loads this data on startup.
Testing

Run java Main -nogui to execute JUnit tests covering:

    Entity creation and validation

    Schedule management

    Appointment booking logic

    Data persistence

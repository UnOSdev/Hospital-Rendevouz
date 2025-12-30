import GUI.ModeSelect;
import org.junit.runner.JUnitCore;
class Main {
    public static void main(String[] args) {
        // Check if the '-nogui' parameter is passed
        if (args.length > 0 && args[0].equalsIgnoreCase("-nogui")) {
            System.out.println("Running in UnitTests mode...");
            JUnitCore.runClasses(HospitalManagementTests.class);
        } else {
            System.out.println("Running in GUI mode...");
            ModeSelect window = new ModeSelect("UN's Healthcare");
        }
    }
}


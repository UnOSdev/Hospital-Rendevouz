package Logic;

public class Doctor extends Person {
    private final int diplomaId;
    private final Schedule schedule;

    public Doctor(String name, long nationalId, int diplomaId) {
        super(name, nationalId);
        this.diplomaId = diplomaId;
        this.schedule = new Schedule(2); // Default max patients per day
    }

    public int getDiplomaId() {
        return diplomaId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public String toString(){
        return this.name + "(" + this.diplomaId + ")";
    }
}

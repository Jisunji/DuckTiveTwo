package Model;

public class HabitData {
    private String habitName;
    private String habitDescription;
    private String habitTime;
    private String habitDate;
    private String habitStatus;

    public HabitData(String habitName, String habitDescription, String habitTime, String habitDate, String habitStatus) {
        this.habitName = habitName;
        this.habitDescription = habitDescription;
        this.habitTime = habitTime;
        this.habitDate = habitDate;
        this.habitStatus = habitStatus;

    }

    public String getHabitDate() {
        return habitDate;
    }

    public void setHabitDate(String habitDate) {
        this.habitDate = habitDate;
    }

    public String getHabitTime() {
        return habitTime;
    }

    public void setHabitTime(String habitTime) {
        this.habitTime = habitTime;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitDescription() {
        return habitDescription;
    }
    public String getHabitStatus(){ return habitStatus; }
    public void setHabitStatus(String habitStatus){ this.habitStatus = habitStatus;}

    public void setHabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }

    public HabitData() {
    }
}

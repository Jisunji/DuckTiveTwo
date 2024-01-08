package Model;

public class AchievementsModel {
    private boolean achieved, claimed;

    public AchievementsModel(boolean achieved, boolean claimed) {
        this.achieved = achieved;
        this.claimed = claimed;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public AchievementsModel() {
    }
}

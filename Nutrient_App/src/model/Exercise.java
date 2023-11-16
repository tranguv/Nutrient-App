package src.model;

public class Exercise {
    private String name;
    private int duration;
    private String intensity;

    public Exercise(String name, int duration, String intensity) {
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getIntensity() {
        return intensity;
    }
}

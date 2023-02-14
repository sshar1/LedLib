package frc.robot.subsystems;

public enum LEDColor {
    PURPLE(70, 2, 115),
    YELLOW(150, 131, 2); 

    public int r;
    public int g;
    public int b;

    private LEDColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}

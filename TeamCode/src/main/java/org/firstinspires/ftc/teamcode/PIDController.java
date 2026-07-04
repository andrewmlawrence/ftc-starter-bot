package org.firstinspires.ftc.teamcode;

public class PIDController {
    public double Kp;
    public double Ki;
    public double Kd;
    public double previousError = 0.0;
    public double integral = 0.0;

    public PIDController(double startKp, double startKi, double startKd) {
        Kp = startKp;
        Ki = startKi;
        Kd = startKd;
    }


    public double calculate(double target, double current) {
        double error = target - current;
        integral += error;
        double derivative = error - previousError;

        double totalPower = (Kp * error) + (Ki * integral) + (Kd * derivative);
        totalPower = Range.clip(totalPower, -1.0, 1.0);

        previousError = error;
        return totalPower;
    }
}
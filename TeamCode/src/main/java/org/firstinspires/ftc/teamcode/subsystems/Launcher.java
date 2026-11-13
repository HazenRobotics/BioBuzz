package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Launcher {

    private DcMotorEx leftLauncher;
    private DcMotorEx rightLauncher;
    private int launcher_target_velocity = 1220;
    private final int TOLERANCE = 60;

    private final double F = 13.8980;
    private final double P = 0.6000;
    private boolean launcherPowerStatus;
    public Launcher(HardwareMap HW) {
        leftLauncher = HW.get(DcMotorEx.class, "leftLauncher");
        rightLauncher = HW.get(DcMotorEx.class, "rightLauncher");

        rightLauncher.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        leftLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        rightLauncher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        leftLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLauncher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcherPowerStatus = false;
    }

    public void toggleLauncher() {
        launcherPowerStatus = !launcherPowerStatus;

        if (launcherPowerStatus) {
            startLauncher();
        }
        else {
            stopLauncher();
        }
    }

    public void startLauncher() {
        leftLauncher.setVelocity(launcher_target_velocity);
        rightLauncher.setVelocity(launcher_target_velocity);
    }

    public void stopLauncher() {
        leftLauncher.setVelocity(0);
        rightLauncher.setVelocity(0);
    }

    public boolean isTargetVelocity() {
        return leftLauncher.getVelocity() >= launcher_target_velocity - TOLERANCE;
    }


    // mostly for testing

    public void changeVelocity(int delta) {
        launcher_target_velocity += delta;
    }
    public double getLeftVelocityTelemetry() {
        return leftLauncher.getVelocity();
    }
    public double getRightVelocityTelemetry() {
        return rightLauncher.getVelocity();
    }
    public int getTargetVelocityTelemetry() {
        return launcher_target_velocity;
    }
}

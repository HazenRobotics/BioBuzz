package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {

    private DcMotorEx leftLauncher;
    private DcMotorEx rightLauncher;
    public final int LAUNCHER_TARGET_VELOCITY = 1005;
    private boolean launcherPowerStatus;
    public Launcher(HardwareMap HW) {
        leftLauncher = HW.get(DcMotorEx.class, "leftLauncher");
        rightLauncher = HW.get(DcMotorEx.class, "rightLauncher");

        rightLauncher.setDirection(DcMotorSimple.Direction.REVERSE);


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
        leftLauncher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        rightLauncher.setVelocity(LAUNCHER_TARGET_VELOCITY);
    }

    public void stopLauncher() {
        leftLauncher.setVelocity(0);
        rightLauncher.setVelocity(0);
    }


    // mostly for testing
    public double getLeftVelocityTelemetry() {
        return leftLauncher.getVelocity();
    }
    public double getRightVelocityTelemetry() {
        return rightLauncher.getVelocity();
    }
    public int getTargetVelocityTelemetry() {
        return LAUNCHER_TARGET_VELOCITY;
    }
}

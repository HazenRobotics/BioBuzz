package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {

    private DcMotorEx leftLauncher;
    private DcMotorEx rightLauncher;
    public final int LAUNCHER_TARGET_VELOCITY = 1250;
    public final int LAUNCHER_MIN_VELOCITY = 1200;
    private boolean launcherPowerStatus;
    public Launcher(HardwareMap HW) {
        leftLauncher = HW.get(DcMotorEx.class, "leftLauncher");
        rightLauncher = HW.get(DcMotorEx.class, "rightLauncher");

        // TODO: check flywheel motor direction

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

    // TODO: test this
    public void startLauncher() {
        leftLauncher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        rightLauncher.setVelocity(LAUNCHER_TARGET_VELOCITY);
    }

    public void stopLauncher() {
        leftLauncher.setVelocity(0);
        rightLauncher.setVelocity(0);
    }
}

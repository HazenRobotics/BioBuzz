package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {

    private DcMotorEx launcher;
    public final int LAUNCHER_TARGET_VELOCITY = 1250;
    public final int LAUNCHER_MIN_VELOCITY = 1200;
    private boolean launcherPowerStatus;
    public Launcher(HardwareMap HW) {
        launcher = HW.get(DcMotorEx.class, "launcher");

        // TODO: check flywheel motor direction

        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
    }

    public void stopLauncher() {
        launcher.setVelocity(0);
    }
}

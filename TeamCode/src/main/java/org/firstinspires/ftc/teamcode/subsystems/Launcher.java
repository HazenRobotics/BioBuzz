package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {

    private DcMotorEx launcher;
    public final int LAUNCHER_TARGET_VELOCITY = 1250;
    public final int LAUNCHER_MIN_VELOCITY = 1200;
    public Launcher(HardwareMap HW) {
        launcher = HW.get(DcMotorEx.class, "launcher");
    }
}

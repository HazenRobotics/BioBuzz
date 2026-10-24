package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Kicker {

    Servo kicker;

    public Kicker(HardwareMap HW) {
        kicker = HW.get(Servo.class, "catcher");

        kicker.setDirection(Servo.Direction.REVERSE);
    }

    public void moveUp() {
        kicker.setPosition(1);

    }

    public void moveDown() {
        kicker.setPosition(0.5);
    }
}

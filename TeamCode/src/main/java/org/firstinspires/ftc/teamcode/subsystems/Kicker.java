package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Kicker {

    Servo kicker;

    double upPosition = 0.5;
    double downPosition = 0.1;

    public Kicker(HardwareMap HW) {
        kicker = HW.get(Servo.class, "catcher");


    }

    public void moveUp() {
        kicker.setPosition(upPosition);

    }

    public void increasePosition() {
        upPosition += 0.01;
        downPosition += 0.01;
        kicker.setPosition(upPosition);
    }

    public void decreasePosition() {
        upPosition -= 0.01;
        downPosition -= 0.01;
        kicker.setPosition(upPosition);
    }

    public void moveDown() {
        kicker.setPosition(downPosition);
    }

    public double getPosition(){
        return kicker.getPosition();
    }
}

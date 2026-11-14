package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wedge {

    Servo wedge;
    CRServo flowerWheel;
    boolean isDown;

    public Wedge(HardwareMap HW) {
        wedge = HW.get(Servo.class, "wedge");
        flowerWheel = HW.get(CRServo.class, "flowerWheel");

        flowerWheel.setDirection(DcMotorSimple.Direction.REVERSE);

        isDown = false;
    }

    public void toggleWedge() {
        isDown = !isDown;

        if (isDown) {
            moveDown();
        }
        else {
            moveUp();
        }
    }

    public void moveUp() {
        wedge.setPosition(0.5); // figure out that position
    }
    public void moveDown() {
        wedge.setPosition(0);
    }

    public void startFlowerWheel() {
        flowerWheel.setPower(1);
    }

    public void stopFlowerWheel() {
        flowerWheel.setPower(0);
    }

    // for testing
    public double getWedgePosition() {
        return wedge.getPosition();
    }
}

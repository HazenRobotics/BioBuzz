package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    DcMotorEx geckoWheels;
    CRServo leftIntake;
    CRServo rightIntake;

    private boolean intakePowerStatus;
    private final double MAX_SPEED_INTAKE_WHEELS = 0.7;
    private final double MAX_SPEED_GECKO_WHEELS = 0.65;
    private final double STOP_SPEED = 0.0;

    public Intake(HardwareMap HW) {
        geckoWheels = HW.get(DcMotorEx.class, "geckoWheels");
        leftIntake = HW.get(CRServo.class, "leftIntake");
        rightIntake = HW.get(CRServo.class, "rightIntake");

        // TODO: check the direction of the servos

        geckoWheels.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakePowerStatus = false;
    }

    // only used for specifically controlling the intake subsystem
    public void toggleIntake() {
        intakePowerStatus = !intakePowerStatus;

        if (intakePowerStatus) {
            startIntake();
        }
        else {
            stopIntake();
        }
    }

    public void stopIntake() {
        geckoWheels.setPower(STOP_SPEED);
    }

    public void startIntake() {
        geckoWheels.setPower(MAX_SPEED_GECKO_WHEELS);
    }

    public void startFlowerIntake() {
        leftIntake.setPower(MAX_SPEED_INTAKE_WHEELS);
        rightIntake.setPower(MAX_SPEED_INTAKE_WHEELS);
    }

    public void stopFlowerIntake() {
        leftIntake.setPower(STOP_SPEED);
        rightIntake.setPower(STOP_SPEED);
    }
    public void intakeReverse() {
        geckoWheels.setPower(-MAX_SPEED_GECKO_WHEELS);
    }
}
package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

    DcMotorEx frontLeft;
    DcMotorEx backLeft;
    DcMotorEx frontRight;
    DcMotorEx backRight;

    public Drive(HardwareMap HW) {
        frontLeft = HW.get(DcMotorEx.class, "frontLeft");
        backLeft = HW.get(DcMotorEx.class, "backLeft");
        frontRight = HW.get(DcMotorEx.class, "frontRight");
        backRight = HW.get(DcMotorEx.class, "backRight");



        // TODO: when testing check which motors need to be reversed


        // Makes motors stop right when they are turned off
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void drive(double forward, double rotate, double strafe) {

        frontLeft.setPower(forward + strafe + rotate);
        backLeft.setPower(forward - strafe + rotate);
        frontRight.setPower(forward - strafe - rotate);
        backRight.setPower(forward + strafe - rotate);
    }

}

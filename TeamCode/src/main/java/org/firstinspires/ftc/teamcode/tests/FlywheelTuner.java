package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

// thank you, Michael
@TeleOp(name = "FlywheelTuner", group = "tests")
public class FlywheelTuner extends OpMode {
    public DcMotorEx flywheelMotorLeft;
    public DcMotorEx flywheelMotorRight;
    public double MIN_targetVelocity = 1250; // TODO: change these for our robot
    public double MAX_TargetVelocity = 1300;
    public double curTargetVelocity = MIN_targetVelocity;
    public double F = 0;
    public double P = 0;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 1;

    //single press booleans for the other thing
    private boolean lastY = false;
    private boolean lastX = false;
    private boolean lastDpadLeft = false;
    private boolean lastDpadRight = false;
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;

    @Override
    public void init() {
        flywheelMotorLeft = hardwareMap.get(DcMotorEx.class, "leftLauncher");
        flywheelMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flywheelMotorRight = hardwareMap.get(DcMotorEx.class, "rightLauncher");
        flywheelMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheelMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidfcoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotorLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfcoefficients);
        flywheelMotorRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfcoefficients);

        telemetry.addData("Status", "Init complete");
        telemetry.update();
    }

    @Override
    public void loop() {
        //single press booleans
        boolean yPressed = gamepad1.y && !lastY;
        boolean xPressed = gamepad1.x && !lastX;
        boolean dpadLeftPressed = gamepad1.dpad_left && !lastDpadLeft;
        boolean dpadRightPressed = gamepad1.dpad_right && !lastDpadRight;
        boolean dpadUpPressed = gamepad1.dpad_up && !lastDpadUp;
        boolean dpadDownPressed = gamepad1.dpad_down && !lastDpadDown;

        // change between 1250 and 1300
        if (yPressed) {
            if (curTargetVelocity == MIN_targetVelocity) {
                curTargetVelocity = MAX_TargetVelocity;
            } else {
                curTargetVelocity = MIN_targetVelocity;
            }
        }

        //tuning size on x
        if (xPressed) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        // Tunes F (Feedforward)
        if (dpadLeftPressed) {
            F -= stepSizes[stepIndex];
        }
        if (dpadRightPressed) {
            F += stepSizes[stepIndex];
        }

        // Tunes P (Proportional)
        if (dpadUpPressed) {
            P += stepSizes[stepIndex];
        }
        if (dpadDownPressed) {
            P -= stepSizes[stepIndex];
        }

        //previous inputs, prevents fast inputs
        lastY = gamepad1.y;
        lastX = gamepad1.x;
        lastDpadLeft = gamepad1.dpad_left;
        lastDpadRight = gamepad1.dpad_right;
        lastDpadUp = gamepad1.dpad_up;
        lastDpadDown = gamepad1.dpad_down;

        // new pidf coefficients
        PIDFCoefficients pidfcoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotorLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfcoefficients);
        flywheelMotorRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfcoefficients);

        flywheelMotorLeft.setVelocity(curTargetVelocity);
        flywheelMotorRight.setVelocity(curTargetVelocity);

        double curVelocityLeft = flywheelMotorLeft.getVelocity();
        double error = curTargetVelocity - curVelocityLeft;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocityLeft);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addData("Tuning P", "%.4f (D-Pad Up/Down)", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad Left/Right)", F);
        telemetry.addData("Step Size", "%.4f (X Button)", stepSizes[stepIndex]);
        telemetry.update();
    }
}
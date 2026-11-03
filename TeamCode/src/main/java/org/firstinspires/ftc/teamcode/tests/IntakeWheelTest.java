package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Intake test", group = "tests")
public class IntakeWheelTest extends LinearOpMode {

    Intake intake;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {
                intake.startFlowerIntake();
            }
            if (gamepad1.b) {
                intake.stopFlowerIntake();
            }

        }
    }
}

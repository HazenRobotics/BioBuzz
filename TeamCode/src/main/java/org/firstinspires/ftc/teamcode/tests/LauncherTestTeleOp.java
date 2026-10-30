package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp(name = "launcher test", group = "tests")
public class LauncherTestTeleOp extends LinearOpMode {

    Launcher launcher;


    @Override
    public void runOpMode() throws InterruptedException {
        launcher = new Launcher(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {
                launcher.startLauncher();
            } else if (gamepad1.b) {
                launcher.stopLauncher();
            }
            telemetry.addLine("Testing with only the right launcher");
            telemetry.addData("Target Velocity: ", launcher.getTargetVelocityTelemetry());
            telemetry.addData("right Velocity: ", launcher.getRightVelocityTelemetry());
            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp(name="BioBuzz TeleOp")
public class BioBuzzTeleOp extends LinearOpMode {
    // init


    enum SystemState {
        OFF,
        INTAKE,
        SHOOT
    }

    SystemState systemState;

    Drive drive;
    Launcher launcher;
    Intake intake;
    Kicker kicker;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap);
        intake = new Intake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        kicker = new Kicker(hardwareMap);
        systemState = SystemState.OFF;

        double forward;
        double strafe;
        double rotate;

        waitForStart();
        while (opModeIsActive()) {

            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            drive.drive(forward, rotate, strafe);

            // button that turns on and off the gecko wheels part of the intake system
            // to be determined, decide if the intake should always be on in TeleOp
            if (gamepad1.x) {
                intake.toggleIntake();
            }

            if (gamepad1.y) {
                launcher.toggleLauncher();
            }
            // TEST
//            if (gamepad1.dpad_up) {
//                launcher.changeVelocity(10);
//            } else if (gamepad1.dpad_down) {
//                launcher.changeVelocity(-10);
//            }

            if (gamepad1.a) {
                kicker.moveUp();
                telemetry.addData("kicker: ", "up");
            } else if (gamepad1.b) {
                kicker.moveDown();
                telemetry.addData("kicker: ", "down");
            }


            if (gamepad1.right_bumper) {
                systemState = SystemState.INTAKE;
            }




            telemetry.addData("Target Velocity: ", launcher.getTargetVelocityTelemetry());
            telemetry.addData("left Velocity: ", launcher.getVelocityTelemetry());
            telemetry.addData("right Velocity: ", launcher.getRightVelocityTelemetry());
            telemetry.update();
        }
    }

    public void updateState() {
        switch (systemState) {
            case OFF:
                intake.stopIntake();
                launcher.stopLauncher();

        }
    }
}

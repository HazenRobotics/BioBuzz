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
        REVERSE,
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


            if (gamepad1.right_bumper) {
                systemState = SystemState.INTAKE;
            }
            if (gamepad1.left_bumper) {
                systemState = SystemState.SHOOT;
            }
            if (gamepad1.b) {
                systemState = SystemState.OFF;
            }
            if (gamepad1.x) {
                systemState = SystemState.REVERSE;
            }

            updateState();
            telemetry.addData("State: ", systemState);
            telemetry.addData("Target Velocity: ", launcher.getTargetVelocityTelemetry());
            telemetry.addData("left Velocity: ", launcher.getLeftVelocityTelemetry());
            telemetry.addData("right Velocity: ", launcher.getRightVelocityTelemetry());
            telemetry.update();
        }
    }

    public void updateState() { // TODO: separate shoot state with starting to shoot state
        switch (systemState) {
            case OFF:
                intake.stopIntake();
                kicker.moveDown();
                launcher.stopLauncher();
                break;
            case INTAKE:
                intake.startIntake();
                kicker.moveDown();
                launcher.stopLauncher();
                break;
            case REVERSE:
                intake.intakeReverse();
                kicker.moveDown();
                launcher.stopLauncher();
                break;
            case SHOOT:
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    kicker.moveUp();
                }
                break;
        }
    }
}
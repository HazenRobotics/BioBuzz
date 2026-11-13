package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.subsystems.Wedge;

@TeleOp(name="BioBuzz TeleOp")
public class BioBuzzTeleOp extends LinearOpMode {
    // init


    enum SystemState {
        IDLE,
        INTAKE,
        REVERSE,
        PREPARE_TO_SHOOT,
        SHOOT
    }

    SystemState systemState;

    Drive drive;
    Launcher launcher;
    Intake intake;
    Transfer transfer;
    Kicker kicker;
    Wedge wedge;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap);
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        launcher = new Launcher(hardwareMap);
        kicker = new Kicker(hardwareMap);
        wedge = new Wedge(hardwareMap);
        systemState = SystemState.IDLE;

        double forward;
        double strafe;
        double rotate;

        waitForStart();
        while (opModeIsActive()) {

            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            drive.drive(forward, rotate, strafe);

//            if (gamepad1.dpadUpWasPressed()) {
//                launcher.changeVelocity(5);
//            } else if (gamepad1.dpadDownWasPressed()) {
//                launcher.changeVelocity(-5);
//            }

//            if (gamepad1.yWasPressed()) {
//                transfer.startTransfer(Transfer.Speed.FAST);
//            }
//            if (gamepad1.aWasPressed()) {
//                transfer.stopTransfer();
//            }


            if (gamepad1.right_bumper) {
                systemState = SystemState.INTAKE;
            }
            if (gamepad1.rightTriggerWasPressed()) {
                wedge.toggleWedge();
            }
            if (gamepad1.left_bumper) {
                systemState = SystemState.PREPARE_TO_SHOOT;
            }
            if (gamepad1.b) {
                systemState = SystemState.IDLE;
            }
            if (gamepad1.x) {
                systemState = SystemState.REVERSE;
            }

            updateState();
            telemetry.addData("State: ", systemState);
            telemetry.addData("Transfer speed: ", transfer.getSpeedState());
            telemetry.addData("Target Velocity: ", launcher.getTargetVelocityTelemetry());
            telemetry.addData("left Velocity: ", launcher.getLeftVelocityTelemetry());
            telemetry.addData("right Velocity: ", launcher.getRightVelocityTelemetry());
            telemetry.update();
        }
    }

    public void updateState() {
        switch (systemState) {
            case IDLE:
                intake.stopIntake();
                transfer.stopTransfer();
                kicker.moveDown();
                launcher.stopLauncher();
                break;
            case INTAKE:
                intake.startIntake();
                wedge.startFlowerWheel();
                transfer.startTransfer(Transfer.Speed.FAST);
                kicker.moveDown();
                launcher.stopLauncher();
                break;
            case REVERSE:
                intake.intakeReverse();
                transfer.startTransfer(Transfer.Speed.REVERSE);
                kicker.moveDown();
                launcher.stopLauncher();
                break;
            case PREPARE_TO_SHOOT:
                kicker.moveDown();
                launcher.startLauncher();
                intake.startIntake();
                transfer.startTransfer(Transfer.Speed.SLOW);
                if (launcher.isTargetVelocity()) {
                    systemState = SystemState.SHOOT;
                }
                break;
            case SHOOT:
                transfer.startTransfer(Transfer.Speed.FAST);
                kicker.moveUp();
                if (!launcher.isTargetVelocity()) {
                    systemState = SystemState.PREPARE_TO_SHOOT;
                }
                break;
        }
    }
}
package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp(name="BioBuzz TeleOp")
public class BioBuzzTeleOp extends LinearOpMode {
    // init


    Drive drive;
    Launcher launcher;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap);
        launcher = new Launcher(hardwareMap);

        double forward;
        double strafe;
        double rotate;

        waitForStart();
        while (opModeIsActive()) {

            forward = gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            drive.drive(forward, rotate, strafe);

        }
    }
}

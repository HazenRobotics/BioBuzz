package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PathingCommands {

    Intake intake;
    Transfer transfer;
    Launcher launcher;
    Kicker kicker;
    Wedge wedge;
    ElapsedTime timer = new ElapsedTime();

    public Command shootThree = Command.build() // TODO: test to make sure robot actually shoots three balls
            .setStart(() -> {
                timer.reset();
                kicker.moveDown();
            })
            .setExecute(() -> {
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    intake.startIntake();
                    transfer.startTransfer(Transfer.Speed.FAST);
                    kicker.moveUp();
                }
            })
            .setDone(() -> timer.seconds() >= 3)
            .setEnd(endCondition -> {
                launcher.stopLauncher();
                intake.stopIntake();
                transfer.stopTransfer();
                kicker.moveDown();
            });

    public Command shootFour = Command.build() // same case for the shoot three command
            .setStart(() -> {
                timer.reset();
                kicker.moveDown();
            })
            .setExecute(() -> {
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    intake.startIntake();
                    transfer.startTransfer(Transfer.Speed.FAST);
                    kicker.moveUp();
                }
            })
            .setDone(() -> timer.seconds() >= 4)
            .setEnd(endCondition -> {
                launcher.stopLauncher();
                intake.stopIntake();
                transfer.stopTransfer();
                kicker.moveDown();
            });

    public Command wedgeDown = Command.build()
            .setStart(() -> wedge.moveDown())
            .setDone(() -> wedge.getWedgePosition() == 0);

    //Command intakeThree = Command.build() Cannot make this because we cannot currently intake from flower
    //Command intakeFour = Command.build() Same thing

    public PathingCommands(HardwareMap HW) {
        intake = new Intake(HW);
        launcher = new Launcher(HW);
        kicker = new Kicker(HW);
        wedge = new Wedge(HW);
        transfer = new Transfer(HW);
    }

}

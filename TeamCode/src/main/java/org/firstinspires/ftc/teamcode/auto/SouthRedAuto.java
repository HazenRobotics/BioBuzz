package org.firstinspires.ftc.teamcode.auto;



import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@Autonomous(name = "red auto", group = "comp1")
public class SouthRedAuto extends OpMode {

    private Follower follower;
    private PoseFactory poseFactory = PoseFactory.degrees();
    Launcher launcher;
    Intake intake;
    Kicker kicker;
    // poses
    ElapsedTime timer = new ElapsedTime();
    private final Pose startPose = poseFactory.of(58, 10,90);
    private final Pose parkPose = poseFactory.of(12, 95, 90);

    private Path startToPark() {
        return line(startPose, parkPose).linear(startPose, parkPose);
    }

    private Command routine() {
        kicker.moveDown();
        intake.startIntake();
        launcher.startLauncher();
        while ((launcher.getLeftVelocityTelemetry() <= launcher.LAUNCHER_TARGET_VELOCITY - 50)){

        }
        kicker.moveUp();
        timer.reset();
        while (timer.seconds() < 5) {

        }
        launcher.stopLauncher();
        intake.stopIntake();
        kicker.moveDown();
        return sequential(
                follow(follower, startToPark())
        );
    }

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        launcher = new Launcher(hardwareMap);
        intake = new Intake(hardwareMap);
        kicker = new Kicker(hardwareMap);

        follower.update();
    }

    @Override
    public void start() {
        schedule(routine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}

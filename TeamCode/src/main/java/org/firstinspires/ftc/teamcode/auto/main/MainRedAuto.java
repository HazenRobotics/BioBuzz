package org.firstinspires.ftc.teamcode.auto.main;

import static com.pedropathing.api.Paths.curve;
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

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@Autonomous(name = "red autonomous", group = "main autos")
public class MainRedAuto extends OpMode {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    Launcher launcher;
    Intake intake;
    Kicker kicker;

    private final Pose startPose = poseFactory.of(56, 9, 90);
    private final Pose firstFlowerPose = poseFactory.of(12, 45.7243, 165);
    private final Pose flowerToShootControlPose = poseFactory.of(14.45, 93.82, 0);
    private final Pose shootCycle2Pose = poseFactory.of(59.78, 129.01, -90);
    private Path startToFirstFlower() {
        return line(startPose, firstFlowerPose).linear(startPose, firstFlowerPose); // TODO: create a control pose for this path
    }
    private Path flowerToShoot() {
        return curve(firstFlowerPose, flowerToShootControlPose, shootCycle2Pose).linear(firstFlowerPose, shootCycle2Pose);
    }

    // TODO: learn how to make commands with ivy

    private Command routine() {
        return sequential(
                // create command to shoot three balls
                follow(follower, startToFirstFlower()),
                // create command to intake pollen from flower
                follow(follower, flowerToShoot())
                // create command to shoot four balls
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

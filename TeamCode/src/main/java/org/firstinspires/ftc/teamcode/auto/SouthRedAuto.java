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

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class SouthRedAuto extends OpMode {

    private Follower follower;
    private PoseFactory poseFactory = PoseFactory.degrees();

    // poses

    private final Pose startPose = poseFactory.of(35, 10,90);
    private final Pose parkPose = poseFactory.of(12, 95, 90);

    private Path startToPark() {
        return line(startPose, parkPose).linear(startPose, parkPose);
    }

    private Command routine() {
        return sequential(
                follow(follower, startToPark())
        );
    }

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);

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

package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


@Autonomous(name="mainAuto")
public class BiobuzzAuto extends OpMode {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();


    // create poses

    // south red poses
    private final Pose startSouthRed = poseFactory.of(60, 8.9, 90);
    private final Pose shootSouthRed = poseFactory.of(60, 18.9, 90); // TODO: Make necessary adjustments once we know a good distance for shooting


    // create path methods


    // south red paths
    private Path startSouthRedToScore() {
        return line(startSouthRed, shootSouthRed).linear(startSouthRed, shootSouthRed);
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }
}

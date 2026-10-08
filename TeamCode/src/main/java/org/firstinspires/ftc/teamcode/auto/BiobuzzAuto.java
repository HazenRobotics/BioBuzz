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

    private enum AutoState {
        START,
        CHECK_APRIL_TAGS
    }

    private AutoState autoState;


    // create poses

    // south red poses
    private final Pose startSouthRed = poseFactory.of(60, 8.9, 90);
    private final Pose shootSouthRed = poseFactory.of(60, 18.9, 90); // TODO: Make necessary adjustments once we know a good distance for shooting

    // north red poses
    private final Pose startNorthRed = poseFactory.of(60, 132.1, -90);
    private final Pose shootNorthRed = poseFactory.of(60, 122.1, -90);

    // south blue poses
    private final Pose startSouthBlue = poseFactory.of(82, 8.9, 90); // TODO: we may want to later change the x to something else
    private final Pose shootSouthBlue = poseFactory.of(82, 18.9, 90);

    // north blue poses
    private final Pose startNorthBlue = poseFactory.of(82, 132.1, -90);
    private final Pose shootNorthBlue = poseFactory.of(82, 122.1, -90);

    // create path methods


    // south red paths
    private Path startSouthRedToScore() {
        return line(startSouthRed, shootSouthRed).linear(startSouthRed, shootSouthRed);
    }

    // north red paths
    private Path startNorthRedToScore() {
        return line(startNorthRed, shootNorthRed).linear(startNorthRed, shootNorthRed);
    }

    // south blue paths
    private Path startSouthBlueToScore() {
        return line(startSouthBlue, shootSouthBlue).linear(startSouthBlue, shootSouthBlue);
    }

    // north blue paths
    private Path startNorthBlueToScore() {
        return line(startNorthBlue, shootNorthBlue).linear(startNorthBlue, shootNorthBlue);
    }

    public void updateStateMachine() {
        switch (autoState) {
            case START:
                // pass for now, figure out what needs to be added here
                autoState = AutoState.CHECK_APRIL_TAGS;
                break;
            case CHECK_APRIL_TAGS:
                // code for scanning April tags to figure out where the robot is goes here
        }
    }

    @Override
    public void init() {
        autoState = AutoState.START;
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }
}

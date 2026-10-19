package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.AprilTagDetector;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;


@Autonomous(name="mainAuto")
public class BiobuzzAuto extends OpMode {

    AprilTagDetector aprilTagDetector = new AprilTagDetector();

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private enum AutoState {
        START,
        CHECK_APRIL_TAGS,
        MOVE_TO_SHOOT
    }

    private enum StartLocation {
        NORTH_RED,
        SOUTH_RED,
        NORTH_BLUE,
        SOUTH_BLUE
    }

    private AutoState autoState;
    private StartLocation startLocation;

    AprilTagSingleDetection northRed1;
    AprilTagSingleDetection southRed1;
    AprilTagSingleDetection northBlue1;
    AprilTagSingleDetection southBlue1;

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
                northRed1 = aprilTagDetector.getTagById(30);
                southRed1 = aprilTagDetector.getTagById(34);
                northBlue1 = aprilTagDetector.getTagById(42);
                southBlue1 = aprilTagDetector.getTagById(38);
                autoState = AutoState.CHECK_APRIL_TAGS;
                break;
            case CHECK_APRIL_TAGS:
                // code for scanning April tags to figure out where the robot is goes here
                if (northRed1 != null) {
                    startLocation = StartLocation.NORTH_RED;
                } else if (southRed1 != null) {
                    startLocation = StartLocation.SOUTH_RED;
                } else if (northBlue1 != null) {
                    startLocation = StartLocation.NORTH_BLUE;
                } else if (southBlue1 != null) {
                    startLocation = StartLocation.SOUTH_BLUE;
                }
                break;
            case MOVE_TO_SHOOT:
                // make if statements to change action based on position
                break;
        }
    }

    @Override
    public void init() {
        Scheduler.reset();

        aprilTagDetector.init(hardwareMap, telemetry);
        follower = Constants.create(hardwareMap);
        if (startLocation == StartLocation.NORTH_RED) {follower.setPose(startNorthRed);}
        else if (startLocation == StartLocation.SOUTH_RED) {follower.setPose(startSouthRed);}
        else if (startLocation == StartLocation.NORTH_BLUE) {follower.setPose(startNorthBlue);}
        else if (startLocation == StartLocation.SOUTH_BLUE) {follower.setPose(startSouthBlue);}

        follower.update();
    }

    @Override
    public void start() {
        autoState = AutoState.START;
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        aprilTagDetector.update();
        updateStateMachine();
        aprilTagDetector.displaySimpleTelemetry(northRed1);
        aprilTagDetector.displaySimpleTelemetry(southRed1);
        aprilTagDetector.displaySimpleTelemetry(northBlue1);
        aprilTagDetector.displaySimpleTelemetry(southBlue1);
    }
}

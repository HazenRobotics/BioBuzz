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
        START_ROUTINE,
        RUNNING_ROUTINE

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

                autoState = AutoState.CHECK_APRIL_TAGS;
                break;
            case CHECK_APRIL_TAGS:
                // code for scanning April tags to figure out where the robot is goes here
                northRed1 = aprilTagDetector.getTagById(30);
                southRed1 = aprilTagDetector.getTagById(34);
                northBlue1 = aprilTagDetector.getTagById(42);
                southBlue1 = aprilTagDetector.getTagById(38);
                if (northRed1 != null) {
                    follower.setPose(startNorthRed);
                    startLocation = StartLocation.NORTH_RED;
                } else if (southRed1 != null) {
                    follower.setPose(startSouthRed);
                    startLocation = StartLocation.SOUTH_RED;
                } else if (northBlue1 != null) {
                    follower.setPose(startNorthBlue);
                    startLocation = StartLocation.NORTH_BLUE;
                } else if (southBlue1 != null) {
                    follower.setPose(startSouthBlue);
                    startLocation = StartLocation.SOUTH_BLUE;
                } else {
                    break;
                }
                autoState = AutoState.START_ROUTINE;
                break;
            case START_ROUTINE:
                // make if statements to change action based on position
                schedule(routine());
                autoState = AutoState.RUNNING_ROUTINE;
                break;
            case RUNNING_ROUTINE:
                break;
        }
    }

    private Command routine() {
        Path firstPath;
        switch (startLocation) {
            case NORTH_RED:
                firstPath = startNorthRedToScore();
                break;
            case SOUTH_RED:
                firstPath = startSouthRedToScore();
                break;
            case NORTH_BLUE:
                firstPath = startNorthBlueToScore();
                break;
            case SOUTH_BLUE:
                firstPath = startSouthBlueToScore();
                break;
            default:
                throw new IllegalStateException("Unknown starting location!");
        }
        return sequential(
            follow(follower, firstPath)
            // could put commands that go after first path
        );
    }

    @Override
    public void init() {
        Scheduler.reset();

        aprilTagDetector.init(hardwareMap, telemetry);
        follower = Constants.create(hardwareMap);

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

        telemetry.addData("XPos", aprilTagDetector.getPosX(30));
        telemetry.update();
    }
}

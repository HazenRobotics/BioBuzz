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
import org.firstinspires.ftc.teamcode.subsystems.AprilTagDetector;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;




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


    // south red poses
    private final Pose startSouthRed = poseFactory.of(60, 8.9, 90);

    // north blue poses
    private final Pose startNorthBlue = poseFactory.of(82, 132.1, -90);

    // create path methods

    /*
    TODO: We need to make the actual paths that will go from the start to the place where more balls can be intaked.
    TODO: We will also need to make the poses for those paths.
    */
    // south red paths
//    private Path startSouthRedToScore() {
//        return line(startSouthRed, shootSouthRed).linear(startSouthRed, shootSouthRed);
//    }

    // north blue paths
//    private Path startNorthBlueToScore() {
//        return line(startNorthBlue, shootNorthBlue).linear(startNorthBlue, shootNorthBlue);
//    }
    public void updateStateMachine() {
        switch (autoState) {

            case START:
                // pass for now, figure out what needs to be added here

                autoState = AutoState.CHECK_APRIL_TAGS;
                break;
            case CHECK_APRIL_TAGS:
                // Still needs to check if is on blue side or red side
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

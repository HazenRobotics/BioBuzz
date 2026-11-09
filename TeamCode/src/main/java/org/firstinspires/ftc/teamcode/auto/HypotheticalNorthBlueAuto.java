package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Kicker;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@Autonomous(name = "North Blue Auto", group = "comp1")
public class HypotheticalNorthBlueAuto extends LinearOpMode {
    private Follower follower;
    private PoseFactory poseFactory = PoseFactory.degrees();
    private Launcher launcher;
    private Intake intake;
    private Kicker kicker;

    private int pathState = 0;
    private ElapsedTime actionTimer = new ElapsedTime();

    // Poses using Pedro v3 PoseFactory
    private final Pose startPose = poseFactory.of(82, 131, -90);
    private final Pose firstFlowerPose = poseFactory.of(131, 95, 180);
    private final Pose farShootPose = poseFactory.of(84, 13, 90);
    private final Pose secondFlowerPose = poseFactory.of(89, 10, 117);
    private final Pose parkPose = poseFactory.of(131, 27, -90);

    // Pedro v3 Path objects
    private Path toFirstFlower, toFarShoot, toSecondFlower, toPark;

    public void buildPaths() {
        toFirstFlower = line(startPose, firstFlowerPose);
        toFarShoot = line(firstFlowerPose, farShootPose);
        toSecondFlower = line(farShootPose, secondFlowerPose);
        toPark = line(secondFlowerPose, parkPose);
    }

    @Override
    public void runOpMode() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);

        launcher = new Launcher(hardwareMap);
        intake = new Intake(hardwareMap);
        kicker = new Kicker(hardwareMap);

        buildPaths();

        telemetry.addData("Status", "Initialization Complete");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            setPathState(0);

            while (opModeIsActive() && !isStopRequested()) {
                follower.update();
                autonomousPathUpdate();

                // Telemetry for debugging
                telemetry.addData("Path State", pathState);
                telemetry.addData("X", follower.getPose().getX());
                telemetry.addData("Y", follower.getPose().getY());
                telemetry.update();
            }
        }
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                kicker.moveDown();
                intake.startIntake();
                launcher.startLauncher();
                follower.followPath(toFirstFlower);
                setPathState(1);
                break;

            case 1:
                if (!follower.isBusy()) {
                    if (launcher.isTargetVelocity()) {
                        kicker.moveUp();
                        actionTimer.reset();
                        setPathState(2);
                    }
                }
                break;

            case 2:
                if (actionTimer.seconds() >= 2.0) {
                    kicker.moveDown();
                    follower.followPath(toFarShoot);
                    setPathState(3);
                }
                break;

            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(toSecondFlower);
                    setPathState(4);
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    launcher.stopLauncher();
                    intake.stopIntake();
                    follower.followPath(toPark);
                    setPathState(5);
                }
                break;

            case 5:
                if (!follower.isBusy()) {
                    // Autonomous finished
                }
                break;
        }
    }

    public function setPathState(int pState) {} // placeholder safety check

    public void setPathState(int pState) {
        pathState = pState;
        actionTimer.reset();
    }
}
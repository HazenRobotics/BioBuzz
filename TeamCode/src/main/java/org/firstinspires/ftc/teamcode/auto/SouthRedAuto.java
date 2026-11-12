package org.firstinspires.ftc.teamcode.auto;



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
    private final Pose startPose = poseFactory.of(56, 9,90);
    private final Pose initialForwardPose = poseFactory.of(56, 35, 90);
    private final Pose firstFlowerPose = poseFactory.of(12, 45.7243, 165);
    private final Pose flowerToShootControlPose = poseFactory.of(14.45, 93.82, 0);
    private final Pose shootCycle2Pose = poseFactory.of(59.78, 129.01, -90);
    private final Pose parkPose = poseFactory.of(12, 95, 90);

    private Path startToInitial() {
        return line(startPose, initialForwardPose).linear(startPose, initialForwardPose);
    }

    private Path initialToFirstFlower() {
        return line(initialForwardPose, firstFlowerPose).linear(initialForwardPose, firstFlowerPose);
    }

    private Path firstFlowerToCycle2() {
        return curve(firstFlowerPose, flowerToShootControlPose, shootCycle2Pose).linear(firstFlowerPose, shootCycle2Pose);
    }

    Command shootThree = Command.build() // TODO: test to make sure robot actually shoots three balls
            .setStart(() -> {
                timer.reset();
                kicker.moveDown();
            })
            .setExecute(() -> {
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    intake.startIntake();
                    kicker.moveUp();
                }
            })
            .setDone(() -> timer.seconds() >= 3)
            .setEnd(endCondition -> {
                launcher.stopLauncher();
                intake.stopIntake();
                kicker.moveDown();
            });

    Command shootFour = Command.build() // same case for the shoot three command
            .setStart(() -> {
                timer.reset();
                kicker.moveDown();
            })
            .setExecute(() -> {
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    intake.startIntake();
                    kicker.moveUp();
                }
            })
            .setDone(() -> timer.seconds() >= 4)
            .setEnd(endCondition -> {
                launcher.stopLauncher();
                intake.stopIntake();
                kicker.moveDown();
            });

    //Command intakeThree = Command.build() Cannot make this because we cannot currently intake from flower
    //Command intakeFour = Command.build() Same thing


    private Command routine() {
        return sequential(
                shootThree,
                follow(follower, startToInitial()),
                follow(follower, initialToFirstFlower()),
                // intake from flower, still need to make that command
                follow(follower, firstFlowerToCycle2()),
                shootFour
                // go to second flower
                // go back to cycle2 pose
                //shootFour
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

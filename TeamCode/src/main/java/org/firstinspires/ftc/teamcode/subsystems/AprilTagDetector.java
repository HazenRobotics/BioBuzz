package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTagDetector {

    private static final double TAG_SIZE_INCHES = 3.25;
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> detectedTags;

    private Telemetry telemetry;

    public void init(HardwareMap HW, Telemetry telemetry) {
        this.telemetry = telemetry;

        AprilTagLibrary.Builder lib = new AprilTagLibrary.Builder();
                for (int id = 30; id <= 45; id++) {
                    lib.addTag(id, "BioBuzz" + id, TAG_SIZE_INCHES, DistanceUnit.INCH);
                }
        
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .setTagLibrary(lib.build())
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(HW.get(WebcamName.class, "camera"));
        builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();
    }

    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
        telemetry.addData("tags:", detectedTags.size());
        for (AprilTagDetection tag: detectedTags) {
            if (tag instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection single = (AprilTagSingleDetection) tag;
                telemetry.addData("tag: ", single.id);
            }
        }
    }

    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }

    public AprilTagSingleDetection getTagById(int id) {
        if (detectedTags == null) {
            return null;
        }
        for (AprilTagDetection detection : detectedTags) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection single = (AprilTagSingleDetection) detection;
                if (single.id == id) {
                    return single;
                }
            }
        }

        return null;
    }

    public void displayDetectionTelemetry(AprilTagSingleDetection detectedId) {

        if (detectedId == null) {return;}

        if (detectedId.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detectedId.ftcPose.x, detectedId.ftcPose.y, detectedId.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll, detectedId.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detectedId.ftcPose.range, detectedId.ftcPose.bearing, detectedId.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedId.center.x, detectedId.center.y));
        }
    }

    public void displaySimpleTelemetry(AprilTagSingleDetection detectedId) {

        if (detectedId == null) {return;}

        if (detectedId.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
        }
        else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
        }

    }
    public double getPosX(int id) {
        if (getTagById(id) != null) {
            return getTagById(id).ftcPose.x;
        }
        return -999999;
    }
    public double getPosY(int id) {return getTagById(id).ftcPose.y;}
    public double getPosZ(int id) {return getTagById(id).ftcPose.z;}


    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}

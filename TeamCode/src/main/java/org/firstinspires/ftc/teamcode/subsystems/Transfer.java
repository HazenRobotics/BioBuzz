package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {

    DcMotorEx transfer;

    public enum Speed {
        SLOW,
        FAST,
        REVERSE
    }

    Speed speed;

    private final double MAX_SPEED_TRANSFER = 0.9;
    private final double SLOW_SPEED_TRANSFER = 0.4;

    public Transfer(HardwareMap HW) {
        transfer = HW.get(DcMotorEx.class, "transfer");
    }


    public void stopTransfer() {
        transfer.setPower(0);
    }


    public void startTransfer(Speed speed) {
        switch (speed) {
            case FAST:
                transfer.setPower(MAX_SPEED_TRANSFER);
                break;
            case SLOW:
                transfer.setPower(SLOW_SPEED_TRANSFER);
                break;
            case REVERSE:
                transfer.setPower(-MAX_SPEED_TRANSFER);
                break;
        }
    }

    public Speed getSpeedState() {
        return speed;
    }


}

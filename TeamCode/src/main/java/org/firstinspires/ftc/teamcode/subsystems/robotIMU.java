package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class robotIMU {

    private IMU imu;

    public robotIMU(HardwareMap hardwareMap) {

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot orientation =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                );

        IMU.Parameters parameters = new IMU.Parameters(orientation);

        imu.initialize(parameters);

    }

    public double getYaw() {

        double yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        return yaw;

    }
    public double getPitch(){

        double pitch = imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);
        return  pitch;

    }
    public double getRoll(){

        double roll = imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES);
        return roll;

    }
}

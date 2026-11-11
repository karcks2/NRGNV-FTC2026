package org.firstinspires.ftc.teamcode.subsystems;

// FTC libaryd
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private DcMotor intakeMotor;
    private boolean isRunning = false;

    public void start() {

        intakeMotor.setPower(0.3);
        isRunning = true;

    }
    public void stop(){

        intakeMotor.setPower(0);
        isRunning = false;

    }

    public Intake(HardwareMap hardwareMap) {

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

    }

    public void toggle() {

        isRunning = !isRunning;

        if (isRunning){

            start();

        }
        else{

            stop();

        }

    }
}

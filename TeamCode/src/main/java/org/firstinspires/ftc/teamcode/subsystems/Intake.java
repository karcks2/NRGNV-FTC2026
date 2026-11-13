package org.firstinspires.ftc.teamcode.subsystems;

// FTC libaryd
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private DcMotor intakeMotor;
    private boolean isRunning = false;

    public void start(double power) {

        intakeMotor.setPower(power);
        isRunning = true;

    }
    public void stop(){

        intakeMotor.setPower(0);
        isRunning = false;

    }

    public Intake(HardwareMap hardwareMap) {

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

    }

    public void toggle(double power) {

        isRunning = !isRunning;

        if (isRunning == true){

            start(power);


        }
        else{

            stop();

        }

    }
    public void setPower(double power){

        if (isRunning){

            intakeMotor.setPower(power);

        }

    }
}

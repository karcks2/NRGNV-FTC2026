package org.firstinspires.ftc.teamcode.subsystems;

// FTC libaryd
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Intake {

    private DcMotor intakeMotor;
    private boolean isRunning = false;
    private boolean reversed = true;

    public void toggleDirection() {
        reversed = !reversed;
        intakeMotor.setDirection(
                reversed ? DcMotorSimple.Direction.REVERSE
                        : DcMotorSimple.Direction.FORWARD
        );
    }

    public String getDirection() {
        return reversed ? "Intake" : "Output";
    }

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
        // Muudab intake pöörlemissuunda
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

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

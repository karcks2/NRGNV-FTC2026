package org.firstinspires.ftc.teamcode.subsystems;

// FTC libaryd
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class XDrive {

        // frontRight = 0
        // frontLeft = 1
        // backRight = 2
        // backLeft = 3
        private DcMotor frontRight;
        private DcMotor frontLeft;
        private DcMotor backRight;
        private DcMotor backLeft;

        // hardware map
        public XDrive(HardwareMap hardwareMap){

                frontRight = hardwareMap.get(DcMotor.class, "frontRight");
                frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
                backRight = hardwareMap.get(DcMotor.class, "backRight");
                backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        }

        // pidurdamis function
        public void stop(){

                // koik mootorid on kinni (deafult state)
                frontRight.setPower(0);
                frontLeft.setPower(0);
                backRight.setPower(0);
                backLeft.setPower(0);

        }

        public void drive(double forward, double strafe, double turn){

                // soitmis valemid
                double flPower = -forward - strafe + turn;
                double frPower = forward - strafe + turn;
                double blPower = forward + strafe + turn;
                double brPower = -forward + strafe + turn;


                // maksimaalne joud
                double maxPower = Math.max(
                        Math.max(Math.abs(flPower),Math.abs(frPower)),
                                Math.max(Math.abs(blPower),Math.abs(brPower)));

                // standar jagaja on 1
                double divisor = Math.max(1.0, maxPower);

                // power /= divisor == power = power / divisor
                flPower /= divisor;
                frPower /= divisor;
                blPower /= divisor;
                brPower /= divisor;

                // seame mootori kiirused
                frontLeft.setPower(flPower);
                frontRight.setPower(frPower);
                backLeft.setPower(blPower);
                backRight.setPower(brPower);


        }

}

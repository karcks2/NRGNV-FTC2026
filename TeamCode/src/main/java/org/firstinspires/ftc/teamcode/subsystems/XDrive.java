package org.firstinspires.ftc.teamcode.subsystems;

// FTC libaryd
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class XDrive {

        // frontRight = 0
        // frontLeft = 1
        // backRight = 2
        // backLeft = 3
        private DcMotor frontRight;
        private DcMotor frontLeft;
        private DcMotor backRight;
        private DcMotor backLeft;

        //Acceleration Ramping
        private double previousForward = 0;
        private double previousStrafe = 0;
        private double previousTurn = 0;

        private static final double ACCELERATION_RATE = 2.0;

        private final ElapsedTime rampTimer = new ElapsedTime();

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

                previousForward = 0;
                previousStrafe = 0;
                previousTurn = 0;

                rampTimer.reset();

        }
        private double ramp(double current, double target, double maxChange) {

                double difference = target - current;

                if (Math.abs(difference) <= maxChange) {
                        return target;
                }

                return current + Math.signum(difference) * maxChange;
        }

        public void drive(double forward, double strafe, double turn){

                double deltaTime = rampTimer.seconds();
                rampTimer.reset();

                double maxChange = ACCELERATION_RATE * deltaTime;

                // Sujuv sisendite muutmine
                forward = ramp(previousForward, forward, maxChange);
                strafe = ramp(previousStrafe, strafe, maxChange);
                turn = ramp(previousTurn, turn, maxChange);

                // Salvestame uued väärtused järgmiseks tsükliks
                previousForward = forward;
                previousStrafe = strafe;
                previousTurn = turn;

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

        public void resetRamping() {

                previousForward = 0;
                previousStrafe = 0;
                previousTurn = 0;
                rampTimer.reset();

        }
}

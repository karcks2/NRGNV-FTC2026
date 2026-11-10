// Kus class asub
package org.firstinspires.ftc.teamcode.teleop;

// FTC libraryd
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

// ise loodud libary
import org.firstinspires.ftc.teamcode.subsystems.XDrive;

@TeleOp(name = "Main TeleOp")

public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        XDrive drive = new XDrive(hardwareMap);

        waitForStart();
        drive.resetRamping();

        boolean previousRightBumper = false;
        boolean previousLeftBumper = false;

        // algne speed limit
        double speedlimit = 0.5;// 0.5 limiteerib mootori kiirust 50%-ni


        // puldiga juhtimine on aktiivne
        while (opModeIsActive()){

            // Kuvatav telemeetria aka SLOP

            telemetry.addData("Status", "Robot is running");

            // Vasak stick
            telemetry.addData("Left stick Y", gamepad1.left_stick_y);
            telemetry.addData("Left stick X", gamepad1.left_stick_x);

            // Parem stick
            telemetry.addData("Right stick Y", gamepad1.right_stick_y);
            telemetry.addData("Right stick X", gamepad1.right_stick_x);

            // speed limiti muutmine
            if (gamepad1.right_bumper && !previousRightBumper){
                speedlimit += 0.1;
            }
            else if (gamepad1.left_bumper && !previousLeftBumper) {
                speedlimit -= 0.1;
            }
            // maksimaalne ja minimaalne speedlimit suurus
            speedlimit = Math.max(0.0, Math.min(1.0,speedlimit));

            double forward = -gamepad1.left_stick_y * speedlimit;
            double strafe = gamepad1.left_stick_x * speedlimit;
            double turn = -gamepad1.right_stick_x * speedlimit;

            // drive functioni ja drive object btw ma tean sen sitt
            drive.drive(forward,strafe,turn);

            boolean currentLeftBumper = gamepad1.left_bumper;
            boolean currentRightBumper = gamepad1.right_bumper;

            previousRightBumper = currentRightBumper;
            previousLeftBumper = currentLeftBumper;

            telemetry.addData("Speed limit", speedlimit);

            // alusta uut telemetry tsyklit
            telemetry.update();
        }
        drive.stop();

    }
}

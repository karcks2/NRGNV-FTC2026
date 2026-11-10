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

        double power = -gamepad1.left_stick_y;
        double steering = -gamepad1.left_stick_x;

        


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

            // nupp a ja b vajutuse kontroll
            if (gamepad1.a && gamepad1.b){

                telemetry.addData("A and B button", "Button pressed");

            } else if (gamepad1.a && !gamepad1.b) {

                telemetry.addData("A and B button","only A pressed");

            } else if (gamepad1.b && !gamepad1.a) {

                telemetry.addData("A and B button", "Only B pressed");

            } else{

                telemetry.addData("A and B button", "Released");

            }

            //
            if (gamepad1.left_bumper || gamepad1.right_bumper && !gamepad1.a){

                telemetry.addData("LEFT and RIGHT pumper", "LEFT or RIGHT pumper pressed while A button is not pressed");

            }

            // puldiga juhtimine
            double speedlimit = 0.5;// 0.5 limiteerib mootori kiirust 50%-ni

            double forward = -gamepad1.left_stick_y * speedlimit;
            double strafe = gamepad1.left_stick_x * speedlimit;
            double turn = -gamepad1.right_stick_x * speedlimit;

            // drive functioni ja drive object btw ma tean sen sitt
            drive.drive(forward,strafe,turn);

            // alusta uut telemetry tsyklit
            telemetry.update();
        }

    }
}

    // Kus class asub
    package org.firstinspires.ftc.teamcode.teleop;

    // FTC libraryd
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import org.firstinspires.ftc.teamcode.subsystems.robotIMU;
    import org.firstinspires.ftc.teamcode.subsystems.Intake;

    // ise loodud libary
    import org.firstinspires.ftc.teamcode.subsystems.XDrive;

    @TeleOp(name = "Main TeleOp")

    public class MainTeleop extends LinearOpMode {

        // Deadzone function
        private double applyDeadzone(double value) {

            if (Math.abs(value) <= 0.1){ // 10% Deadzone

                return 0;

            }
            return value;
        }

        @Override
        public void runOpMode() throws InterruptedException {

            XDrive drive = new XDrive(hardwareMap);

            robotIMU robotIMU = new robotIMU(hardwareMap);

            Intake intake = new Intake(hardwareMap);

            waitForStart();
            drive.resetRamping();

            // speed limit
            boolean previousRightBumper = false;
            boolean previousLeftBumper = false;

            // intake trigger
            boolean previousR2 = false;
            // intake speedlimit
            boolean previousCross = false;
            boolean previousCircle = false;

            // algne speed limit
            double speedlimit = 0.5;// 0.5 limiteerib mootori kiirust 50%-ni

            double intakePower = 0.5;

            // puldiga juhtimine on aktiivne
            while (opModeIsActive()){

                // Kuvatav telemeetria

                telemetry.addData("Status", "Robot is generating slop");

                // Vasak stick
                telemetry.addData("Foward", gamepad1.left_stick_y);
                telemetry.addData("Strafe", gamepad1.left_stick_x);

                // Parem stick
                telemetry.addData("Turn", gamepad1.right_stick_x);

                // speed limiti muutmine
                if (gamepad1.right_bumper && !previousRightBumper){
                    speedlimit += 0.1;
                }
                else if (gamepad1.left_bumper && !previousLeftBumper) {
                    speedlimit -= 0.1;
                }

                // maksimaalne ja minimaalne intake speedlimit suurus
                speedlimit = Math.max(0.0, Math.min(1.0,speedlimit));

                if (gamepad1.cross && !previousCross){
                    intakePower += 0.1;
                }
                else if (gamepad1.circle && !previousCircle) {
                    intakePower -= 0.1;
                }

                // maksimaalne ja minimaalne speedlimit suurus
                intakePower = Math.max(0.0, Math.min(1.0,intakePower));

                double forward = applyDeadzone(-gamepad1.left_stick_y) * speedlimit;
                double strafe = applyDeadzone(gamepad1.left_stick_x) * speedlimit;
                double turn = applyDeadzone(-gamepad1.right_stick_x) * speedlimit;

                // drive functioni ja drive object btw ma tean sen sitt
                drive.drive(forward,strafe,turn);

                // intake tirgger
                if (gamepad1.right_trigger > 0.5 && !previousR2) {

                    intake.toggle(intakePower);
                }

                boolean currentLeftBumper = gamepad1.left_bumper;
                boolean currentRightBumper = gamepad1.right_bumper;

                boolean currentR2Trigger = gamepad1.right_trigger > 0.5;

                boolean currentCross = gamepad1.cross;
                boolean currentCircle = gamepad1.circle;

                previousRightBumper = currentRightBumper;
                previousLeftBumper = currentLeftBumper;

                previousR2 = currentR2Trigger;

                previousCross = currentCross;
                previousCircle = currentCircle;

                intake.setPower(intakePower);

                telemetry.addData("Speed limit", speedlimit);

                telemetry.addData("Intake speed limit", intakePower);

                telemetry.addData("Yaw", robotIMU.getYaw());
                telemetry.addData("Pitch", robotIMU.getPitch());
                telemetry.addData("Roll", robotIMU.getRoll());

                // alusta uut telemetry tsyklit
                telemetry.update();
            }
            drive.stop();
            intake.stop();
        }
    }

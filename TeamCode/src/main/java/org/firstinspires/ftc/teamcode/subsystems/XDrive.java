package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Nelja mootoriga X-ajam.
 * Liikumissisendid on roboti suhtes ja nende muutumist silutakse ajapõhiselt.
 */
public class XDrive {

        // Iga telje sisendi maksimaalne muutus sekundis.
        // Suurem väärtus annab kiirema, väiksem pehmema reageerimise.
        private static final double ACCELERATION_RATE = 5.0;

        // Kommentaarides kavandatud pordijärjestus:
        // frontRight = 0, frontLeft = 1, backRight = 2, backLeft = 3.
        // Tegelikud pordid määratakse aktiivses riistvarakonfiguratsioonis.

        // DcMotorEx võimaldab lisaks lugeda enkoodri kiirust.
        private DcMotorEx frontRight;
        private DcMotorEx frontLeft;
        private DcMotorEx backRight;
        private DcMotorEx backLeft;

        // Eelmised silutud sisendid, millest järgmine tsükkel jätkab.
        private double previousForward = 0;
        private double previousStrafe = 0;
        private double previousTurn = 0;

        private final ElapsedTime rampTimer = new ElapsedTime();

        public XDrive(HardwareMap hardwareMap) {

                // Nimede kirjapilt peab vastama roboti konfiguratsioonile.
                frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
                frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
                backRight = hardwareMap.get(DcMotorEx.class, "backRight");
                backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

                // Lülita iga mootori jaoks sisse hubi enkoodripõhine kiiruseregulatsioon.
                configureEncoderMotor(frontRight);
                configureEncoderMotor(frontLeft);
                configureEncoderMotor(backRight);
                configureEncoderMotor(backLeft);
        }

        public void drive(double forward, double strafe, double turn) {

                // Mõõda eelmisest kutsest möödunud aeg.
                // Nii sõltub silumine ajast, mitte juhtimistsüklite arvust.
                double deltaTime = rampTimer.seconds();
                rampTimer.reset();

                double maxChange = ACCELERATION_RATE * deltaTime;

                // Silu iga telge eraldi, ka aeglustamisel ja suuna vahetamisel.
                forward = ramp(previousForward, forward, maxChange);
                strafe = ramp(previousStrafe, strafe, maxChange);
                turn = ramp(previousTurn, turn, maxChange);

                previousForward = forward;
                previousStrafe = strafe;
                previousTurn = turn;

                // Kombineeri edasi-, külg- ja pöördliikumine iga ratta väljundiks.
                // Märgid peavad vastama mootorite ja rataste tegelikule paigutusele.
                double flPower = -forward - strafe + turn;
                double frPower = forward - strafe + turn;
                double blPower = forward + strafe + turn;
                double brPower = -forward + strafe + turn;

                // Leia suurim absoluutne mootorivõimsus.
                double maxPower = Math.max(
                        Math.max(Math.abs(flPower), Math.abs(frPower)),
                        Math.max(Math.abs(blPower), Math.abs(brPower))
                );

                // Kui kõik väljundid on juba vahemikus -1...1, jääb jagajaks 1.
                double divisor = Math.max(1.0, maxPower);

                // Vajaduse korral vähenda kõiki väljundeid sama teguriga.
                // Nii säilivad rataste võimsuste omavahelised suhted.
                flPower /= divisor;
                frPower /= divisor;
                blPower /= divisor;
                brPower /= divisor;

                // Määra mootorivõimsused. Mõõdetud kiiruse tagasisidet siin ei kasutata.
                // Täpsustus pärast enkoodrite lisamist: Java koodis eraldi regulaatorit
                // pole, kuid RUN_USING_ENCODER režiimis kasutab hub kiiruse tagasisidet.
                // setPower määrab siis soovitud kiiruse osa konfiguratsioonis
                // määratud maksimaalsest kiirusest.
                frontLeft.setPower(flPower);
                frontRight.setPower(frPower);
                backLeft.setPower(blPower);
                backRight.setPower(brPower);
        }

        public void stop() {

                // Peata väljundid kohe, sujuvustöötlust läbimata.
                // Nullvõimsus ei määra iseenesest BRAKE-käitumist.
                frontRight.setPower(0);
                frontLeft.setPower(0);
                backRight.setPower(0);
                backLeft.setPower(0);

                previousForward = 0;
                previousStrafe = 0;
                previousTurn = 0;

                rampTimer.reset();
        }

        // Alusta silumist nullist ja uuest ajahetkest, näiteks pärast START-i.
        public void resetRamping() {
                previousForward = 0;
                previousStrafe = 0;
                previousTurn = 0;
                rampTimer.reset();
        }

        // Lisa enkoodrite asendid ja mõõdetud kiirused telemeetriale.
        // ticks tähendab enkoodri loendusi, ticks/s loendusi sekundis.
        // telemetry.update() jääb MainTeleop klassi.
        public void addEncoderTelemetry(Telemetry telemetry) {
                telemetry.addData(
                        "FL encoder",
                        "%d ticks | %.0f ticks/s",
                        frontLeft.getCurrentPosition(),
                        frontLeft.getVelocity()
                );

                telemetry.addData(
                        "FR encoder",
                        "%d ticks | %.0f ticks/s",
                        frontRight.getCurrentPosition(),
                        frontRight.getVelocity()
                );

                telemetry.addData(
                        "BL encoder",
                        "%d ticks | %.0f ticks/s",
                        backLeft.getCurrentPosition(),
                        backLeft.getVelocity()
                );

                telemetry.addData(
                        "BR encoder",
                        "%d ticks | %.0f ticks/s",
                        backRight.getCurrentPosition(),
                        backRight.getVelocity()
                );
        }

        // Nulli väljund enne töörežiimi muutmist.
        // Enkoodri asendit pole kiiruseregulatsiooni jaoks vaja nullida.
        private void configureEncoderMotor(DcMotorEx motor) {
                motor.setPower(0);
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        // Liigu sihtväärtuse poole kuni maxChange võrra, sihist üle minemata.
        private double ramp(double current, double target, double maxChange) {
                double difference = target - current;

                if (Math.abs(difference) <= maxChange) {
                        return target;
                }

                // signum valib muutuse suuna: positiivne või negatiivne.
                return current + Math.signum(difference) * maxChange;
        }
}
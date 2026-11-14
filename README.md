# NRGNV FTC 2026

NRGNV FTC roboti juhtimistarkvara, mis põhineb **FIRST Tech Challenge SDK-l 12.0.0**. Projekt on kirjutatud Javas ja mõeldud arendamiseks Android Studios.

Robot kasutab nelja mootoriga X-ajamit, ühe mootoriga intake’i ning REV Hubi IMU-d.

## Võimalused

- Edasi-tagasi liikumine, külgliikumine ja pööramine.
- Sõidusisendite sujuv muutmine ehk acceleration ramping.
- Juhtkangide 10% surnud tsoon.
- Sõidu võimsusteguri muutmine puldilt.
- Intake’i sisse-välja lülitamine ning võimsuse reguleerimine.
- IMU yaw-, pitch- ja roll-nurkade kuvamine Driver Stationis.

Praegu sisaldab meeskonna kood ühte juhtimisrežiimi: **Main TeleOp**.

Autonoomset OpMode’i veel pole. Sõit toimub roboti suhtes; IMU näite kasutatakse praegu telemeetria jaoks.

## Riistvara

Kood eeldab järgmisi seadmeid:

| Seadme nimi konfiguratsioonis | Tüüp | Ülesanne |
| --- | --- | --- |
| `frontRight` | DC-mootor | Eesmine parem sõidumootor |
| `frontLeft` | DC-mootor | Eesmine vasak sõidumootor |
| `backRight` | DC-mootor | Tagumine parem sõidumootor |
| `backLeft` | DC-mootor | Tagumine vasak sõidumootor |
| `intakeMotor` | DC-mootor | Intake’i mootor |
| `imu` | FTC IMU-liidesega ühilduv IMU | Roboti orientatsiooni mõõtmine |

Seadmenimed peavad FTC konfiguratsioonis vastama tabelile täpselt, sealhulgas suur- ja väiketähed.

Lisaks on vaja:

- FTC-ga ühilduvat Robot Controllerit;
- Driver Stationit;
- ühte FTC-ga ühilduvat mängupulti;
- akut, juhtmestikku ja piisavalt mootorikontrolleri väljundeid.

Projekt kasutab kokku **viit DC-mootorit**. Ühel REV Control Hubil on neli mootoriväljundit, seega on kõigi mootorite ühendamiseks vaja lisaks näiteks REV Expansion Hubi.

### Mootorite ühendamine

`XDrive.java` kommentaarides on sõidumootoritele märgitud järgmine portide järjestus:

| Port | Mootor |
| --- | --- |
| 0 | `frontRight` |
| 1 | `frontLeft` |
| 2 | `backRight` |
| 3 | `backLeft` |

Kood leiab mootorid nime järgi. Konfiguratsioonis peab iga nimi olema seotud tegeliku õige pordiga. Intake’i hub ja port tuleb määrata vastavalt juhtmestikule.

### IMU paigaldussuund

Praegune `robotIMU.java` eeldab järgmist hubi paigutust:

- REV logo on suunatud üles: `LogoFacingDirection.UP`;
- USB-pesade külg on suunatud roboti ette: `UsbFacingDirection.FORWARD`.

Kui hub on robotile paigaldatud teisiti, muuda need väärtused tegelikule paigutusele vastavaks.

## Arenduskeskkond

| Komponent | Projekti seadistus |
| --- | --- |
| Keel | Java |
| Java lähtekoodi tase | Java 8 |
| FTC SDK | 12.0.0 |
| Android Gradle Plugin | 8.13.2 |
| Gradle Wrapper | 9.1.0 |
| Android compile SDK | 30 |
| Android min SDK | 24 |
| Android target SDK | 28 |
| NDK seadistus | 21.3.6528147 |

Kasuta Android Studiot, mis toetab projektis määratud Android Gradle Pluginat. Gradle’i käivitamiseks sobib JDK 17.

Java 8 lähtekoodi tase ja Gradle’i käivitamiseks kasutatav JDK on eraldi seadistused.

## Projekti allalaadimine

Gitiga:

```bash
git clone https://github.com/karcks2/NRGNV-FTC2026.git
cd NRGNV-FTC2026
```

Alternatiivina vali GitHubis **Code → Download ZIP** ning paki projekt lahti.

## Android Studio seadistamine

1. Ava Android Studio.
2. Vali **Open**.
3. Ava projekti juurkaust, kus asuvad `settings.gradle` ja `gradlew`.
4. Oota Gradle Synci lõppemist.
5. Määra Gradle JDK-ks JDK 17 või muu projekti tööriistaversioonidega ühilduv JDK.
6. Paigalda SDK Managerist **Android SDK Platform 30** ja **Android SDK Platform-Tools**.
7. Kui ehitus nõuab NDK-d, paigalda projektis määratud versioon.
8. Lase Android Studiol luua kohaliku SDK asukohta sisaldav `local.properties`.

Esimene sünkroonimine vajab sõltuvuste allalaadimiseks internetti.

Kasuta projektiga kaasas olevat Gradle Wrapperit. Enne Gradle’i, Android Gradle Plugina või FTC SDK uuendamist kontrolli versioonide omavahelist ühilduvust.

## Rakenduse ehitamine

Android Studios vali rakendusmooduliks **TeamCode**.

Terminalist saab silumisrakenduse ehitada järgmiselt.

Linux ja macOS:

```bash
./gradlew :TeamCode:assembleDebug
```

Windows PowerShell:

```powershell
.\gradlew.bat :TeamCode:assembleDebug
```

Valmis APK asub tavaliselt siin:

```text
TeamCode/build/outputs/apk/debug/TeamCode-debug.apk
```

## Robot Controllerile paigaldamine

1. Lülita Robot Controller sisse.
2. Ühenda kontroller arvutiga sobiva USB-andmekaabli kaudu.
3. Vajaduse korral luba USB debugging ja kinnita arvuti silumisõigus.
4. Vali Android Studios sihtseadmeks roboti kontroller.
5. Käivita **TeamCode** moodul nupuga **Run**.
6. Kontrolli, et Robot Controlleri rakendus käivitub.
7. Ühenda Driver Station robotiga.

Paigaldamine uuendab Robot Controlleri rakendust. Driver Stationi rakendust tuleb paigaldada või uuendada eraldi.

Robot Controlleri ja Driver Stationi versioonid peavad olema omavahel ühilduvad.

## Roboti käivitamine

1. Kontrolli juhtmestikku ja aku ühendust.
2. Loo või ava roboti riistvarakonfiguratsioon.
3. Lisa mootorid õigete tüüpide ja selles README-s toodud nimedega.
4. Kontrolli, et IMU nimi on `imu`.
5. Salvesta ja aktiveeri konfiguratsioon.
6. Ühenda mängupult Driver Stationiga ning määra see esimeseks puldiks ehk `gamepad1`.
7. Vali TeleOp-loendist **Main TeleOp**.
8. Vajuta **INIT**.
9. Vajuta **START**.
10. Lõpetamiseks vajuta **STOP**.

Esimesel katsetusel tõsta rattad maast lahti ja kontrolli väikese võimsusega eraldi sõidusuundi ning intake’i tööd.

## Juhtnupud

Kõik käsud kasutavad esimest pulti: `gamepad1`.

| Juhtnupp | Tegevus |
| --- | --- |
| Vasak kang üles / alla | Edasi-tagasi liikumine |
| Vasak kang vasakule / paremale | Külgliikumine |
| Parem kang vasakule / paremale | Pööramine |
| Parem õlanupp — R1 / RB | Suurendab sõidu võimsustegurit 0.1 võrra |
| Vasak õlanupp — L1 / LB | Vähendab sõidu võimsustegurit 0.1 võrra |
| Parem päästik — R2 / RT | Lülitab intake’i sisse või välja |
| Rist — Cross / A | Suurendab intake’i võimsust 0.1 võrra |
| Ring — Circle / B | Vähendab intake’i võimsust 0.1 võrra |

Cross/Circle ja A/B vastavus sõltub puldist ning FTC SDK nupuvastendusest.

### Algväärtused

| Seadistus | Algväärtus | Vahemik |
| --- | --- | --- |
| Sõidu võimsustegur | 0.5 | 0.0–1.0 |
| Intake’i võimsus | 0.5 | 0.0–1.0 |
| Intake’i olek | Välja lülitatud | Sees / väljas |
| Juhtkangi surnud tsoon | 0.1 | — |

Õlanuppude ning Cross/Circle’i puhul muudab üks vajutus väärtust ühe sammu võrra. Nupu hoidmine väärtust pidevalt ei muuda.

Intake’i lülitamiseks tuleb parem päästik vajutada üle poole. Uuesti lülitamiseks vabasta päästik vähemalt poole peale ja vajuta uuesti.

Intake’i võimsuse muutmine jõustub ka töötamise ajal. Võimsus `0.0` peatab mootori, kuid ei muuda intake’i sisemist sisselülitatud olekut.

### Sõidu võimsustegur

Koodis nimetatakse sõidu võimsustegurit `speedlimit`.

See korrutab edasi-, külg- ja pööramissisendit enne nende kombineerimist. Väärtus `0.5` ei taga, et iga mootori lõplik võimsus jääks kõigi liikumiskombinatsioonide korral 50% piiresse.

Lõplikud mootoriväljundid normaliseeritakse vahemikku `-1.0–1.0`.

## Telemeetria

Driver Stationis kuvatakse:

| Väli | Tähendus |
| --- | --- |
| `Status` | Koodis määratud olekutekst |
| `Foward` | Vasaku kangi töötlemata Y-sisend |
| `Strafe` | Vasaku kangi töötlemata X-sisend |
| `Turn` | Parema kangi töötlemata X-sisend |
| `Speed limit` | Sõidu võimsustegur |
| `Intake speed limit` | Intake’i seadistatud võimsus |
| `Yaw` | Suunanurk kraadides |
| `Pitch` | Ette-taha kaldenurk kraadides |
| `Roll` | Külgkaldenurk kraadides |

`Foward` kirjapilt vastab praegusele koodile.

Kangide näidud on toorsisendid enne surnud tsooni, märgimuutust, võimsustegurit ja sujuvustöötlust. Intake’i võimsuse näit ei kuva eraldi selle sisse-välja olekut.

## Projekti struktuur

```text
NRGNV-FTC2026/
├── TeamCode/
│   ├── build.gradle
│   └── src/main/
│       ├── java/org/firstinspires/ftc/teamcode/
│       │   ├── subsystems/
│       │   │   ├── XDrive.java
│       │   │   ├── Intake.java
│       │   │   └── robotIMU.java
│       │   └── teleop/
│       │       └── MainTeleop.java
│       └── res/
├── FtcRobotController/
├── gradle/wrapper/
├── libs/
├── doc/
├── build.gradle
├── build.common.gradle
├── build.dependencies.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── LICENSE
```

Meeskonna roboti kood asub kaustas:

```text
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/
```

### MainTeleop.java

Peamine juhitav OpMode.

- Loob ajami, intake’i ja IMU objektid.
- Ootab START-käsku.
- Loeb mängupulti.
- Töötleb nupuvajutusi ja juhtkangide sisendeid.
- Juhib mootoreid ning uuendab telemeetriat.
- Juhtimistsükli tavalisel lõppemisel peatab ajami ja intake’i.

### XDrive.java

Juhib nelja sõidumootorit.

Mootorivõimsused arvutatakse järgmiselt:

```text
frontLeft  = -forward - strafe + turn
frontRight =  forward - strafe + turn
backLeft   =  forward + strafe + turn
backRight  = -forward + strafe + turn
```

Enne arvutamist silutakse iga liikumistelje sisendit. Muutumiskiiruse määrab:

```java
private static final double ACCELERATION_RATE = 5.0;
```

Väiksem väärtus muudab reageerimise aeglasemaks ja sujuvamaks, suurem väärtus kiiremaks.

Kui arvutatud mootorivõimsuse absoluutväärtus ületab ühe, jagatakse kõik neli väljundit sama teguriga.

`stop()` määrab mootorivõimsused nulliks. Kood ei seadista eraldi `ZeroPowerBehavior.BRAKE` käitumist ega suletud ahelaga kiirusejuhtimist.

### Intake.java

Juhib mootorit `intakeMotor`.

| Meetod | Ülesanne |
| --- | --- |
| `start(power)` | Käivitab intake’i |
| `stop()` | Peatab intake’i |
| `toggle(power)` | Vahetab sisse-välja olekut |
| `setPower(power)` | Uuendab töötava intake’i võimsust |

Praegune TeleOp kasutab intake’i jaoks ainult mittenegatiivset võimsust.

### robotIMU.java

Algseadistab IMU ja annab orientatsiooninurgad kraadides:

- `getYaw()`
- `getPitch()`
- `getRoll()`

Praeguses koodis pole puldilt yaw lähtestamist ega IMU järgi sõidusuuna korrigeerimist.

## Seadistuste muutmine

| Muudetav omadus | Asukoht |
| --- | --- |
| Juhtkangide surnud tsoon | `MainTeleop.java` → `applyDeadzone()` |
| Sõidu algne võimsustegur | `MainTeleop.java` → `speedlimit` |
| Intake’i algne võimsus | `MainTeleop.java` → `intakePower` |
| Võimsuse muutmise samm | `MainTeleop.java` → nupuvajutuste tingimused |
| Intake’i päästiku lävi | `MainTeleop.java` → `right_trigger > 0.5` |
| Sõidusisendite muutumiskiirus | `XDrive.java` → `ACCELERATION_RATE` |
| Mootorite konfiguratsiooninimed | `XDrive.java` ja `Intake.java` |
| IMU nimi ja paigaldussuund | `robotIMU.java` |

## Uue OpMode’i lisamine

1. Loo meeskonna paketti uus Java klass.
2. Kasuta baasklassina `LinearOpMode` või `OpMode`.
3. Lisa `@TeleOp` või `@Autonomous` annotatsioon.
4. Anna režiimile eristatav nimi.
5. Kasuta vajaduse korral olemasolevaid alamsüsteeme.
6. Ehita ja paigalda rakendus uuesti.
7. Kontrolli uue režiimi ilmumist Driver Stationis.

FTC SDK näidised asuvad siin:

```text
FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/
```

Näidisfailide olemasolu ei tähenda, et nende funktsioonid oleksid meeskonna robotis kasutusele võetud.

## Tõrkeotsing

| Probleem | Kontroll |
| --- | --- |
| `Main TeleOp` puudub | Paigalda uusim `TeamCode` ehitus ja kontrolli ühendust õige robotiga. |
| INIT annab seadme leidmise vea | Kontrolli aktiivset konfiguratsiooni, seadme tüüpi ja täpset nime. |
| Robot ei reageeri puldile | Kontrolli gamepad1 määramist, START-olekut ja sõidu võimsustegurit. |
| Robot liigub valesti | Kontrolli mootorite nimesid, ühendusi, suundi ja rataste paigutust. |
| Intake ei käivitu | Kontrolli ühendust, intake’i võimsust ja päästiku üle poole vajutamist. |
| Intake ei lülitu uuesti | Vabasta päästik vähemalt poole peale ja vajuta uuesti. |
| IMU näidud on valed | Kontrolli hubi tegelikku paigaldussuunda ja IMU seadistust. |
| Gradle Sync ebaõnnestub | Kontrolli internetti, Gradle JDK-d ja veateates nimetatud sõltuvusi. |
| Android Studio ei näe kontrollerit | Kontrolli USB-andmekaablit, silumisõigust ja Platform-Toolsi. |
| `gradlew` annab õiguste vea | Linuxis/macOS-is käivita `chmod +x gradlew`. |

## GitHubi töövoog

Tee muudatused eraldi harus:

```bash
git switch -c feature/juhtimise-muudatus
```

Pärast muudatuste kontrollimist:

```bash
git add TeamCode/src/main/java/org/firstinspires/ftc/teamcode/
git commit -m "Täienda roboti juhtimist"
git push -u origin feature/juhtimise-muudatus
```

Ava GitHubis pull request ning kirjelda muudatust ja tehtud kontrolle.

Edukas ehitus kontrollib kompileerimist. Sõidusuundi ja mehhanismide toimimist tuleb kontrollida robotil.

### Failide hoidmine

Hoia repositooriumis alles:

- meeskonna lähtekood ja vajalikud ressursid;
- `FtcRobotController` moodul;
- Gradle’i seadistusfailid ja Wrapper;
- ehituseks vajalikud teegid ja failid;
- `LICENSE` ning vajalikud autoriõiguse teated.

Praegune ehitus kasutab ka faili `libs/ftc.debug.keystore`.

Genereeritud `build/` ja `.gradle/` kaustu ning arvutipõhist `local.properties` faili ei ole vaja GitHubi lisada. Ignoreerimisreeglid asuvad failis [`.gitignore`](.gitignore).

## Kasulikud lingid

- [Projekti GitHub](https://github.com/karcks2/NRGNV-FTC2026)
- [FTC dokumentatsioon](https://ftc-docs.firstinspires.org/)
- [FTC Robot Controller SDK](https://github.com/FIRST-Tech-Challenge/FtcRobotController)
- [REV Robotics dokumentatsioon](https://docs.revrobotics.com/)

## Litsents

Projekt põhineb FIRST Tech Challenge SDK-l.

Kasutamisel ja levitamisel järgi repositooriumi [LICENSE](LICENSE) faili, lähtekoodi autoriõiguse teateid ning sõltuvuste litsentsitingimusi.

Täiendav SDK õigusteave asub kaustas [`doc/legal`](doc/legal/).

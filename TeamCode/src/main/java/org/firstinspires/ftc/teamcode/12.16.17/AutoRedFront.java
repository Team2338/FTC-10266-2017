/**
 * Created by Mach Speed Programming on 10/31/2017.
 */

// AUTO RED FRONT   AUTONOMOUS BY TIME WITH COLOR, DISTANCE and TOUCH CODE

package org.firstinspires.ftc.teamcode;// AUTONOMOUS BY TIME WITH COLOR, DISTANCE and TOUCH CODE

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.Locale;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "AutoRedFront", group = "Sensor")
//@Disabled                            // Comment this out to add to the opmode list

public class AutoRedFront extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor backleftmotor;
    DcMotor backrightmotor;

    DcMotor liftmotor;

    Servo servo0;  //Grabber
    Servo servo1;  //Grabber
    Servo servo2;  //Sensor Arm

    DigitalChannel digitaltouch;  // Hardware Device Object

    ColorSensor sensorcolor;
    DistanceSensor sensordistance;

    Boolean Isblue;

    int ColorBlue = 0;

    //START AND END PARMS
    double StartTime    = 0.0;
    double EndTime      = 0.0;

    //BELOW VALUES IN SECONDS
    double CloseArms       = 1.0;    //Step 1
    double DropColorArm    = 2.0;    //Step 2
    double ReadColor       = 2.0;    //Step 3
    double KnockOffBall    = 0.1;   //Was .38
    double ResetMove       = 0.1;
    double Stop            = 2.0;    //Multiple Steps
    double RaiseColorArm   = 2.0;    //
    double MoveOffBoard    = 0.45;    //Was .9
    double Spin            = 0.8;    //Was .8
    double MoveToWall      = 0.3;    //
    double DropGlyph       = 1.0;    //
    double BackUp          = 0.5;    //was .3
    double PushGlyph       = 0.5;   // was .3
    double BackUpFinal     = 0.05;    //

    //
    // SERVO CODE
    //
    // LOWER COLOR ARM
    // .75 is about perfect position lowering color arm
    // .55 is probably too high off the ground
    static final double MAX_POS     =  .9;     // Maximum rotational position LOWER .75

    // RAISE COLOR ARM
    // .2 is about perfect position for raising color arm
    // .1 raises it too much
    // .3 arm ends up way too low in the raised position
    static final double MIN_POS     =  0.2;     // Minimum rotational position RAISE 0.2

    double  positionx = (MAX_POS - MIN_POS) / 2;

    @Override
    public void runOpMode() {


        backleftmotor = hardwareMap.dcMotor.get("backleftdrive");
        backrightmotor = hardwareMap.dcMotor.get("backrightdrive");

        // get a reference to our digitalTouch object.

        digitaltouch = hardwareMap.get(DigitalChannel.class, "sensordigital");
        // set the digital channel to input.
        digitaltouch.setMode(DigitalChannel.Mode.INPUT);

        servo0 = hardwareMap.servo.get("servo0");
        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");
        liftmotor = hardwareMap.dcMotor.get("liftmotor");
        // get a reference to the color sensor.
        sensorcolor = hardwareMap.get(ColorSensor.class, "sensorcolordistance");
        // get a reference to the distance sensor that shares the same name.
        sensordistance = hardwareMap.get(DistanceSensor.class, "sensorcolordistance");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // wait for the start button to be pressed
        waitForStart();


        // LOOP LOOP LOOP
        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.

        runtime.reset();

        while (opModeIsActive()) {

            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
            Color.RGBToHSV((int) (sensorcolor.red() * SCALE_FACTOR),
                    (int) (sensorcolor.green() * SCALE_FACTOR),
                    (int) (sensorcolor.blue() * SCALE_FACTOR),
                    hsvValues);

            // Send the info back to driver station using telemetry function.
            telemetry.addData("Motor Output ", "Back Left" + backleftmotor.getPower());
            telemetry.addData("Motor Output ", "Back Right" + backrightmotor.getPower());
            //telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", sensordistance.getDistance(DistanceUnit.CM)));
            //telemetry.addData("Hue", hsvValues[0]);

            //
            //RESET RUNTIME
            //
            StartTime = 0;
            EndTime = StartTime + CloseArms;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Start Reset ", "1");
                telemetry.addData("StartTime   ", StartTime);
                telemetry.addData("EndTime     ", EndTime);

                servo0.setPosition(1);
                servo1.setPosition(0); 	//Clamp Glyph

            }

            //
            //DROP COLOR ARM
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + DropColorArm;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Drop Color  ", "2");
                telemetry.addData("StartTime   ", StartTime);
                telemetry.addData("EndTime     ", EndTime);

                positionx = MAX_POS;   //DROP ARM
                servo2.setPosition(positionx);

                liftmotor.setPower(.225);

        }

            //
            //READ COLOR
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + ReadColor;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Read Color ", "3");
                telemetry.addData("StartTime   ", StartTime);
                telemetry.addData("EndTime     ", EndTime);
                liftmotor.setPower(0);
                // ASSUME WE ARE RED TEAM
                // ASSUME SENSOR FACING BACKWARDS ON ROBOT ON RIGHT SIDE
                // CHECK IF STATEMENT FOR BLUE ONLY
                if (hsvValues[0] >= 150 && hsvValues[0] <= 250) {
                    //add variable
                    ColorBlue = 1;
                    telemetry.addData("In BLUE, If TRUE ", sensorcolor.blue());
                    telemetry.addData("Hue", hsvValues[0]);

                } else {
                    telemetry.addData("In RED, Else FALSE ", sensorcolor.red());
                    telemetry.addData("Hue", hsvValues[0]);
                    ColorBlue = 0;
                }
            }

            //
            // MOVE TO KNOCK OFF BALL
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + KnockOffBall;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Knock Off Ball ", "4");
                telemetry.addData("StartTime   ", StartTime);
                telemetry.addData("EndTime     ", EndTime);

                if (ColorBlue == 1) {
                    telemetry.addData("Knock Off Ball, Backward TRUE ", "Running: " + runtime.toString());
                    backleftmotor.setPower(-.2);   //Move Back
                    backrightmotor.setPower(-.2);
                    //liftmotor.setPower(1);
                } else {
                    telemetry.addData("Knock off Ball, Forward FALSE ", "Running: " + runtime.toString());
                    backleftmotor.setPower(.2);   //Move Forward
                    backrightmotor.setPower(.2);
                    //servo0.setPosition(0);
                    //servo1.setPosition(1);

                    //liftmotor.setPower(1);
                    // finish red
                }
            }
             //
            //STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("STOP After Knock ", "5");
                telemetry.addData("StartTime   ", StartTime);
                telemetry.addData("EndTime     ", EndTime);

                backleftmotor.setPower(0);     //Stop
                backrightmotor.setPower(0);     //Stop
            }
            //
            //RAISE COLOR ARM
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + RaiseColorArm;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Raise Color ", "6");
                telemetry.addData("StartTime   ", StartTime);
                telemetry.addData("EndTime     ", EndTime);

                positionx = MIN_POS;   //RAISE ARM
                servo2.setPosition(positionx);
            }
            //
            //RESET
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + ResetMove;

            if(runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                if (ColorBlue == 1) {
                    telemetry.addData("Knock Off Ball, Backward TRUE ", "Running: " + runtime.toString());
                    backleftmotor.setPower(.2);   //Move Back
                    backrightmotor.setPower(.2);
                    //liftmotor.setPower(1);
                } else {
                    telemetry.addData("Knock off Ball, Forward FALSE ", "Running: " + runtime.toString());
                    backleftmotor.setPower(-.2);   //Move Forward
                    backrightmotor.setPower(-.2);
                }
            }
            //
            //STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("STOP After Knock ", "5");
                telemetry.addData("StartTime   ", StartTime);
                telemetry.addData("EndTime     ", EndTime);

                backleftmotor.setPower(0);     //Stop
                backrightmotor.setPower(0);     //Stop
            }


            //
            //MOVE OFF BOARD, FORWARD
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + MoveOffBoard;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Move Off Board ", "7");
                telemetry.addData("StartTime     ", StartTime);
                telemetry.addData("EndTime       ", EndTime);

                if (ColorBlue == 1 ) {
                    telemetry.addData("Move Off Board Forward, BLUE ", "Running: " + runtime.toString());
                    backleftmotor.setPower(-.4);     //Forward
                    backrightmotor.setPower(.4);     //Forward
                    //servo0.setPosition(1);
                    //servo1.setPosition(0);
                }
                else {
                    telemetry.addData("Move Off Board, Forward RED ", "Running: " + runtime.toString());
                    backleftmotor.setPower(-.4);     //Forward
                    backrightmotor.setPower(.4);     //Forward
                    //servo0.setPosition(1);
                    //servo1.setPosition(0);
                }
            }

            //
            //STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("STOP After Knock ", "8");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(0);     //Stop
                backrightmotor.setPower(0);     //Stop
            }

            //
            //SPIN, Turn Right
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Spin;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("SPIN Right      ", "9");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(-.5);   // Turn Right
                backrightmotor.setPower(-.5);  // Turn Right
            }

            //
            //STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("STOP          ", "10");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(0);      //Stop
                backrightmotor.setPower(0);     //Stop
            }

            //
            //MOVE TO WALL
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + MoveToWall;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Move to Wall  ", "11");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(-.5);     //Forward
                backrightmotor.setPower(.5);     //Forward
            }

            //
            //STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("STOP          ", "12");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(0);	//Stop
                backrightmotor.setPower(0);   	//Stop
            }

            //
            //DROP GLYPH
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + DropGlyph;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Drop Glyph    ", "13");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                servo0.setPosition(0); 		//Drop Glyph
                servo1.setPosition(1); 		//Drop Glyph
            }

            //
            //BACK UP AFTER DROP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + BackUp;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Back Up After Drop ", "14");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(.4);  	//Backward
                backrightmotor.setPower(-.4);   //Backward
                liftmotor.setPower(-.225);
            }

            //
            //STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("STOP AND CLOSE  ", "15");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(0);     //Stop
                backrightmotor.setPower(0);    //Stop

                //servo0.setPosition(1); 		//Close Glyph
                //servo1.setPosition(0); 		//Close Glyph
            }

            //
            //PUSH GLYPH INTO BOX
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + PushGlyph;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Push Glyph    ", "16");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(-.5);     //Forward
                backrightmotor.setPower(.5);     //Forward
            }

            //
            //STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("STOP After Push ", "17");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(0);     //Stop
                backrightmotor.setPower(0);    //Stop
            }

            //
            //BACK UP AFTER GLYPH PUSH
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + BackUpFinal;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("Back Up Final   ", "18");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(.4);     //Backward
                backrightmotor.setPower(-.4);	//Backward
            }

            //
            //FINAL STOP
            //
            StartTime = EndTime + .1;
            EndTime = StartTime + Stop;

            if (runtime.seconds() >= StartTime && runtime.seconds() <= EndTime) {
                telemetry.addData("FINAL STOP    ", "19");
                //telemetry.addData("StartTime     ", StartTime);
                //telemetry.addData("EndTime       ", EndTime);

                backleftmotor.setPower(0);	//Stop
                backrightmotor.setPower(0);	//Stop
            }

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.

            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();		//SEND TELEMETRY TO PHONE
        }

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}

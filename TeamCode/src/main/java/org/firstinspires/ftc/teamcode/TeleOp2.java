/**
 * Created by Mach Speed Programming on 10/31/2017.
 */
//
//TELEOP WITH CODE FOR COLOR AND DISTANCE AS WELL AS TOUCH SENSOR
//
package org.firstinspires.ftc.teamcode;
//import com.ftdi.j2xx.D2xxManager;
//import com.qualcomm.robotcore.robocol.PeerApp;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;  //Needed for Color Sensor


@TeleOp(name="TeleOp2", group="Iterative OpMode")  // @Autonomous(...) is the other common choice
//@Disabled

public class TeleOp2 extends OpMode {
    /* Declare OpMode members. */

    private ElapsedTime runtime = new ElapsedTime();

    //DcMotor frontleftmotor;
    //DcMotor frontrightmotor;
    DcMotor backleftmotor;
    DcMotor backrightmotor;

    DcMotor liftmotor;

    Servo servo0;
    Servo servo1;
    Servo servo2;

    DigitalChannel digitaltouch;  // Hardware Device Object

    //ColorSensor sensorcolor;
    //DistanceSensor sensordistance;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        //frontleftmotor = hardwareMap.dcMotor.get("frontleftdrive");
        //frontrightmotor = hardwareMap.dcMotor.get("frontrightdrive");
        backleftmotor = hardwareMap.dcMotor.get("backleftdrive");
        backrightmotor = hardwareMap.dcMotor.get("backrightdrive");

        liftmotor = hardwareMap.dcMotor.get("liftmotor");

        servo0 = hardwareMap.servo.get("servo0"); //LGrab
        servo1 = hardwareMap.servo.get("servo1"); //RGrab
        servo2 = hardwareMap.servo.get("servo2"); //ColorArm

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery

        // frontLeftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        // frontRightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        // backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        // backRightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        // servo2.setPosition(0);

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        //telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.addData("Front Left Ticks:", backleftmotor.getCurrentPosition());
        telemetry.addData("Front Right Ticks", backrightmotor.getCurrentPosition());
        telemetry.addData("Motor Output", "Front Left" + backleftmotor.getPower());
        telemetry.addData("Motor Output", "Front Right" + backrightmotor.getPower());

        // get a reference to our digitalTouch object.
        digitaltouch = hardwareMap.get(DigitalChannel.class, "sensordigital");

        // set the digital channel to input.
        digitaltouch.setMode(DigitalChannel.Mode.INPUT);


        // GAMBEPAD1, RAISE COLOR ARM IF NECESSARY
        if (gamepad1.y) {
            servo2.setPosition(0);
        }


        // GAMEPAD2, FRONT GLYPH GRABBER
        if (gamepad2.x) {
            servo0.setPosition(1);
            servo1.setPosition(0);
        }
        if (gamepad2.b) {
            servo0.setPosition(0);
            servo1.setPosition(1);
        }

        // GAMEPAD2, LIFT MOTOR Y AND A FOR FINER CONTROL
        if (gamepad2.y) {
            liftmotor.setPower(.7);
        } else if (gamepad2.a) {
            liftmotor.setPower(-.7);
        } else {
            liftmotor.setPower(0);
        }


        // UPPER LIMIT TOUCH SWITCH
        if (digitaltouch.getState() == true) {
            telemetry.addData("Digital Touch", "Is Not Pressed");
            telemetry.addData("Digital Touch", digitaltouch.getState());
            liftmotor.setPower(-gamepad2.left_stick_y);
        } else {
            telemetry.addData("Digital Touch", "Is Pressed");
            liftmotor.setPower(-.2);
        }

        // GAMEPAD1, ROBOT SPEED CONTROL
        if (gamepad1.left_bumper) {
            backleftmotor.setPower(gamepad1.left_stick_y * .35);   // SLOW BUTTON
            backrightmotor.setPower(-gamepad1.right_stick_y * .35);
            telemetry.addData("", "Left Bumper" + backrightmotor.getPower());

        } else if (gamepad1.right_bumper) {
            backleftmotor.setPower(gamepad1.left_stick_y * .70);   // TURBO BUTTON
            backrightmotor.setPower(-gamepad1.right_stick_y * .70);
            telemetry.addData("", "Right Bumper" + backrightmotor.getPower());

        } else {
            backleftmotor.setPower(gamepad1.left_stick_y * .6);    // REGULAR SPEED
            backrightmotor.setPower(-gamepad1.right_stick_y * .6);
            telemetry.addData("", "Normal Power" + backrightmotor.getPower());
        }
    }

    @Override
    public void stop() {
    }
}



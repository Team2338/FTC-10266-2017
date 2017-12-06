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


@TeleOp(name="Relic", group="Iterative OpMode")  // @Autonomous(...) is the other common choice
//@Disabled

public class Relic extends OpMode {
    /* Declare OpMode members. */

    private ElapsedTime runtime = new ElapsedTime();


    Servo servo0;
    Servo servo1;


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

        servo0 = hardwareMap.servo.get("servo0"); //capball
        servo1 = hardwareMap.servo.get("servo1"); //capball

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
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

        // GAMEPAD2, FRONT GLYPH GRABBER
        if (gamepad2.x) {
            servo0.setPosition(1);
        }
        if (gamepad2.b) {
            servo0.setPosition(0);
        }
        if (gamepad2.dpad_left) {
            servo1.setPosition(0);
        }
        if (gamepad2.dpad_right) {
            servo1.setPosition(1);
        }

    }

    @Override
    public void stop() {
    }
}



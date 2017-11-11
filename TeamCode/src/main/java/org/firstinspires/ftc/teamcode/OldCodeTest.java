//package org.firstinspires.ftc.teamcode;
//import com.ftdi.j2xx.D2xxManager;
//import com.qualcomm.robotcore.robocol.PeerApp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="OldCodeTest", group="Iterative OpMode")  // @Autonomous(...) is the other common choice
@Disabled
public class OldCodeTest extends OpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
//    DcMotor backLeftMotor;
//    DcMotor backRightMotor;

    DcMotor liftMotor;
    Servo servo0;
    Servo servo1;

    // DcMotor intakeLowMotor;
    // DcMotor intakeHighMotor;

    // DcMotor leftShooterMotor;
    // DcMotor rightShooterMotor;


    //static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    //static final int    CYCLE_MS    =   50;     // period of each cycle
    //static final double MAX_POS     =  .5;     // Maximum rotational position
    //static final double MIN_POS     =  0.0;     // Minimum rotational position

    //double  positionx = (MAX_POS - MIN_POS) / 2;
    //double  positionb = (MAX_POS - MIN_POS) / 2;

    // Define class members
    // Servo servo2;
    // Servo servo3;
    //Servo servo0;
    //Servo servo1;
    // double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    //boolean rampUp = true;

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
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftDrive");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightDrive");
//        backLeftMotor = hardwareMap.dcMotor.get("backLeftDrive");
//        backRightMotor = hardwareMap.dcMotor.get("backRightDrive");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");

        //intakeLowMotor = hardwareMap.dcMotor.get("collectorLow");
        //intakeHighMotor = hardwareMap.dcMotor.get("collectorHigh");

        //leftShooterMotor = hardwareMap.dcMotor.get("LeftShooter");
        //rightShooterMotor = hardwareMap.dcMotor.get("RightShooter");

        servo0 = hardwareMap.servo.get("servo0"); //capball
        servo1 = hardwareMap.servo.get("servo1"); //capball
        //servo2 = hardwareMap.servo.get("servo2");  //actuator 1
        //servo3 = hardwareMap.servo.get("servo3"); //actuator 2


        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
//         frontLeftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
//         frontRightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
//        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
//        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
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

       /* positionx -= INCREMENT;
        if (positionx <= MIN_POS) {
            positionx = MIN_POS;
        }


        positionb -= INCREMENT;
        if (positionb <= MIN_POS) {
            positionb = MIN_POS;
        }
    */
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {        /*
        if (rampUp) {
            telemetry.addData("In Rampup", 1);

            // Keep stepping up until we hit the max value.
            position += INCREMENT;
            if (position >= MAX_POS) {
                telemetry.addData("In Position", 1);
                position = MAX_POS;

            }
        } else {
            // Keep stepping down until we hit the min value.
            position -= INCREMENT;
            if (position <= MIN_POS) {
                telemetry.addData("In Else", 1);
                position = MIN_POS;
            }
        }
        */

        //telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.addData("Front Left Ticks:", frontLeftMotor.getCurrentPosition());
        telemetry.addData("Front Right Ticks", frontRightMotor.getCurrentPosition());
        telemetry.addData("Motor Output", "Front Left" + frontLeftMotor.getPower());
        telemetry.addData("Motor Output", "Front Right" + frontRightMotor.getPower());
        //telemetry.addData("Motor Output", "Rear Left" + backLeftMotor.getPower());
        //telemetry.addData("Motor Output", "Rear Right" + backRightMotor.getPower());


        frontLeftMotor.setPower(gamepad1.left_stick_y);
        frontRightMotor.setPower(-gamepad1.right_stick_y);
        //backLeftMotor.setPower(gamepad1.left_stick_y);
        //backRightMotor.setPower(-gamepad1.right_stick_y);

        // if(gamepad2.dpad_down) {
        //  intakeHighMotor.setPower(1);
        //}
        // KEEP, worked for grabber arm

        //.35 was the speed for #1 lifter
        //.70 was the speed for #2 lifter
        if (gamepad2.y) {
            liftMotor.setPower(.70);
        } else if (gamepad2.a) {
            liftMotor.setPower(-.70);
        } else {
            liftMotor.setPower(0);
        }

        if (gamepad2.x) {
            servo0.setPosition(1);
            servo1.setPosition(0);
        }
        else {
            servo0.setPosition(0);
            servo1.setPosition(1);
        }
        if(gamepad1.right_bumper) {
            frontLeftMotor.setPower(.2);
            frontRightMotor.setPower(-.2);
        }
        if(gamepad1.left_bumper) {
            frontLeftMotor.setPower(-.2);
            frontRightMotor.setPower(.2);
        }
            /*
            servo0.setPosition(1);
            servo1.setPosition(0);
        } else {
            servo0.setPosition(0);
            servo1.setPosition(1);
        }
        */
        //if (gamepad1.a) {
        //    servo0.setPosition(1);
        //} else {
        //    servo0.setPosition(0);
//        /}
        //  positionx += INCREMENT;
        //  if (positionx >= MAX_POS) {
        //positionx = MAX_POS;

        // }
        //servo3.setPosition(positionx);
        //  }
        // else {
        //   positionx -= INCREMENT;
        //  if (positionx <= MIN_POS) {
        //    positionx = MIN_POS;

        // }
        // servo3.setPosition(positionx);
        // }

        //if (gamepad2.b) {
        //   positionb += INCREMENT;
        //  if (positionb >= MAX_POS) {
        //     positionb = MAX_POS;
        //     servo2.setPosition(positionb);
        // }

        // } else {
        //   positionb -= INCREMENT;
        // if (positionb <= MIN_POS) {
        //    positionb = MIN_POS;
        // }
        //  servo2.setPosition(positionb);
        //  }

        //  if (gamepad2.left_bumper) {
        //     intakeLowMotor.setPower(-1);
        // } else if (gamepad2.right_bumper) {
        //     intakeHighMotor.setPower(-1);
        //} else {
        ///    intakeLowMotor.setPower(0);
        //   intakeHighMotor.setPower(0);
        // }
        // if (gamepad2.y) {
        //    leftShooterMotor.setPower(1);
        //    rightShooterMotor.setPower(-1);
        // } else {
        //  leftShooterMotor.setPower(0);
        //   rightShooterMotor.setPower(0);


        // }

    }


    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}

//have faith nerds
//okay we have 4 motors total we did it guys
// This will explode the robot


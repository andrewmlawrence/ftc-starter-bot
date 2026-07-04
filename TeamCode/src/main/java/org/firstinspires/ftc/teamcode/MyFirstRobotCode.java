package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "My First OpMode", group = "Linear OpMode")
public class MyFirstRobotCode extends LinearOpMode {

    // 1. Declare your hardware variables here (motors, servos, sensors)
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor arm = null;
    private CRServo intake_roller_servo_1 = null;
    private CRServo intake_roller_servo_2 = null;

    PIDController armPID = new PIDController(0.01, 0.0, 0.0);

    @Override
    public void runOpMode() {

        // 2. INITIALIZATION: This runs ONCE when you press "INIT" on the driver controller.
        // This links your Java variable to the physical motor plugged into the Control Hub.
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake_roller_servo_1 = hardwareMap.get(CRServo.class, "intake_roller_servo_1");
        intake_roller_servo_2 = hardwareMap.get(CRServo.class, "intake_roller_servo_2");

        leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_roller_servo_1.setDirection(DcMotorSimple.Direction.FORWARD);
        intake_roller_servo_2.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // 3. WAIT: The robot stops here and waits until you press the PLAY button.
        waitForStart();

        // 4. THE LOOP: Once PLAY is pressed, this loop runs over and over again
        // until you press the STOP button.
        while (opModeIsActive()) {

            // This reads the left joystick on Gamepad 1.
            // Pushing the stick forward gives a negative value, so we flip it with a minus sign.
            double leftDrivePower = gamepad1.left_stick_y;
            double rightDrivePower = gamepad1.right_stick_y;

            // Send the power level to the motor
            leftDrive.setPower(leftDrivePower);
            rightDrive.setPower(rightDrivePower);

            if (gamepad1.y) {
                arm.setTargetPosition(750);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(1);
            }

            if (gamepad1.a) {
                arm.setTargetPosition(0);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setPower(1);
            }
            
            if (gamepad1.right_bumper) {
                intake_roller_servo_1.setPower(1.0);
                intake_roller_servo_2.setPower(1.0);
            } else if (gamepad1.left_bumper) {
                intake_roller_servo_1.setPower(-1.0);
                intake_roller_servo_2.setPower(-1.0);
            } else {
                intake_roller_servo_1.setPower(0.0);
                intake_roller_servo_2.setPower(0.0);
            }

            double power = armPID.calculate(targetPosition, arm.getCurrentPosition());

            // Send a message back to the driver screen so we can see what's happening
            telemetry.addData("Left Motor Power", leftDrivePower);
            telemetry.addData("Right Motor Power", rightDrivePower);
            telemetry.addData("Arm Position", arm.getCurrentPosition());
            telemetry.update();

        }
    }
}
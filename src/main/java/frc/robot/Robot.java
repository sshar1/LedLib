// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.LedCommand;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LEDColor;
import frc.robot.subsystems.LEDMode;

import java.awt.Color;
import java.security.DigestOutputStream;
import java.sql.Driver;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public static final LED led = new LED(0, 60);

  private AddressableLED LEDStrip;
  private AddressableLEDBuffer LEDBuffer;

  // private RobotContainer m_robotContainer;
  private Joystick joystick = new Joystick(0);
  private JoystickButton rainbowButton = new JoystickButton(joystick, 1);
  private JoystickButton coneButton = new JoystickButton(joystick, 11);
  private JoystickButton cubeButton = new JoystickButton(joystick, 12);
  private JoystickButton conePulse = new JoystickButton(joystick, 9);
  private JoystickButton cubePulse = new JoystickButton(joystick, 10);

  private void setColor(int r, int g, int b){
    for(int i = 0; i < LEDBuffer.getLength(); i++){
      LEDBuffer.setRGB(i, r, g, b);
    }
  }

  private void configureBindings(){
    // Command coneFlash = new RepeatCommand(new LedCommand(LEDBuffer, LEDStrip, "cone", true)
    // .andThen(new WaitCommand(0.15))
    // .andThen(new LedCommand(LEDBuffer, LEDStrip, "blank", true))
    // .andThen(new WaitCommand(0.15)));

    // Command cubeFlash = new RepeatCommand(new LedCommand(LEDBuffer, LEDStrip, "cube", true)
    // .andThen(new WaitCommand(0.15))
    // .andThen(new LedCommand(LEDBuffer, LEDStrip, "blank", true))
    // .andThen(new WaitCommand(0.15)));

    coneButton.onTrue(new LedCommand(LEDColor.YELLOW, LEDMode.STATIC));
    cubeButton.onTrue(new LedCommand(LEDColor.PURPLE, LEDMode.STATIC));
    // conePulse.onTrue(new RepeatCommand(new SequentialCommandGroup(new LedCommand(LEDColor.YELLOW, LEDMode.PULSE), new WaitCommand(0.1))));
    // cubePulse.onTrue(new RepeatCommand(new SequentialCommandGroup(new LedCommand(LEDColor.PURPLE, LEDMode.PULSE), new WaitCommand(0.1))));
    conePulse.onTrue(new RepeatCommand(new LedCommand(LEDColor.YELLOW, LEDMode.PULSE)));
    cubePulse.onTrue(new RepeatCommand(new LedCommand(LEDColor.PURPLE, LEDMode.PULSE)));
    rainbowButton.onTrue(new LedCommand(LEDColor.YELLOW, LEDMode.RAINBOW));
  }


  private void rainbow(int start) {
    // int m_rainbowFirstPixelHue = start;
    // For every pixel
    for (int i = 0; i < LEDBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      i %= LEDBuffer.getLength();
      final var hue = (start + (i * 180 / LEDBuffer.getLength())) % 180;
      // Set the value
      LEDBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    // m_rainbowFirstPixelHue += 50;
    // Check bounds
    start += 3;
    start %= 180;
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
  //   m_robotContainer = new RobotContainer();

  //   LEDStrip = new AddressableLED(0);
  //   LEDBuffer = new AddressableLEDBuffer(60);

  //   LEDStrip.setLength(LEDBuffer.getLength());

  //   LEDStrip.setData(LEDBuffer);
  //   LEDStrip.start();

  //   for (var i = 0; i < LEDBuffer.getLength(); i++) {
  //     // Sets the specified LED to the RGB values for red
  //     LEDBuffer.setRGB(i, 255, 0, 0);
  //     // LEDBuffer.setRGB(i, 35, 184, 149);
  //  }
  //   LEDStrip.setData(LEDBuffer);

    // SmartDashboard.putData("Cone", new LedCommand(LEDBuffer, LEDStrip, "cone", false));
    // SmartDashboard.putData("Cube", new LedCommand(LEDBuffer, LEDStrip, "cube", false));

    configureBindings();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    // if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
    //   setColor(0, 0, 255);
    // }
    // else {
    //   setColor(255, 0, 0);
    // }

    // LEDStrip.setData(LEDBuffer);
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // rainbow(start);
    // start += 5;
    // LEDStrip.setData(LEDBuffer);
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

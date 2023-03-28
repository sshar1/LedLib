// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.LedCommand;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.LEDColor;
import frc.robot.subsystems.LEDMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public static final LED led = new LED(0, 60);

  private Joystick joystick = new Joystick(0);
  private JoystickButton rainbowButton = new JoystickButton(joystick, 1);
  private JoystickButton coneButton = new JoystickButton(joystick, 11);
  private JoystickButton cubeButton = new JoystickButton(joystick, 12);
  private JoystickButton conePulse = new JoystickButton(joystick, 9);
  private JoystickButton cubePulse = new JoystickButton(joystick, 10);

  // Buttons to demonstrate different modes of leds
  private void configureBindings(){
    coneButton.onTrue(new LedCommand(LEDColor.YELLOW, LEDMode.STATIC));
    cubeButton.onTrue(new LedCommand(LEDColor.PURPLE, LEDMode.STATIC));
    conePulse.onTrue(new LedCommand(LEDColor.YELLOW, LEDMode.PULSE));
    cubePulse.onTrue(new LedCommand(LEDColor.PURPLE, LEDMode.PULSE));
    rainbowButton.onTrue(new LedCommand(LEDColor.YELLOW, LEDMode.RAINBOW));
  }

  @Override
  public void robotInit() {
    configureBindings();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    // Turn led to alliance color (red or blue)
    if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
      led.setColor(LEDColor.BLUE);
    }
    else {
      led.setColor(LEDColor.RED);
    }
  }

  @Override
  public void autonomousInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}

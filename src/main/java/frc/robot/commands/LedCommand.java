// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.LEDColor;
import frc.robot.subsystems.LEDMode;

/** An example command that uses an example subsystem. */
public class LedCommand extends CommandBase {

  private LEDColor color;
  private LEDMode mode;

  /**
   * Creates a new LedCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LedCommand(LEDColor color, LEDMode mode) {
    this.color = color;
    this.mode = mode;

    addRequirements(Robot.led);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (mode != LEDMode.RAINBOW) {
      Robot.led.setColor(color.r, color.g, color.b);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (mode == LEDMode.PULSE) {
      Robot.led.setColor(color.r, color.g, color.b);
      Timer.delay(0.1);
      Robot.led.setColor(0, 0, 0);
      Timer.delay(0.1);
    }
    else if (mode == LEDMode.STATIC) {
      Robot.led.setColor(color.r, color.g, color.b);

    }
    else if (mode == LEDMode.RAINBOW) {
      Robot.led.rainbow();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.led.setColor(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (mode == LEDMode.PULSE || mode == LEDMode.STATIC) {
      return true;
    }
    else {
      return false;
    }
  }
}

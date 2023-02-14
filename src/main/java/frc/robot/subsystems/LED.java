// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
  /** Creates a new AddressableLED. */
  private AddressableLED LEDStrip;
  private AddressableLEDBuffer LEDBuffer;
  private static int rainbowStart = 0;

  public LED(int port, int length) {
    LEDStrip = new AddressableLED(port);
    LEDBuffer = new AddressableLEDBuffer(length);

    LEDStrip.setLength(LEDBuffer.getLength());
   
    LEDStrip.setData(LEDBuffer);
    LEDStrip.start();
  }

  public void setColor(int h, int s, int v) {

    for(int i = 0; i < LEDBuffer.getLength(); i++){
      LEDBuffer.setRGB(i, h, s, v);
    }

    LEDStrip.setData(LEDBuffer);
  }

  public void rainbow() {

    for (int i = 0; i < LEDBuffer.getLength(); i++) {
      i %= LEDBuffer.getLength();
      final var hue = (rainbowStart + (i * 180 / LEDBuffer.getLength())) % 180;
      // Set the value
      LEDBuffer.setHSV(i, hue, 255, 128);
    }

    LEDStrip.setData(LEDBuffer);

    rainbowStart += 3;
    rainbowStart %= 180;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
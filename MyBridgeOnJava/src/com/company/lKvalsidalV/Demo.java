package com.company.lKvalsidalV;

import com.company.lKvalsidalV.devices.Device;
import com.company.lKvalsidalV.devices.Radio;
import com.company.lKvalsidalV.devices.TV;
import com.company.lKvalsidalV.remotes.AdvancedRemote;
import com.company.lKvalsidalV.remotes.BasicRemote;

public class Demo {
    public static void main(String[] args) {
        testDevice(new TV());
        testDevice(new Radio());
    }

    public static void testDevice(Device device) {
        System.out.println("Tests with basic remote.");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }
}
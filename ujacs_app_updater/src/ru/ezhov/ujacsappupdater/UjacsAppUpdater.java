package ru.ezhov.ujacsappupdater;

import java.io.IOException;

public class UjacsAppUpdater {

    public static void main(String[] args) throws IOException {
        ContollerVersion contollerVersion = new ContollerVersion("spt", "1.06.56", "10.103.24.135", 4444);
        contollerVersion.startCheckerAndUpdate();
    }
}

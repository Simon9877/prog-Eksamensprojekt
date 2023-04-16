// Denne klasse fungerer som driverklassen for registrerings- og loginsystemet.

package org.example;

public class RegLogSysDriver {
    public static void main(String[] args) {
// Opretter et RegLogSystem-objekt og kører systemet.
        RegLogSystem system = new RegLogSystem();
        system.run();
    }
}
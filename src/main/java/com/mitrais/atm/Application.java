package com.mitrais.atm;

import com.mitrais.atm.model.Account;

public class Application {

    public static void main(String[] args) {
        // setting up data first
        SetupData setupData = new SetupData();
        setupData.addAccount(new Account(" John Doe", "000000", 100, 112233));
        setupData.addAccount(new Account("Jane Doe", "000000", 30, 112244));

        Screens screens = new Screens(setupData);

        screens.showAuthScreen();
    }
}

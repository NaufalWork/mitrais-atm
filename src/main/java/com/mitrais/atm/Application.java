package com.mitrais.atm;

import com.mitrais.atm.service.CSVService;
import com.mitrais.atm.utility.FileUtil;

public class Application {

    public static void main(String[] args) {
        Screens screens = new Screens();

        //show the auth screen first
        screens.showAuthScreen();
    }
}

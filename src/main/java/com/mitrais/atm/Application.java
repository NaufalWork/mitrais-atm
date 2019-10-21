package com.mitrais.atm;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.DataService;
import com.mitrais.atm.utility.FileUtil;

public class Application {

    public static void main(String[] args) {
        // setting up data first
        AccountService accountService = new AccountService(FileUtil.getResourcePath() + "\\");
        DataService setupData = accountService.getDataService();

        Screens screens = new Screens(setupData);

        //show the auth screen first
        screens.showAuthScreen();
    }
}

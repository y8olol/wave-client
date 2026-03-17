/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.gui.screens.accounts;

import waveclient.waveclient.gui.GuiTheme;
import waveclient.waveclient.gui.WindowScreen;
import waveclient.waveclient.gui.widgets.containers.WHorizontalList;
import waveclient.waveclient.gui.widgets.pressable.WButton;
import waveclient.waveclient.systems.accounts.Account;
import waveclient.waveclient.systems.accounts.AccountType;
import waveclient.waveclient.systems.accounts.TokenAccount;
import waveclient.waveclient.utils.render.color.Color;

import static waveclient.waveclient.WaveClient.mc;

public class AccountInfoScreen extends WindowScreen {
    private final Account<?> account;

    public AccountInfoScreen(GuiTheme theme, Account<?> account) {
        super(theme, account.getUsername() + " details");
        this.account = account;
    }

    @Override
    public void initWidgets() {
        TokenAccount e = (TokenAccount) account;
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        String tokenLabel = account.getType() + " token:";
        if (account.getType() == AccountType.Session) tokenLabel = "";

        WButton copy = theme.button("Copy");
        copy.action = () -> mc.keyboard.setClipboard(e.getToken());

        l.add(theme.label(tokenLabel));
        l.add(theme.label(account.getType() == AccountType.Session ? "Click to copy Token" : e.getToken()).color(Color.GRAY)).pad(5);
        l.add(copy);
    }
}

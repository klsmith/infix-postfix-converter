package io.github.klsmith.ifpfc.cl.command;

import io.github.klsmith.ifpfc.cl.CommandLineApp;

public class StopCommand implements Command {

    private final CommandLineApp app;

    public StopCommand(CommandLineApp app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.stop();
    }

}

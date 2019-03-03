package io.github.klsmith.ifpfc.cl.command;

import java.util.Objects;

import io.github.klsmith.ifpfc.cl.CommandLineApp;

public final class ExitCommand implements Command {

    private final CommandLineApp app;

    public ExitCommand(CommandLineApp app) {
        this.app = app;
    }

    @Override
    public void execute() {
        app.stop();
    }

    @Override
    public int hashCode() {
        return Objects.hash(app);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExitCommand) {
            final ExitCommand other = (ExitCommand) obj;
            return Objects.equals(app, other.app);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Exit()";
    }

}

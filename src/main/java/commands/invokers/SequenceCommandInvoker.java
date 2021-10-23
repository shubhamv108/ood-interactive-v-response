package commands.invokers;

import commands.IVRCommand;
import commands.IVRInvalidInputCommand;
import commands.IVRValidInputCommand;
import options.IVROption;

import java.util.ArrayList;
import java.util.List;

public class SequenceCommandInvoker {

    private IVROption state;
    private final List<IVRCommand> commands = new ArrayList<>();
    private int currentSequenceNumber = -1;
    private Integer maxFailures;

    public SequenceCommandInvoker(IVROption state, Integer maxFailures) {
        this.state = state;
        this.maxFailures = maxFailures;
    }

    public void invoke(String input) {
        IVRCommand command = null;
        try {
            this.state = this.state.getNext(Integer.valueOf(input));
            command = new IVRValidInputCommand(input);
        } catch (Exception exception) {
            command = new IVRInvalidInputCommand(input);
            if (this.maxFailures != null && this.currentSequenceNumber >= this.maxFailures - 2) {
                int i = currentSequenceNumber;
                while (i >= currentSequenceNumber - maxFailures + 2)
                    if (this.getCommand(i) instanceof IVRInvalidInputCommand)
                        i--;
                    else break;
                if (i < currentSequenceNumber - maxFailures + 2) {
                    System.out.println("Ending call");
                    System.exit(0);
                }
            }
        }
        this.invoke(command);
    }

    private void invoke(IVRCommand command) {
        this.setCommand(command);
    }

    private void setCommand(final IVRCommand command) {
        this.getCommands().add(command);
        this.incrementSequenceNumber();
        command.execute(this.state);
    }

    private void incrementSequenceNumber() {
        this.currentSequenceNumber++;
    }

    public IVRCommand getLastExecutedCommand() {
        return this.getCommand(this.getCurrentSequenceNumber());
    }

    private IVRCommand getCommand(int sequenceNumber) {
        return this.getCommands().get(sequenceNumber);
    }

    private int getCurrentSequenceNumber() {
        return this.currentSequenceNumber;
    }

    private List<IVRCommand> getCommands() {
        return this.commands;
    }
}

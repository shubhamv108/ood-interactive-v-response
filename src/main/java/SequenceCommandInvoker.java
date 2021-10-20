import java.util.ArrayList;
import java.util.List;

public class SequenceCommandInvoker {

    private IVRState state;
    private final List<IVRCommand> commands = new ArrayList<>();
    private int currentSequenceNumber = -1;

    public SequenceCommandInvoker(IVRState state) {
        this.state = state;
    }

    public void invoke(String input) {
        IVRCommand command = null;
        try {
            this.state = this.state.getNext(Integer.valueOf(input));
            command = new IVRValidInputCommand(input);
        } catch (Exception exception) {
            command = new IVRInvalidInputCommand(input);
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
        return this.getCommands().get(this.getCurrentSequenceNumber());
    }

    private int getCurrentSequenceNumber() {
        return this.currentSequenceNumber;
    }

    private List<IVRCommand> getCommands() {
        return this.commands;
    }
}

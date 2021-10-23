package commands;

import options.IVROption;

public abstract class IVRCommand {

    private final String number;

    public IVRCommand(String number) {
        this.number = number;
    }

    public void execute(IVROption state) {
        state.getOptions().forEach(System.out::println);
    }

}

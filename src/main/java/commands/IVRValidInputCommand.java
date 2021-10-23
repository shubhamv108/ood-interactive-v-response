package commands;

import options.IVROption;

public class IVRValidInputCommand extends IVRCommand {
    public IVRValidInputCommand(String number) {
        super(number);
    }

    public void execute(IVROption option) {
        /*toVoice(*/
        option.getOptions().forEach(System.out::println)
        /*)*/;
    }
}

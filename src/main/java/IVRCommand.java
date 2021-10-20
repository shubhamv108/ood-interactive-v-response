public abstract class IVRCommand {

    private final String number;

    public IVRCommand(String number) {
        this.number = number;
    }

    public void execute(IVRState state) {
        System.out.println(state.getOptions());
    }

}

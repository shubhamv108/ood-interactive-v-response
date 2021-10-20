import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class IVRState {

    private final String optiontext;
    private IVRState parent;

    Map<Integer, Map<Integer, IVRState>> version;

    private final Map<Integer, IVRState> ivrSubStates;
    private final List<String> options;

    public IVRState(String optiontext, Map<Integer, IVRState> ivrSubStates) {
        this.optiontext = optiontext;
        this.ivrSubStates = ivrSubStates;
        this.ivrSubStates.forEach((k, v) -> v.setParent(this));
        this.options = new ArrayList<>();
        this.options.addAll(
                this.ivrSubStates.keySet()
                        .stream()
                        .filter(k -> k != 8 && k != 9)
                        .map(k -> this.ivrSubStates.get(k).getOptiontext())
                        .collect(Collectors.toList()));
        if (this.ivrSubStates.containsKey(9))
            this.options.add("Press 9 to go back to the main menu");
        this.options.add("Please disconnect to end this call");
    }

    private IVRState copy() {
        return new IVRState(this.getOptiontext(), this.ivrSubStates);
    }

    private void setParent(IVRState parent) {
        this.parent = parent;
        this.ivrSubStates.put(8, parent);
        this.options.add("Press 8 to go back to the previous menu");
    }

    public IVRState setNext(Integer input) {
        IVRState next = this.ivrSubStates.get(input);
        if (next == null) {
            throw new IllegalArgumentException("You have not provided the right input. Please try again");
        }
        return next;
    }

    public void setOption(int number, IVRState state) {
        this.ivrSubStates.putIfAbsent(number, state);
    }

    public String getOptiontext() {
        return optiontext;
    }

    public List<String> getOptions() {
        return this.ivrSubStates.values()
                .stream()
                .map(IVRState::getOptiontext)
                .collect(Collectors.toList());
    }

    public static IVRStateBuilder builder() {
        return new IVRStateBuilder();
    }

    public static class IVRStateBuilder {
        private String optionText;
        private final Map<Integer, IVRState> ivrSubStatesPrototypes= new TreeMap<>();

        public IVRStateBuilder withOptionText(String optionText) {
            this.optionText = optionText;
            return this;
        }

        public IVRStateBuilder withOption(int number, IVRState state) {
            this.ivrSubStatesPrototypes.putIfAbsent(number, state);
            return this;
        }

        public IVRStateBuilder withBaseState(IVRState baseState) {
            this.ivrSubStatesPrototypes.put(9, baseState);
            return this;
        }

        public IVRState build() {
            return new IVRState(optionText, ivrSubStatesPrototypes);
        }
    }

}

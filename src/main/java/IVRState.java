import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class IVRState {

    private final String optionText;
    private IVRState parent;

    private final Map<Integer, IVRState> ivrSubStates;

    public IVRState(String optionText, Map<Integer, IVRState> ivrSubStates) {
        this.optionText = optionText;
        this.ivrSubStates = ivrSubStates;
        this.ivrSubStates.forEach((k, v) -> {
            if (k != 8 && k != 9)
                v.setParent(this);
        });
    }

    private void setParent(IVRState parent) {
        this.parent = parent;
        this.ivrSubStates.put(8, parent);
    }

    public IVRState getNext(Integer input) {
        IVRState next = this.ivrSubStates.get(input);
        if (next == null) {
            throw new IllegalArgumentException("You have not provided the right input. Please try again");
        }
        return next;
    }

    public void setNextOption(int number, IVRState state) {
        this.ivrSubStates.putIfAbsent(number, state);
        state.setParent(this);
    }

    public String getOptionText() {
        return optionText;
    }

    public List<String> getOptions() {
        List<String> options = this.ivrSubStates.entrySet()
                .stream()
                .map(entry -> {
                    if (entry.getValue() == this.parent)
                        if (entry.getKey() == 8)
                            return "Press 8 to go back to the previous menu";
                    return entry.getValue().getOptionText();
                })
                .filter(Objects::nonNull)
                .filter(e -> !e.isBlank())
                .collect(Collectors.toList());
        if (this.ivrSubStates.containsKey(9))
            options.add("Press 9 to go back to the main menu");
        options.add("Please disconnect to end this call");
        return options;
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

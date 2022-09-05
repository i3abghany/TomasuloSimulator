import java.util.HashMap;
import java.util.Map;

public class RegisterFile {
    private final HashMap<String, Register> registers;

    public RegisterFile() {
        registers = constructDefaultRegisterFile();
    }

    private static HashMap<String, Register> constructDefaultRegisterFile() {
        var registers = new HashMap<String, Register>();
        registers.put("R0", new Register("R0", 0, ""));
        registers.put("R1", new Register("R1", 1, ""));
        registers.put("R2", new Register("R2", 2, ""));
        registers.put("R3", new Register("R3", 3, ""));
        registers.put("R4", new Register("R4", 4, ""));
        registers.put("R5", new Register("R5", 5, ""));
        registers.put("R6", new Register("R6", 6, ""));
        registers.put("R7", new Register("R7", 7, ""));
        return registers;
    }

    public int getValue(String name) {
        return registers.get(name).getValue();
    }

    public String getTag(String name) {
        return registers.get(name).getTag();
    }

    public boolean isValid(String name) {
        return registers.get(name).isValid();
    }

    public void invalidate(String name, String tag) {
        registers.get(name).setValid(false);
        registers.get(name).setTag(tag);
    }

    public Register getByName(String name) {
        return registers.get(name);
    }

    public void broadcastValue(String tag, int value) {
        for (Register entry: registers.values()) {
            if (!entry.isValid() && entry.getTag().equals(tag)) {
                entry.setValue(value);
                entry.setValid(true);
                entry.setTag("");
            }
        }
    }
}

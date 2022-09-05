import java.util.Objects;

public class Register {
    private final String name;
    private int value;
    private String tag;
    private boolean valid;

    public Register(String _name, int _value, String _tag) {
        this(_name, _value, _tag, true);
    }

    public Register(String _name, int _value, String _tag, boolean _valid) {
        name = _name;
        value = _value;
        tag = _tag;
        valid = _valid;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getTag() {
        return tag;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Register register = (Register) o;
        return value == register.value && valid == register.valid && Objects.equals(name, register.name) && Objects.equals(tag, register.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, tag, valid);
    }
}

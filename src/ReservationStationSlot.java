import java.util.Objects;

public class ReservationStationSlot {
    private Instruction instruction;
    private Register src1;
    private Register src2;
    private boolean valid;
    private final int tag;

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public void setSrc1(Register src1) {
        this.src1 = src1;
    }

    public void setSrc2(Register src2) {
        this.src2 = src2;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public ReservationStationSlot(Instruction instruction, Register src1, Register src2, boolean valid) {
        this.instruction = instruction;
        this.src1 = src1;
        this.src2 = src2;
        this.valid = valid;
        this.tag = Objects.hash(instruction, src1, src2);
    }

    public ReservationStationSlot(Instruction instruction, Register src1, Register src2) {
        this(instruction, src1, src2, false);
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public Register getSrc1() {
        return src1;
    }

    public Register getSrc2() {
        return src2;
    }

    public boolean getValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationStationSlot that = (ReservationStationSlot) o;
        return valid == that.valid && Objects.equals(instruction, that.instruction) && Objects.equals(src1, that.src1) && Objects.equals(src2, that.src2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instruction, src1, src2, valid);
    }

    @Override
    public String toString() {
        return "ReservationStationSlot[" +
                "instruction=" + instruction + ", " +
                "src1=" + src1 + ", " +
                "src2=" + src2 + ", " +
                "valid=" + valid + ']';
    }

    public int getTag() {
        return tag;
    }
}

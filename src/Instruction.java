import java.util.Objects;
import java.util.Scanner;

public class Instruction {
    private final Kind kind;
    private final String op1;
    private final String op2;
    private final String dst;

    public Instruction(Kind _kind, String _dst, String _op1, String _op2) {
        kind = _kind;
        dst = _dst;
        op1 = _op1;
        op2 = _op2;
    }

    public enum Kind {
        ADD,
        MUL
    }

    public Kind getKind() {
        return kind;
    }

    public String getDst() {
        return dst;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public static Instruction fromString(String insn) {
        String noCommas = insn.replace(",", "");
        Scanner scanner = new Scanner(noCommas);
        String opcode = scanner.next();
        return new Instruction(opcode.equals("MUL") ? Kind.MUL : Kind.ADD, scanner.next(), scanner.next(), scanner.next());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return kind == that.kind && Objects.equals(op1, that.op1) && Objects.equals(op2, that.op2) && Objects.equals(dst, that.dst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, op1, op2, dst);
    }
}

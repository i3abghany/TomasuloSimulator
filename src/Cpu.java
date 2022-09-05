import java.util.ArrayList;
import java.util.List;

public class Cpu {
    private final ArrayList<Instruction> instructions;
    private int position;
    private final RegisterFile registerFile;
    private final ReservationStation addReservationStation = new ReservationStation(8);
    private final ExecutionUnit addExecutionUnit = new AddExecutionUnit(this);
    private final CommonDataBus commonDataBus;

    private static final int N_STAGES = 4;
    private static final int FETCH_STAGE = 0;
    private static final int DECODE_STAGE = 1;
    private static final int EXECUTE_STAGE = 2;
    private static final int WRITEBACK_STAGE = 3;
    private final Instruction[] instructionsInStages = new Instruction[4];

    public Cpu(ArrayList<Instruction> _instructions) {
        instructions = _instructions;
        position = 0;
        registerFile = new RegisterFile();
        commonDataBus = new CommonDataBus(registerFile, new ArrayList<>(List.of(addReservationStation)));
    }

    public void run() {
        boolean mustStall = false;
        String finalizedTag = null;
        int finalizedValue = -1;

        do {
            Instruction writebackInstruction;
            if (!mustStall) {
                var fetchedInstruction = fetch();
                writebackInstruction = shiftPipeline(fetchedInstruction);
            } else {
                writebackInstruction = shiftFromExecuteStage();
            }

            if (finalizedTag != null) {
                commonDataBus.writeBack(finalizedTag, finalizedValue);
            }

            var readySlot = addReservationStation.getOccupiedReadySlot();
            ReservationStationSlot slotToExecute = null;
            if (readySlot != -1) {
                slotToExecute = addReservationStation.getAt(readySlot);
            }

            if (instructionsInStages[DECODE_STAGE] != null) {
                mustStall = !decode();
            }

            if (addExecutionUnit.hasInstruction()) {
                addExecutionUnit.progress();
                if (addExecutionUnit.executionFinished()) {
                    var executingSlot = addExecutionUnit.getCurrentSlot();
                    var value = addExecutionUnit.calc();
                    commonDataBus.broadcastValue(Integer.toString(executingSlot.getTag()), value);
                    finalizedTag = Integer.toString(executingSlot.getTag());
                    finalizedValue = value;
                    addReservationStation.removeSlot(addExecutionUnit.getCurrentSlot());
                }
            } else {
                if (slotToExecute != null) {
                    addExecutionUnit.dispatch(slotToExecute);
                    slotToExecute.setValid(false);
                }
            }

        } while (!emptyPipeline());
    }

    private boolean emptyPipeline() {
        boolean pipelineEmpty = true;
        for (Instruction insn : instructionsInStages)
            pipelineEmpty &= (insn == null);
        return pipelineEmpty;
    }

    private boolean decode() {
        Instruction instruction = instructionsInStages[DECODE_STAGE];
        boolean isSource1Valid = registerFile.isValid(instruction.getOp1());
        boolean isSource2Valid = registerFile.isValid(instruction.getOp2());

        var renamedS1Entry = new Register(
                instruction.getOp1(),
                isSource1Valid ? registerFile.getValue(instruction.getOp1()) : -1,
                isSource1Valid ? instruction.getOp1() : registerFile.getTag(instruction.getOp1()),
                isSource1Valid
        );

        var renamedS2Entry = new Register(
                instruction.getOp2(),
                isSource2Valid ? registerFile.getValue(instruction.getOp2()) : -1,
                isSource2Valid ? instruction.getOp2() : registerFile.getTag(instruction.getOp2()),
                isSource2Valid
        );

        int emptySlotIdx = addReservationStation.getEmptySlotIndex();
        if (emptySlotIdx == -1) return false;

        var slot = new ReservationStationSlot(instruction, renamedS1Entry, renamedS2Entry, true);
        addReservationStation.setAt(emptySlotIdx, slot);
        var slotTag = addReservationStation.getAt(emptySlotIdx).getTag();
        registerFile.invalidate(instruction.getDst(), Integer.toString(slotTag));
        return true;
    }

    private Instruction shiftFromStage(int stage, Instruction injectedInstruction) {
        Instruction writebackInstruction = instructionsInStages[WRITEBACK_STAGE];
        for (int i = stage + 1; i < N_STAGES; i++) {
            instructionsInStages[i] = instructionsInStages[i - 1];
        }
        instructionsInStages[stage] = injectedInstruction;
        return writebackInstruction;
    }

    private Instruction shiftPipeline(Instruction fetchedInstruction) {
        return shiftFromStage(FETCH_STAGE, fetchedInstruction);
    }

    /**
     * Shift the execute and writeback stages and inject a bubble in execute. Should be used in the case of not having
     * an empty reservation slot.
     *
     * @return The writeback stage instruction that exited the pipeline.
     */
    private Instruction shiftFromExecuteStage() {
        return shiftFromStage(EXECUTE_STAGE, null);
    }

    private Instruction fetch() {
        return position < instructions.size() ? instructions.get(position++) : null;
    }

    public RegisterFile getRegisterFile() {
        return registerFile;
    }
}

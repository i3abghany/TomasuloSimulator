import java.util.Arrays;

public class ReservationStation {
    private final ReservationStationSlot[] reservationStationSlots;

    public ReservationStation(int size) {
        reservationStationSlots = new ReservationStationSlot[size];
        for (int i = 0; i < size; i++) {
            reservationStationSlots[i] = new ReservationStationSlot(null, null, null);
        }
    }

    public int getOccupiedReadySlot() {
        for (int i = 0; i < reservationStationSlots.length; i++) {
            var slot = reservationStationSlots[i];
            if (slot.getValid() && slot.getSrc1().isValid() && slot.getSrc2().isValid())
                return i;
        }
        return -1;
    }

    public int getEmptySlotIndex() {
        for (int i = 0; i < reservationStationSlots.length; i++) {
            if (!reservationStationSlots[i].getValid())
                return i;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationStation that = (ReservationStation) o;
        return Arrays.equals(reservationStationSlots, that.reservationStationSlots);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(reservationStationSlots);
    }

    public void setAt(int emptySlotIdx, ReservationStationSlot slot) {
        reservationStationSlots[emptySlotIdx] = slot;
    }

    public ReservationStationSlot getAt(int slot) {
        return slot == -1 ? null : reservationStationSlots[slot];
    }

    public void removeSlot(ReservationStationSlot slot) {
        slot.setValid(false);
    }

    public void broadcastValue(String tag, int value) {
        for (var slot: reservationStationSlots) {
            if (!slot.getValid()) continue;

            var src1 = slot.getSrc1();
            if (!src1.isValid() && src1.getTag().equals(tag)) {
                src1.setValid(true);
                src1.setTag(tag);
                src1.setValue(value);
            }

            var src2 = slot.getSrc2();
            if (!src2.isValid() && src2.getTag().equals(tag)) {
                src2.setValid(true);
                src2.setTag(tag);
                src2.setValue(value);
            }
        }
    }
}

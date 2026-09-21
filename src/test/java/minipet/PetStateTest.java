package minipet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Rectangle;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedAnnotationTypes;

class PetStateTest {
    private static final Rectangle SCREEN = new Rectangle(0, 0, 500, 400);

    @Test
    void advanceMovesPet() {
        PetState state = new minipet.PetState(100, 100, 3, 2);

        state.advance(SCREEN);

        assertEquals(103, state.getX());
        assertEquals(102, state.getY());
    }

    @Test
    void advanceReversesAtRightEdge() {
        PetState state = new PetState(403, 100, 3, 2);

        state.advance(SCREEN);

        assertEquals(404, state.getX());
        assertEquals(-3, state.getSpeedX());
    }

    @Test
    void setPositionKeepsPetOnScreen() {
        minipet.PetState state = new minipet.PetState(100, 100, 3, 2);

        state.setPosition(999, -50, SCREEN);

        assertEquals(404, state.getX());
        assertEquals(0, state.getY());
    }

}

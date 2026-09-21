package minipet;

import java.awt.Rectangle;
public class PetStateTest {
    private static final Rectangle SCREEN = new Rectangle(0, 0, 500, 400);

    private PetStateTest() {

    }

    public static void main(String[] args) {
        testAdvancedMovesPet();
        testAdvancedReversesAtRightEdge();
        testSetPositionKeepsPetOnScreen();

        System.out.println("All PetState tests passed.");
    }

    private static void testAdvancedMovesPet() {
        PetState state = new PetState(100, 100, 3, 2);

        state.advance(SCREEN);

        assertEquals(103, state.getX(), "The pet should move right.");
        assertEquals(102, state.getY(), "The pet should move down.");
    }

    private static void testAdvancedReversesAtRightEdge() {
        PetState state = new PetState(403, 100, 3, 2);

        state.advance(SCREEN);

        assertEquals(404, state.getX(), "The pet should stop at the right edge.");
        assertEquals(-3, state.getSpeedX(), "The horizontal speed should reverse");
    }

    private static void testSetPositionKeepsPetOnScreen() {
        PetState state = new PetState(100, 100, 3, 2);

        state.setPosition(1000, -10, SCREEN);

        assertEquals(404, state.getX(), "The pet should not move past the right edge.");
        assertEquals(0, state.getY(), "The pet should not move above the top edge.");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(
                    message + " Expected: " + ", actual: " + actual
            );
        }
    }
}

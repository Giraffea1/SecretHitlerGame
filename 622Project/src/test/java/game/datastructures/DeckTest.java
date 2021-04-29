package game.datastructures;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;
    Policy fascistPolicy;
    Policy liberalPolicy;

    @BeforeEach
    void setUp() {
        fascistPolicy = new Policy(Policy.Type.FASCIST);
        liberalPolicy = new Policy(Policy.Type.LIBERAL);
        deck = new Deck();
    }

    @Test
    void removeOnePolicy() {
        assertTrue(deck.isEmpty());
        deck.add(fascistPolicy);
        deck.add(liberalPolicy);
        Policy p = deck.remove();
        assertEquals(liberalPolicy,p);
        assertEquals(1, deck.getSize());
        assertEquals(fascistPolicy, deck.peek(0));
    }

    @Test
    void removeEmptyPolicy() {
        assertTrue(deck.isEmpty());
        // throws exception when removing empty deck
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            deck.remove();
        });
    }



    @Test
    void addOnePolicy() {
        assertTrue(deck.isEmpty());
        deck.add(fascistPolicy);
        assertFalse(deck.isEmpty());
        assertEquals(1, deck.getSize());
        assertEquals(fascistPolicy, deck.peek(0));
    }

    @Test
    void addMultiplePolicy() {
        assertTrue(deck.isEmpty());
        for (int i = 0; i < 3; i++) {
            deck.add(fascistPolicy);
            assertEquals(fascistPolicy, deck.peek(0));
            assertEquals(i * 2 + 1, deck.getSize());
            deck.add(liberalPolicy);
            assertEquals(liberalPolicy, deck.peek(0));
            assertEquals(i * 2 + 2, deck.getSize());
        }
        assertFalse(deck.isEmpty());
    }

    @Test
    void peek() {
        assertTrue(deck.isEmpty());
        deck.add(fascistPolicy);
        deck.add(liberalPolicy);
        assertEquals(liberalPolicy, deck.peek(0));
        assertEquals(fascistPolicy, deck.peek(1));
    }

    @Test
    void isEmpty() {
        assertTrue(deck.isEmpty());
        deck.add(fascistPolicy);
        assertFalse(deck.isEmpty());
    }

    @Test
    void testIsEmptyFalse() {
        assertEquals(true, deck.isEmpty());
        deck.add(liberalPolicy);
        assertEquals(false, deck.isEmpty());
    }

    @Test
    void getSize() {
        assertTrue(deck.isEmpty());
        deck.add(fascistPolicy);
        deck.add(liberalPolicy);
        assertEquals(2, deck.getSize());
    }

    @Test
    void shuffle() {
        assertTrue(deck.isEmpty());
        deck.add(fascistPolicy);
        deck.add(fascistPolicy);
        deck.add(fascistPolicy);
        deck.add(fascistPolicy);
        deck.add(liberalPolicy);
        int differentCount = 0;
        for (int i = 0; i < 99; i++) {
            deck.shuffle();
            if (deck.peek(0).equals(fascistPolicy)) {
                differentCount++;
            }
        }
//        System.out.print(differentCount);
        assertTrue(differentCount > 50);
    }
}
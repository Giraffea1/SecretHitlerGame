package game.datastructures.board;

import com.fasterxml.jackson.databind.JsonSerializer;
import game.datastructures.Policy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiveToSixPlayerBoardTest {
  Board board;
  Policy fP;
  Policy lP;

  @BeforeEach
  void setUp() {
    board = new FiveToSixPlayerBoard();
    fP = new Policy(Policy.Type.FASCIST);
    lP = new Policy(Policy.Type.LIBERAL);
  }

  @Test
  void enactFascistPolicy() {
    board.enactPolicy(fP);
    assertEquals(board.getLastEnactedType(), Policy.Type.FASCIST);
    assertEquals(board.getNumFascistPolicies(), 1);
  }

  @Test
  void enactLiberalPolicy() {
    board.enactPolicy(lP);
    assertEquals(board.getLastEnactedType(), Policy.Type.LIBERAL);
    assertEquals(board.getNumLiberalPolicies(), 1);
  }

  @Test
  void enactPolicyAfterVictory() {
    for (int i = 0; i < 5; i++ ) {
      board.enactPolicy(lP);
    }
    // enact policy after victory would throw exception
    assertThrows(Exception.class, () -> board.enactPolicy(lP));
  }

  @Test
  void isLiberalVictory() {
    for (int i = 0; i < 4; i++ ) {
      board.enactPolicy(lP);
    }
    assertFalse(board.isLiberalVictory());
    board.enactPolicy(lP);
    // test after 5 liberal policy, liberal would win
    assertTrue(board.isLiberalVictory());
  }

  @Test
  void isFascistVictory() {
    for (int i = 0; i < 5; i++ ) {
      board.enactPolicy(fP);
    }
    assertFalse(board.isFascistVictory());
    board.enactPolicy(fP);
    // test after 6 liberal policy, liberal would win
    assertTrue(board.isFascistVictory());
  }

  @Test
  void hasActivatedPowerNone() {
    // 0 fp and 3 lp but no power
    for (int i = 0; i < 3; i++) {
      board.enactPolicy(lP);
    }
    assertNotNull(board.getActivatedPower());
    assertEquals(board.getActivatedPower(), PresidentialPower.NONE);
    // 2 fp no power
    for (int i = 0; i < 2; i++ ) {
      board.enactPolicy(fP);
    }
    assertEquals(board.getActivatedPower(), PresidentialPower.NONE);
    assertFalse(board.hasActivatedPower());

  }

  @Test
  void hasActivatedPowerPeek() {
    // 3 fp peek power
    for (int i = 0; i < 3; i++ ) {
      board.enactPolicy(fP);
    }
    assertEquals(board.getActivatedPower(), PresidentialPower.PEEK);
    assertTrue(board.hasActivatedPower());
  }

  @Test
  void hasActivatedPowerExecution() {
    // 4 fp execution power
    for (int i = 0; i < 4; i++ ) {
      board.enactPolicy(fP);
    }
    assertEquals(board.getActivatedPower(), PresidentialPower.EXECUTION);
    // 5 fp execution power
    board.enactPolicy(fP);
    assertEquals(board.getActivatedPower(), PresidentialPower.EXECUTION);
  }

  @Test
  void fascistsCantWinByElection() {
    for (int i = 0; i < 2; i++ ) {
      board.enactPolicy(fP);
    }
    // when there are only 3 or more fascist policy, fascist cant win by election
    assertFalse(board.fascistsCanWinByElection());
  }

  @Test
  void fascistsCanWinByElection() {
    for (int i = 0; i < 3; i++ ) {
      board.enactPolicy(fP);
    }
    // when there are only 3 or more fascist policy, fascist cant win by election
    assertTrue(board.fascistsCanWinByElection());
  }
}
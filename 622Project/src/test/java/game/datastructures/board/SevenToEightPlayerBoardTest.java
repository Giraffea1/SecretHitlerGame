package game.datastructures.board;

import game.datastructures.Policy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SevenToEightPlayerBoardTest {
  Board board;
  Policy fP;
  Policy lP;

  @BeforeEach
  void setUp() {
    board = new SevenToEightPlayerBoard();
    fP = new Policy(Policy.Type.FASCIST);
    lP = new Policy(Policy.Type.LIBERAL);
  }

  @Test
  void hasActivatedPowerNone() {
    // 0 fp and 3 lp but no power
    for (int i = 0; i < 3; i++) {
      board.enactPolicy(lP);
    }
    assertEquals(board.getActivatedPower(), PresidentialPower.NONE);
    // 1 fp no power
    for (int i = 0; i < 1; i++ ) {
      board.enactPolicy(fP);
    }
    assertEquals(board.getActivatedPower(), PresidentialPower.NONE);
    assertFalse(board.hasActivatedPower());

  }

  @Test
  void hasActivatedPowerInvestigate() {
    // 3 fp investigate power
    for (int i = 0; i < 2; i++ ) {
      board.enactPolicy(fP);
    }
    assertEquals(board.getActivatedPower(), PresidentialPower.INVESTIGATE);
    assertTrue(board.hasActivatedPower());
  }

  @Test
  void hasActivatedPowerElection() {
    // 3 fp election power
    for (int i = 0; i < 3; i++ ) {
      board.enactPolicy(fP);
    }
    assertEquals(board.getActivatedPower(), PresidentialPower.ELECTION);
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
}
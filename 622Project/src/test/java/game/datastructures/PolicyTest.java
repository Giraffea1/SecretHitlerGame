package game.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolicyTest {

  Policy fascistPolicy;
  Policy liberalPolicy;

  @BeforeEach
  void setUp() {
    fascistPolicy = new Policy(Policy.Type.FASCIST);
    liberalPolicy = new Policy(Policy.Type.LIBERAL);
  }

  @Test
  void getType() {
    assertEquals(Policy.Type.FASCIST, fascistPolicy.getType());
    assertEquals(Policy.Type.LIBERAL, liberalPolicy.getType());
  }
}
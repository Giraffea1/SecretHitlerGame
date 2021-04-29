package game.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  Player player;

  @BeforeEach
  void setUp() {
    player = new Player("Melon");

  }

  @Test
  void testGetUsername() {
    assertEquals("Melon", player.getUsername());
  }

  @Test
  void testSetIdentity() {
    player.setIdentity(Identity.HITLER);
    assertEquals(true, player.isHitler());
  }

  @Test
  void testIsHitlerTrue() {
    player.setIdentity(Identity.HITLER);
    assertEquals(true, player.isHitler());
  }

  @Test
  void testIsHitlerfalse() {
    player.setIdentity(Identity.FASCIST);
    assertEquals(false, player.isHitler());
  }

  @Test
  void testKill() {
    player.setIdentity(Identity.LIBERAL);
    player.kill();
    assertEquals(false, player.isAlive());
  }

  @Test
  void testIsAliveTrue() {
    player.setIdentity(Identity.FASCIST);
    assertEquals(true, player.isAlive());

  }

  @Test
  void testIsAliveFalse() {
    player.setIdentity(Identity.LIBERAL);
    player.kill();
    assertEquals(false, player.isAlive());

  }

  @Test
  void testInvestigate() {
    player.investigate();
    assertEquals(true, player.hasBeenInvestigated());
  }

  @Test
  void testHasBeenInvestigatedTrue() {
    player.investigate();
    assertEquals(true, player.hasBeenInvestigated());
  }

  @Test
  void testHasBeenInvestigatedFalse() {
    assertEquals(false, player.hasBeenInvestigated());
  }

  @Test
  void testisFascistHilter() {
    player.setIdentity(Identity.HITLER);
    assertEquals(true, player.isFascist());
  }

  @Test
  void testisFascist() {
    player.setIdentity(Identity.FASCIST);
    assertEquals(true, player.isFascist());
  }

  @Test
  void testisFascistFalse() {
    player.setIdentity(Identity.LIBERAL);
    assertEquals(false, player.isFascist());
  }
}
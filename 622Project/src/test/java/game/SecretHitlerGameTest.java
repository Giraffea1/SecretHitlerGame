package game;

import game.datastructures.Policy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class SecretHitlerGameTest {

  SecretHitlerGame game;
  SecretHitlerGame correctGame;
  SecretHitlerGame gameAfterVote;

  @BeforeEach
  void setUp() {
    ArrayList<String> playerNames = new ArrayList<>();
    playerNames.add("Om");
    playerNames.add("Tommy");
    playerNames.add("Theo");
    playerNames.add("Forty");
    playerNames.add("Fiver");
    playerNames.add("Sam");
    correctGame = new SecretHitlerGame(playerNames);

    gameAfterVote = new SecretHitlerGame(playerNames);
    gameAfterVote.nominateChancellor("Sam");
    gameAfterVote.registerVote("Om", TRUE);
    gameAfterVote.registerVote("Tommy", TRUE);
    gameAfterVote.registerVote("Theo", TRUE);
    gameAfterVote.registerVote("Forty", TRUE);
    gameAfterVote.registerVote("Fiver", TRUE);
    gameAfterVote.registerVote("Sam", TRUE);
  }

  @Test
  void newGameNotStarted() {
    Assertions.assertThrows(NullPointerException.class, () -> {
      game.getElectionTracker();
    });
  }

  @Test
  void newGameMinPlayer() {
    ArrayList<String> playerNames = new ArrayList<>();
    playerNames.add("one");
    playerNames.add("two");
    playerNames.add("three");
    playerNames.add("four");
    playerNames.add("five");
    game = new SecretHitlerGame(playerNames);
    assertEquals(0, game.getElectionTracker());
    for (int i = 0; i < 5; i++) {
      assertTrue(playerNames.get(i).equals(game.getPlayerList().get(i).getUsername()));
    }
  }

  @Test
  void newGameMaxPlayer() {
    ArrayList<String> playerNames = new ArrayList<>();
    playerNames.add("one");
    playerNames.add("two");
    playerNames.add("three");
    playerNames.add("four");
    playerNames.add("five");
    playerNames.add("six");
    playerNames.add("seven");
    playerNames.add("eight");
    playerNames.add("nine");
    playerNames.add("ten");
    game = new SecretHitlerGame(playerNames);
    assertEquals(0, game.getElectionTracker());
    for (int i = 0; i < 5; i++) {
      assertTrue(playerNames.get(i).equals(game.getPlayerList().get(i).getUsername()));
    }
  }

  @Test
  void newGameLessThanMinPlayer() {
    ArrayList<String> playerNames = new ArrayList<>();
    playerNames.add("one");
    playerNames.add("two");
    playerNames.add("three");
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      game = new SecretHitlerGame(playerNames);
    });
  }

  @Test
  void newGameMoreThanMaxPlayer() {
    ArrayList<String> playerNames = new ArrayList<>();
    playerNames.add("one");
    playerNames.add("two");
    playerNames.add("three");
    playerNames.add("four");
    playerNames.add("five");
    playerNames.add("six");
    playerNames.add("seven");
    playerNames.add("eight");
    playerNames.add("nine");
    playerNames.add("ten");
    playerNames.add("eleven");
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      game = new SecretHitlerGame(playerNames);
    });
  }


  @Test
  void hasPlayer() {
    ArrayList<String> playerNames = new ArrayList<>();
    playerNames.add("Om");
    playerNames.add("Tommy");
    playerNames.add("Theo");
    playerNames.add("Forty");
    playerNames.add("Fiver");
    playerNames.add("Sam");
    game = new SecretHitlerGame(playerNames);
    for (int i = 0; i < 6; i++) {
      assertTrue(game.hasPlayer(playerNames.get(i)));
    }
    assertFalse(game.hasPlayer("No such player"));
  }

  @Test
  void getState() {
    assertEquals(GameState.CHANCELLOR_NOMINATION, correctGame.getState());
  }

  @Test
  void nominateChancellor() {
    correctGame.nominateChancellor("Sam");
    assertEquals(GameState.CHANCELLOR_VOTING, correctGame.getState());
  }

  @Test
  void nominateChancellorLastChancellor() {
    // TODO: run game flow and nominate Sam again, should throw IllegalArgument use gameAfterVote

  }

  @Test
  void nominateChancellorIllegalState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      gameAfterVote.nominateChancellor("Sam");
    });
  }

  @Test
  void nominateChancellorKilled() {
    correctGame.getPlayerList().get(2).kill();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      correctGame.nominateChancellor("Theo");
    });
  }

  @Test
  void nominateChancellorNotExisting() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      correctGame.nominateChancellor("NotExistingPlayer");
    });
  }

  @Test
  void registerVoteSuccessful() {
    assertEquals(GameState.LEGISLATIVE_PRESIDENT, gameAfterVote.getState());
  }

  @Test
  void registerVoteNotFinished() {
    correctGame.nominateChancellor("Sam");
    correctGame.registerVote("Om", TRUE);
    correctGame.registerVote("Tommy", TRUE);
    assertEquals(GameState.CHANCELLOR_VOTING, correctGame.getState());
  }

  @Test
  void registerVoteNotApproved() {
    correctGame.nominateChancellor("Sam");
    correctGame.registerVote("Om", FALSE);
    correctGame.registerVote("Tommy", FALSE);
    correctGame.registerVote("Theo", FALSE);
    correctGame.registerVote("Forty", FALSE);
    correctGame.registerVote("Fiver", FALSE);
    correctGame.registerVote("Sam", FALSE);
    assertEquals(GameState.POST_LEGISLATIVE, correctGame.getState());
  }

  @Test
  void registerVoteLeadsFascistVictory() {
    //TODO: fascists win
  }

  @Test
  void registerVotePlayerNotInGame() {
    correctGame.nominateChancellor("Sam");
    correctGame.registerVote("Om", TRUE);
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      correctGame.registerVote("NotExistingMan", TRUE);
    });
  }

  @Test
  void registerVoteVoteTwice() {
    correctGame.nominateChancellor("Sam");
    correctGame.registerVote("Om", TRUE);
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.registerVote("Om", TRUE);
    });
  }

  @Test
  void registerVoteWrongState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.registerVote("Om", TRUE);
    });
  }

  @Test
  void endPresidentialTermWrongState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.endPresidentialTerm();
    });
  }

  @Test
  void endPresidentialTerm() {
    correctGame.nominateChancellor("Sam");
    correctGame.registerVote("Om", FALSE);
    correctGame.registerVote("Tommy", FALSE);
    correctGame.registerVote("Theo", FALSE);
    correctGame.registerVote("Forty", FALSE);
    correctGame.registerVote("Fiver", FALSE);
    correctGame.registerVote("Sam", FALSE);
    correctGame.endPresidentialTerm();
    assertEquals(GameState.CHANCELLOR_NOMINATION, correctGame.getState());
  }

  @Test
  void endPresidentialTermBranch() {
    //TODO: finish all branches
  }

  @Test
  void getPresidentLegislativeChoices() {
    List<Policy> list = gameAfterVote.getPresidentLegislativeChoices();
    for (Policy p : list) {
      assertTrue(p.getType().equals(Policy.Type.FASCIST) || p.getType().equals(Policy.Type.LIBERAL));
//      System.out.println(p.getType());
    }
//    System.out.println(correctGame.getPresidentLegislativeChoices().toString());
  }

  @Test
  void getPresidentLegislativeChoicesWrongState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.getPresidentLegislativeChoices();
    });
  }

  @Test
  void presidentDiscardPolicyIllegalState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.presidentDiscardPolicy(0);
    });
  }

  @Test
  void presidentDiscardPolicyIndexOutOfBound() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.presidentDiscardPolicy(4);
    });
  }

  @Test
  void presidentDiscardPolicyIndexOutOfBoundNegative() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.presidentDiscardPolicy(-9);
    });
  }

  @Test
  void presidentDiscardPolicyIndexOutOfBoundMax() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.presidentDiscardPolicy(Integer.MAX_VALUE);
    });
  }

  @Test
  void presidentDiscardPolicyIndexOutOfBoundMin() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.presidentDiscardPolicy(Integer.MIN_VALUE);
    });
  }

  @Test
  void presidentDiscardPolicy() {
    gameAfterVote.presidentDiscardPolicy(0);
    assertEquals(GameState.LEGISLATIVE_CHANCELLOR, gameAfterVote.getState());
  }

  @Test
  void chancellorEnactPolicy() {
    gameAfterVote.presidentDiscardPolicy(0);
    gameAfterVote.chancellorEnactPolicy(0);
    assertEquals(GameState.POST_LEGISLATIVE, gameAfterVote.getState());
  }

  @Test
  void chancellorEnactPolicyIllegalState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.chancellorEnactPolicy(0);
    });
  }

  @Test
  void chancellorEnactPolicyIndexOutOfBound() {
    gameAfterVote.presidentDiscardPolicy(0);
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.chancellorEnactPolicy(3);
    });
  }

  @Test
  void chancellorEnactPolicyNegativeIndex() {
    gameAfterVote.presidentDiscardPolicy(0);
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.chancellorEnactPolicy(-9);
    });
  }

  @Test
  void chancellorEnactPolicyMin() {
    gameAfterVote.presidentDiscardPolicy(0);
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.chancellorEnactPolicy(Integer.MIN_VALUE);
    });
  }

  @Test
  void chancellorEnactPolicyMax() {
    gameAfterVote.presidentDiscardPolicy(0);
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      gameAfterVote.chancellorEnactPolicy(Integer.MAX_VALUE);
    });
  }


  @Test
  void chancellorVeto() {
    //TODO: state = LEGISLATIVE_CHANCELLOR
  }

  @Test
  void chancellorVetoIllegalState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.chancellorVeto();
    });
  }

  @Test
  void presidentialVeto() {
    //TODO; state = LEGISLATIVE_PRESIDENT_VETO
  }

  @Test
  void presidentialVetoIllegalState() {
    Assertions.assertThrows(IllegalStateException.class, () -> {
      correctGame.presidentialVeto(TRUE);
    });
  }

  @Test
  void endPeek() {
  }

  @Test
  void investigatePlayer() {
  }

  @Test
  void executePlayer() {
  }

  @Test
  void electNextPresident() {
  }
}
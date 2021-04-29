package server.util;

import game.SecretHitlerGame;
import game.datastructures.Policy;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameToJSONConverterTest {
  List<String> users;
  SecretHitlerGame game;
  List<Policy> policies;
  GameToJSONConverter converter;
  JSONObject gameJson;

  @BeforeEach
  void setUp() {
    converter = new GameToJSONConverter();
    users = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
    game = new SecretHitlerGame(users);
    policies = new ArrayList<>();
    for (int i =0 ; i < 3; i ++) {
      policies.add(new Policy(Policy.Type.LIBERAL));
      policies.add(new Policy(Policy.Type.FASCIST));
    }
  }

  @Test
  void convertNull() {
    assertThrows(NullPointerException.class, () -> GameToJSONConverter.convert(null));
  }


  @Test
  void convert() {
    gameJson = converter.convert(game);
    String exectedGameString = "{\"election-tracker-advanced\":false,\"liberal-policies\":0,\"fascist-policies\":0,\"last-state\":\"SETUP\",\"discard-size\":0,\"draw-size\":17,\"players\":{\"a\":{\"alive\":true,\"id\":\"LIBERAL\",\"investigated\":false},\"b\":{\"alive\":true,\"id\":\"LIBERAL\",\"investigated\":false},\"c\":{\"alive\":true,\"id\":\"FASCIST\",\"investigated\":false},\"d\":{\"alive\":true,\"id\":\"HITLER\",\"investigated\":false},\"e\":{\"alive\":true,\"id\":\"LIBERAL\",\"investigated\":false}},\"player-order\":[\"a\",\"b\",\"c\",\"d\",\"e\"],\"state\":\"CHANCELLOR_NOMINATION\",\"president\":\"a\",\"election-tracker\":0,\"user-votes\":{}}\n";
    assertEquals("0", gameJson.get("liberal-policies").toString());
    assertEquals("0", gameJson.get("fascist-policies").toString());
    assertEquals("SETUP", gameJson.get("last-state").toString());
    assertFalse(gameJson.has("president-choices"));
  }

//  @Test
//  void convertState() {
//  }

  @Test
  void convertPolicyListToStringArray() {
    String[] arr = new String[]{"LIBERAL", "FASCIST", "LIBERAL", "FASCIST", "LIBERAL", "FASCIST"};
    assertArrayEquals(arr, GameToJSONConverter.convertPolicyListToStringArray(policies));
  }
}
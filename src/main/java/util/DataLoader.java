package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Characters;
import model.Enemies;
import model.Spells;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataLoader {

    public static List<Characters> loadCharacters() {
        List<Characters> characters = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = DataLoader.class.getResourceAsStream("/Data/characters.json");
            List<Map<String, Object>> rawList = mapper.readValue(is, new TypeReference<>() {});
            for (Map<String, Object> map : rawList) {
                String name = (String) map.get("name");
                int maxHp = (int) map.get("maxHp");
                int currentHp = (int) map.get("currentHp");
                int ac = (int) map.get("ac");
                int dmg = (int) map.get("dmg");
                String imagePath = (String) map.get("imagePath");
                String spellName = (String) map.get("spell");

                Spells spell = Spells.getByName(spellName);
                characters.add(new Characters(name, maxHp, currentHp, ac, dmg, imagePath, spell));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return characters;
    }

    public static List<Enemies> loadEnemies() {
        List<Enemies> enemies = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = DataLoader.class.getResourceAsStream("/Data/enemies.json");
            List<Map<String, Object>> rawList = mapper.readValue(is, new TypeReference<>() {});
            for (Map<String, Object> map : rawList) {
                String name = (String) map.get("name");
                int maxHp = (int) map.get("maxHp");
                int currentHp = (int) map.get("currentHp");
                int ac = (int) map.get("ac");
                int dmg = (int) map.get("dmg");
                String imagePath = (String) map.get("imagePath");

                enemies.add(new Enemies(name, maxHp, currentHp, ac, dmg, imagePath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enemies;
    }
}

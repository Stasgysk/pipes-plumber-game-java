package sk.tuke.goose.gamestudio.pipes.game.level.save;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sk.tuke.goose.gamestudio.pipes.game.entities.Tile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveLevelToJson {
    public SaveLevelToJson(List<Tile> listOfTiles) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(listOfTiles);
            save(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void save(String json) {
        try {
            FileWriter myWriter = new FileWriter("C:\\Java\\Pipes\\src\\main\\resources\\filename.json");
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

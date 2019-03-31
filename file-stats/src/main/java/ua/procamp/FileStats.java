package ua.procamp;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
public class FileStats {

    private final Map<Character, Integer> charCountMap;

    public FileStats(String fileName) {
        charCountMap = createMapOfCharacters(fileName);
    }
    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName){
        return new FileStats(fileName);
    }

    public Map<Character, Integer> createMapOfCharacters(String fileName) {
        Map<Character, Integer> map = new HashMap<>();
        try (InputStream reader = getClass().getClassLoader().getResource(fileName).openStream()){
            int symbol;
            while ((symbol = reader.read()) > -1) {
                if (symbol != ' '){
                    map.put((char) symbol, map.get((char)symbol) == null ? 1 : map.get((char)symbol) + 1);
                }
            }
        } catch (IOException | NullPointerException e) {
            throw new FileStatsException(e.getMessage());
        }
        return map;
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        return charCountMap.get(character);
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        return charCountMap.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        return charCountMap.containsKey(character);
    }
}

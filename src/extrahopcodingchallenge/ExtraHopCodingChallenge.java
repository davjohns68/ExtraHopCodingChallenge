/*
 * Finds words in an 8x8 grid of letters by
 * using a knight's moves
 * Created by David Johnson, October 3, 2017
 * for ExtraHop Networks interview
 */
package extrahopcodingchallenge;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraHopCodingChallenge {

    private static void findWordsInGrid(char[][] table, Set<String> words) {
        
        ArrayList<TablePoint> firstLetterPositions;
        // Store words we find to gather statistics
        Map<String, TablePoint> foundWords = new HashMap<>();
        String longestWord = "";
        int longestWordLength = 0;
        
        for(String word: words) {
            firstLetterPositions = findFirstLetter(table, word);
            for(TablePoint flp: firstLetterPositions) {
                if(findWord(table, flp, word)) {
                    foundWords.put(word, flp);
                    if(word.length() > longestWordLength) {
                        longestWord = word;
                        longestWordLength= word.length();
                    }
                }
            }
        }
        if(longestWordLength != 0) {
            System.out.println("Longest word found was: \"" + longestWord + "\" at " + longestWordLength + " characters.");
            System.out.println("Found a total of " + foundWords.size() + " words");
        } else {
            System.out.println("No words found in grid.");
        }
    }
    
    private static boolean findWord(char[][] table, TablePoint tp, String word) {
        // Turn the String into a char array
        char[] wordArray = word.toCharArray();
        // A list to store the point of each found letter
        ArrayList<TablePoint> wordPointsFound = new ArrayList<>();
        // Make a copy of the current point so we don't mess up the master list
        TablePoint currentPoint = new TablePoint(tp.getY(), tp.getX());
        
        //System.out.println("Starting position: " + currentPoint);
        // Named the loop for clarity of the continue statment
        mainLoop: for(char letter: wordArray) {
            // Skipping the first letter since we already know where it is
            if(letter == wordArray[0]) {
                continue mainLoop;
            }
            // Get a list of the next possible points to check
            ArrayList<TablePoint> nextPoints = findNextPoints(currentPoint);
            // Iterate through all the points to look for a match
            for(TablePoint nextPoint: nextPoints) {
                // We found the next letter!
                if(table[nextPoint.getY()][nextPoint.getX()] == letter) {
                    // Add the point to the list
                    wordPointsFound.add(nextPoint);
                    // Set the next starting point
                    currentPoint = nextPoint;
                    continue mainLoop;
                }
            }
            if(wordPointsFound.size() < word.length()) {
                return false;
            }
        }
        return true;
    }
    
    private static ArrayList<TablePoint> findNextPoints(TablePoint tp) {
        // There are 8 possible "moves" that can be made
        /*
            1-2. 2 up, 1 right/left (y-2, x+-1)
            3-4. 1 up, 2 right/left (y-1, x+-2)
            5-6. 2 down, 1 right/left (y+2, x+-1)
            7-8. 1 down, 2 right/left (y+1, x+-2)
        */
        ArrayList<TablePoint> nextPoints = new ArrayList<>();
        ArrayList<TablePoint> potentialMoves = new ArrayList<>();
        
        // Move 1
        potentialMoves.add(new TablePoint(tp.getY() - 2, tp.getX() + 1));
        // Move 2
        potentialMoves.add(new TablePoint(tp.getY() - 2, tp.getX() - 1));
        // Move 3
        potentialMoves.add(new TablePoint(tp.getY() - 1, tp.getX() + 2));
        // Move 4
        potentialMoves.add(new TablePoint(tp.getY() - 1, tp.getX() - 2));
        // Move 5
        potentialMoves.add(new TablePoint(tp.getY() + 2, tp.getX() + 1));
        // Move 6
        potentialMoves.add(new TablePoint(tp.getY() + 2, tp.getX() - 1));
        // Move 7
        potentialMoves.add(new TablePoint(tp.getY() + 1, tp.getX() + 2));
        // Move 8
        potentialMoves.add(new TablePoint(tp.getY() + 1, tp.getX() - 2));
        
        // If the point is "in bounds," add it to the array to return
        for(TablePoint potentialMove: potentialMoves) {
            if(isMoveValid(potentialMove)) {
                nextPoints.add(potentialMove);
            }
        }
        
        return nextPoints;
    }
    
    private static boolean isMoveValid(TablePoint tp) {
        if(tp.getY() >= 0 && tp.getY() <= 7 && tp.getX() >= 0 && tp.getX() <= 7) {
            return true;
        } else {
            return false;
        }
    }
    
    private static ArrayList<TablePoint> findFirstLetter(char[][] table, String word) {
        
        ArrayList<TablePoint> firstLetterPositions = new ArrayList<>();
        char[] wordArray = word.toCharArray();

        for(int y = 0; y <= 7; y++) {
            for(int x = 0; x <= 7; x++) {
                if(table[y][x] == wordArray[0]) {
                    TablePoint tp = new TablePoint(y, x);
                    firstLetterPositions.add(tp);
                }
            }
        }
        
        return firstLetterPositions;
    }
    
    private static void loadWordsList(Set<String> words) throws FileNotFoundException{
        Scanner data = new Scanner(new File("loveslabourslost.txt"));
        
        while(data.hasNext()) {
            String newWord = data.next().toLowerCase();
            // Set up the regex to pull the word away from any punctuation
            Pattern pattern = Pattern.compile("([a-z]+)");
            Matcher matcher = pattern.matcher(newWord);
            // Group 1 contains anything that is a letter (therefore a word)
            if(matcher.matches()) {
                words.add(matcher.group(1));
            }
        }
    }
    
    public static void main(String[] args) {
        // Here is our 8x8 table
        char table[][] = {{'e','x','t','r','a','h','o','p'}, {'n','e','t','w','o','r','k','s'},
                        {'q','i','h','a','c','i','q','t'}, {'l','f','u','n','u','r','x','b'},
                        {'b','w','d','i','l','a','t','v'}, {'o','s','s','y','n','a','c','k'},
                        {'q','w','o','p','m','t','c','p'}, {'k','i','p','a','c','k','e','t'}
                    };
        // Here is the data structure for our words
        Set<String> words = new TreeSet<>();
    
        // Try to load the words from the file
        try {
            loadWordsList(words);
        } catch(FileNotFoundException e) {
            System.out.println("Error: File not found!");
        }
        if(!words.isEmpty()) {
            findWordsInGrid(table, words);
        }
    }   
}
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class Highscore implements Serializable {
    private int score;
    private String name;
    private ArrayList<Highscore> scores = new ArrayList<Highscore>();
    private String highscoreFile = "highscore.txt";

    Comparator<Highscore> scoresCompare = new Comparator<Highscore>() {
        @Override
        public int compare(Highscore one, Highscore two) {
            if (one.getScore() < two.getScore())
                return 1;
            else
                return -1;
        }
    };

    public Highscore() {

    }

    public Highscore(int aScore, String aName) {
        score = aScore;
        name = aName;
    }

    public void add(Highscore s) {
        scores.add(s);
    }

    public void sortList() {
        Collections.sort(scores, scoresCompare);
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    public void init(){
        this.read();
    }

    public void calculateScore(int lines) {
        if (lines != 0)
            if (lines == 1) {
                score += 1;
            } else {
                score += (int) Math.pow(2, lines - 1);
            }
    }

    public void write() {
        try {
            FileWriter fw = new FileWriter(highscoreFile);
            BufferedWriter output = new BufferedWriter(fw);
            int sz = scores.size();
            for (int i = 0; i < sz; i++) {
                output.write(scores.get(i).toString());
                output.newLine();
            }
            output.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void reset() {
        scores = new ArrayList<Highscore>();
    }

    public void read() {
            try {
                Scanner in = new Scanner(new File(highscoreFile));
                while (in.hasNextLine()) {
                    while (in.hasNext()) {
                        //System.out.println("Got this far");
                        String aName = in.next();
                        int aScore = in.nextInt();
                            scores.add(new Highscore(aScore, aName));
                        in.nextLine();
                    }
                }
            } catch (FileNotFoundException e) {
                //System.out.println("You dun goofed.");
            }
    }

    public String toString() {
        return getName() + "\t" + "\t" + getScore();
    }

    public void newArrayList() {
        scores = new ArrayList<Highscore>();
    }

    public ArrayList<Highscore> getScores() {
        return scores;
    }

    public void saveScore(Highscore s) throws ClassNotFoundException, IOException, EOFException, FileNotFoundException {
        scores.add(s);
        Collections.sort(scores, scoresCompare);
        write();
    }

    public String getHighscoreByRank(int rank) {

        String scoreString;
        if (this.getScores().toArray().length > 0 && rank - 1 < this.getScores().toArray().length) {
            scoreString = getScores().get(rank - 1).toString().replaceAll("[\\n\\t ]", " ");
        }
        else {
            scoreString = "Empty";
        }
        return scoreString;
    }
    public int getHighscoreNumberByRank(int rank){
        int scoreNumberByRank=0;
        if (this.getScores().toArray().length > 0 && rank - 1 < this.getScores().toArray().length) {
            scoreNumberByRank=getScores().get(rank-1).getScore();
            }
        return scoreNumberByRank;
    }

}

    /*

    */
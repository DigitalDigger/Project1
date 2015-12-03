import java.io.*;
import java.util.Arrays;

public class Highscore {
    private int[] highscoreList = new int[6];
    private int currentScore;
    private String highscoreFile = "highscore.txt";
    private String name;

    public Highscore() {
    }
    public Highscore(String name) {
        highscoreList = readHighscoreFile();
        currentScore = 0;
        this.name = name;
    }

    public int getHighscore(int rank) {
        return this.highscoreList[rank - 1];
    }

    public int[] getHighscoreList() {
        return this.highscoreList;
    }

    public void calculateScore(int lines) {
        if (lines != 0)
            currentScore += (int) Math.pow(3, lines);
    }

    public void initHighscores() {
        readHighscoreFile();
        sortHighscore();
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void sortHighscore() {

        Arrays.sort(highscoreList);

        /* Sort the highscores from high a score to a low score */
        for (int k = 0; k < highscoreList.length / 2; k++) {
            int temp = highscoreList[k];
            highscoreList[k] = highscoreList[highscoreList.length - k - 1];
            highscoreList[highscoreList.length - k - 1] = temp;
        }
    }

    public int[] readHighscoreFile() {
        int[] highscore = new int[6];
        // This will reference one line at a time
        String line = null;
        File file = new File(highscoreFile);
        try {

            int i = 4;

            if (!file.isFile()) {
                file.createNewFile();
            }
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(highscoreFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null && i >= 0) {
                int score = 0;
                try {
                    score = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                }

                highscore[i] = score;
                i--;
            }


            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            highscoreFile + "'");
        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return highscore;
    }

    public void addCurrentScore() {
        highscoreList[5] = currentScore;
        sortHighscore();
    }

    public void writeToHighscores() {
        try {
            // input the file content to the String "input"
            String input = "";

            for (int i = 1; i <= 5; i++) {
                input += getHighscore(i) + System.getProperty("line.separator");
            }
            // write the new String with the replaced line OVER the same file
            new File(highscoreFile).mkdirs();
            FileOutputStream fileOut = new FileOutputStream(highscoreFile);
            fileOut.write(input.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file. " + highscoreFile);
            System.out.println(e);
        }
    }

    public void writeToHighscores2() {

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                    new FileWriter(highscoreFile);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            //for(int i=1; i<=5; i++) {
            bufferedWriter.write(getHighscore(1));
            //bufferedWriter.newLine();
            // }


            // Always close files.
            bufferedWriter.close();

        } catch (IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + highscoreFile + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

    }

}
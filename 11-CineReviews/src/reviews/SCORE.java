package reviews;

/**
 * Classification's score
 */
public enum SCORE {
    terrible(1),
    poor(2),
    average(3),
    good(4),
    excellent(5);

    //CONSTANT
    private final int score;

    //CONSTRUCTOR
    SCORE(int score) {
        this.score = score;
    }

    //METHOD

    /**
     * Returns the score based on classification
     * @return score based on classification
     */
    public int getScore() {
        return score;
    }
}

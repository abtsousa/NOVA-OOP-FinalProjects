package reviews;

import users.*;

/**
 * GenericReview - posted review to comment and classify a show
 */
public class GenericReview implements Review {
    //VARIABLES
    private final Reviewer reviewer;
    private final String comment;
    private final String classification;

    //CONSTRUCTOR

    /**
     * Posted review to comment and classify a show
     * @param reviewer review's author
     * @param comment review's comment
     * @param classification review's classification
     */
    public GenericReview(Reviewer reviewer, String comment, String classification) {
        this.reviewer = reviewer;
        this.comment = comment;
        this.classification = classification;
    }

    //METHODS
    @Override
    public Reviewer getReviewer() {
        return reviewer;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public String getClassification() {
        return classification;
    }

    @Override
    public int getScore() { return SCORE.valueOf(classification).getScore();}

    @Override
    public int getWeight() {return reviewer.getWeight();}

}

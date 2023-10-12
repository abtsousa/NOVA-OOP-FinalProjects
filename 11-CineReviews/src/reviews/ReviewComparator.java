package reviews;

import users.CriticReviewer;

import java.util.Comparator;

/**
 * ReviewComparator - lists the reviews of a show by showing first the critics
 * and then the audience members, sorted from the highest to the lowest score and then alphabetically
 * by username
 */

public class ReviewComparator implements Comparator<Review> {
    @Override
    public int compare(Review o1, Review o2) {
        //1st parameter -- reviewer is critic
        boolean o1critic = o1.getReviewer() instanceof CriticReviewer;
        boolean o2critic = o2.getReviewer() instanceof CriticReviewer;
        if (o1critic && !o2critic) return -1;
        else if (!o1critic && o2critic) return 1;
        else {
            //2nd parameter -- review score
            int o1score = o1.getScore();
            int o2score = o2.getScore();
            if (o1score != o2score) return o2score-o1score;
            //3rd parameter -- reviewer name (alphabetically)
            else return o1.getReviewer().compareTo(o2.getReviewer());
        }
    }
}

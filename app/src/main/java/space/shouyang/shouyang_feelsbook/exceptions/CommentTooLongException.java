
package space.shouyang.shouyang_feelsbook.exceptions;

/**
 *  Purpose:
 *      Should be called when the comment of a feeling record is greater than the 100 characters
 *      or length specified in the class.
 *
 *  Design Rationale:
 *      Done as in labs, better to provide a clear exception for validators to express when needed.
 */
public class CommentTooLongException extends Exception {
}

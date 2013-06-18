/**
 * 
 */
package io.nextweb.engine.persistence;

/**
 * 
 * @author Max Rohde
 * 
 */
public interface WhenCommitted {
    /**
     * To be called when all pending operations for the persistence connection
     * are successfully completed.
     */
    public void thenDo();

    /**
     * To be called if an unexpected failure occurs while commiting changes to
     * the persistence connection.
     * 
     * @param t
     */
    public void onFailure(Throwable t);
}
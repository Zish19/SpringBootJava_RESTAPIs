package com.college.studentmanagement.exception;

/**
 * Exception thrown when attempting to create a record with an existing identifier.
 */
public class DuplicateIdException extends RuntimeException {

    public DuplicateIdException(String message) {
        super(message);
    }
}

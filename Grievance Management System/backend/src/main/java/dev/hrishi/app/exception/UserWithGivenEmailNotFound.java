package dev.hrishi.app.exception;

public class UserWithGivenEmailNotFound extends RuntimeException{

    public UserWithGivenEmailNotFound() {
        super();
    }

    public UserWithGivenEmailNotFound(String error) {
        super(error);
    }
}

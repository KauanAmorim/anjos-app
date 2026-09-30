package com.nauakavlis.anjos_app.domain.exception;

public abstract class DomainException extends RuntimeException {
    protected DomainException(String message) { super(message); }
    public abstract DomainErrorCategory getCategory();
}
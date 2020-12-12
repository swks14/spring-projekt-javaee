package net.projekt.springboot.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Nie znaleziono " + id);
    }
}

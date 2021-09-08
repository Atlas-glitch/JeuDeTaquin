public interface Jeu {
    void succ(char action);
    boolean enable(char action);
    void pred(char action);
    void reset();
    Jeu copy();
}

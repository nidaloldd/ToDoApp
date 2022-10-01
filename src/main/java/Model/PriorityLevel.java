package Model;

/**
 * This enum Class contains the PriorityLevels that a Cetli can get.
 * Red mean a very important task.
 * Yellow mean a moderately  important task.
 * Green mean a not so important task.
 */
public enum PriorityLevel {

    Red,
    Yellow,
    Green;

    @Override
    public String toString(){
        switch (this){
            case Red -> {
                return "Red";
            }
            case Yellow -> {
                return "Yellow";
            }
            case Green -> {
                return "Green";
            }
            default -> throw new IllegalArgumentException();

        }
    }
}

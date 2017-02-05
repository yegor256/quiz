package ch.fware.teamed.quiz;

public class NoFilter implements DataFilter {

    @Override
    public boolean isAllowedData(int data) {
        return true;
    }
}
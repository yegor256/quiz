package ch.fware.teamed.quiz;

public class RemoveUnicodeFilter implements DataFilter {

    @Override
    public boolean isAllowedData(int data) {
        return data < 0x80;
    }
}
public class UnicodeFilter extends Filter {

    @Override
    public Character filter(int data) {
        if (data < 0x80) {
            return (char) data;
        } else {
            return null;
        }
    }

}

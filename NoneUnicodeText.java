import java.io.IOException;

public class NoneUnicodeText implements Text {
    private final Text origin;

    public NoneUnicodeText (Text text) {
        this.origin = text;
    }
    @Override
    public String content() {
        String content = origin.content();

        StringBuffer output = new StringBuffer();
        for (int i = 0; i < content.length(); i++) {
            char character = content.charAt(i);
            if (character < 0x80) {
                output.append(character);
            }
        }
        return output.toString();
    }

    @Override
    public void saveContent(String content) {
        origin.saveContent(content);
    }
}

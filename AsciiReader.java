import java.util.Objects;

/**
 * AsciiReader
 *
 * @author Alexander Kontsur (a.kontsur)
 * @since 19.01.2016
 */
public class AsciiReader extends AbstractReader {

    @Override
    protected void apply(StringBuilder sb, int data) {
        if (Objects.equals(Character.UnicodeBlock.of(data), Character.UnicodeBlock.BASIC_LATIN)) {
            sb.append((char) data);
        }
    }
}

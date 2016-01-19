/**
 * CommonReader
 *
 * @author Alexander Kontsur (a.kontsur)
 * @since 19.01.2016
 */
public class CommonReader extends AbstractReader {

    @Override
    protected void apply(StringBuilder sb, int data) {
        sb.append((char) data);
    }
}

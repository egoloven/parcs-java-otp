import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class DataEnc implements Serializable {
    public final String plaintext;
    public final byte[] key;

    public DataEnc(String plaintext, byte[] key) {
        this.plaintext = plaintext;
        this.key = key;
    }
}

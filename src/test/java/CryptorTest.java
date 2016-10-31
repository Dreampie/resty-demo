import cn.dreampie.common.util.crypto.Cryptor;
import org.junit.Test;

/**
 * Created by wangrenhui on 15/3/5.
 */
public class CryptorTest {
  @Test
  public void testCrypto() {
    String crypto = Cryptor.crypto("SHA-512", "shengmu");
    System.out.println(crypto);
  }
}

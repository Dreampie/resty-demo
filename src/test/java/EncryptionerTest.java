import cn.dreampie.common.util.Encryptioner;
import org.junit.Test;

/**
 * Created by wangrenhui on 15/3/5.
 */
public class EncryptionerTest {
  @Test
  public void testEncryption() {
    System.out.println(Encryptioner.sha512Encrypt("shengmu"));
  }
}

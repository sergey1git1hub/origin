import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by SChubuk on 15.12.2017.
 */
public class MavenFailsafePluginTest {

    @Test
    public static void test() throws InterruptedException, FindFailed, IOException {
        //System.out.println("Maven failsafe plugin test.060496");
        Methods.openCXphone(1000);
       // Assert.assertTrue(false);
    }
}

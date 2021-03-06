import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * Created by SChubuk on 04.10.2017.
 */
@Listeners(VideoListener.class)
public class PDProgressiveReleasedAgentHangup {
    static IEData data;
    static WebDriver driver;
    static boolean debug = true;

    @Test
    @Video
    public static void IELogin() throws InterruptedException, IOException {
        data = new IEData();
        data.group = "\\!test_group5_5220";
        driver = Methods.openWebphoneLoginPage(driver, data.browser, data.webphoneUrl);
        Methods.login(driver, data.method, data.username, data.group);
        Methods.checkStatus(driver, "Available", 60);
    }

    @Test(dependsOnMethods = "IELogin")
    @Video
    public static void changeStatusToAUX() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "AUX");
        Methods.checkStatus(driver, "AUX", 3);
    }

    @Test()
    @Video
    public static void loginToPD(){

    }

    @Test
    @Video
    public static void runPDCampaign(){

    }

    @Test(dependsOnMethods = "changeStatusToAUX")
    @Video
    public static void runSQLQuery() throws SQLException, ClassNotFoundException, InterruptedException, FindFailed, IOException {
        Methods.runSqlQuery("pd_5220copy", "94949");
        Methods.openCXphone(2000);
    }

    @Test(dependsOnMethods = "runSQLQuery")
    @Video
    public static void waitForCallOnClientSide(){
    }

    @Test(dependsOnMethods = "waitForCallOnClientSide")
    @Video
    public static void noIncomingCallToClient() throws InterruptedException {
        if(debug == true)
        Thread.sleep(5000);
        else Thread.sleep(20000);
    }

    @Test(dependsOnMethods = "noIncomingCallToClient")
    @Video
    public static void changeStatusToAvailable() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.changeStatus(driver, "Available");
        Methods.checkStatus(driver, "Available", 3);
    }

    @Test(dependsOnMethods = "changeStatusToAvailable")
    @Video
    public static void waitForCallOnClientSide2() throws FindFailed, InterruptedException {
        try{
        Methods.cxAnswer();
        } catch(Exception e){
            e.printStackTrace();
            WebDriver driverTemp = Methods.loginToPD();
            Methods.runPDCampaign(driverTemp, 257);
            Methods.cxAnswer();
        }
    }

    @Test(dependsOnMethods = "waitForCallOnClientSide2")
    @Video
    public static void receiveIncomingCallToAgent(){

    }

    @Test(dependsOnMethods = "receiveIncomingCallToAgent")
    @Video
    public static void agentHangup() throws InterruptedException, FindFailed {
        Methods.agentHangup(driver, 1);
    }

    @Test(dependsOnMethods = "agentHangup")
    @Video
    public static void setResultCodeAndCheckAvailableStatus() throws InterruptedException, FindFailed, UnknownHostException, UnsupportedEncodingException {
        Methods.setWebphoneResultCode(driver);
        Methods.checkStatus(driver, "Available", 3);
    }

    @AfterClass
    @Video
    public void teardown() throws IOException {
        boolean isIE = Methods.isIE(driver);
        driver.quit();

        if(isIE){
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        }
    }
}

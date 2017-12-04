package com.andrew.phantomjsdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author xinhuiyang
 * @date 2017/12/4
 */
public class JavaCodeDemo {
    public static void main(String[] args) {
        String[] cliArgs = new String[]{"--ssl-protocol=any", "--ignore-ssl-errors=true", "--web-security=false"};
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability("acceptSslCerts", true);
        dcaps.setCapability("cssSelectorsEnabled", true);
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgs);
        dcaps.setCapability("localToRemoteUrlAccessEnabled", true);
        dcaps.setJavascriptEnabled(true);
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");

        WebDriver driver = new PhantomJSDriver(dcaps);
        try {
//            driver.get("https://movie.douban.com/tag/#/?sort=T&range=0,10&tags=%E7%A7%91%E5%B9%BB");
            driver.get("http://movie.douban.com/explore");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("scrollTo(0,10000)");

            WebElement next = driver.findElement(By.className("more"));
            next.click();

        } catch (Exception e) {
            e.getStackTrace();
        }
        System.out.println(driver.getPageSource());
        driver.close();
    }
}

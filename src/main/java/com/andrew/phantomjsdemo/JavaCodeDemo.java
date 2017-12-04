package com.andrew.phantomjsdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author xinhuiyang
 * @date 2017/12/4
 */
public class JavaCodeDemo {
    public static void main(String[] args) {
        //支持https协议头的网页
        String[] cliArgs = new String[]{"--ssl-protocol=any", "--ignore-ssl-errors=true", "--web-security=false"};
        //设置必要参数
        DesiredCapabilities dCaps = new DesiredCapabilities();
        //ssl证书支持
        dCaps.setCapability("acceptSslCerts", true);
        //css搜索支持
        dCaps.setCapability("cssSelectorsEnabled", true);
        dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgs);
        dCaps.setCapability("localToRemoteUrlAccessEnabled", true);
        //js支持
        dCaps.setJavascriptEnabled(true);
        //支持截屏
        dCaps.setCapability("takesScreenshot", true);

        /*
        //若已配置过phantomjs环境变量,此处可省略
        //驱动支持
        dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");*/

        WebDriver driver = new PhantomJSDriver(dCaps);
        try {
            //访问页面
            driver.get("http://movie.douban.com/explore");

            //页面滚动到底部
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("scrollTo(0,10000)");

            //获取"加载更多"的Element位置
            WebElement next = driver.findElement(By.className("more"));
            //模拟一次页面点击
            next.click();
            //睡眠3秒,等待页面加载完成
            Thread.sleep(3000);
            //获取当前页面内容
            System.out.println(driver.getPageSource());
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            //记得在最后关闭driver
            driver.close();
        }
    }
}

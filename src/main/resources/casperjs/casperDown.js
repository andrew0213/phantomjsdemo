/**
 * Created by xinhuiyang on 2017/12/2.
 */
var casper = require('casper').create({
    //页面配置项(Casper使用的WebPage实例,会使用这些配置)
    pageSettings: {
        javascriptEnabled: true,//是否执行页面内的javascript(default true)
        XSSAuditingEnabled: false, //是否监控跨域请求(default false)
        loadImages: false, //是否加载页面图片(default true)。若无必须，可设为false
        loadPlugins: false, //是否加载 NPAPI 插件 (Flash, Silverlight, …)
        userAgent: "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) Appl" +
        "eWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36"
    },
    waitTimeout: 10000,//全局的timeout时间设置
    exitOnError: true,
    logLevel: "info",//日志级别
    verbose: false //是否实时输出log信息
});
//捕获resource加载错误信息
casper.on("resource.error", function (resourceError) {
    console.log('Unable to load resource (#' + resourceError.id + 'URL:' + resourceError.url + ')');
    console.log('Error code: ' + resourceError.errorCode + '. Description: ' + resourceError.errorString);
});
//捕获页面的console信息
casper.on('remote.message', function (msg) {
    this.log(msg, 'info');
});
//捕获错误信息
casper.on('error', function (msg, backtrace) {
    var msgStack = ['PHANTOM ERROR: ' + msg];
    if (backtrace && backtrace.length) {
        msgStack.push('TRACE:');
        backtrace.forEach(function (t) {
            msgStack.push(' -> ' + (t.file || t.sourceURL) + ': ' + t.line + (t.function ? ' (in function ' + t.function + ')' : ''));
        });
    }
    this.log(msgStack.join('\n'), "error");
});

casper.start('http://movie.douban.com/explore', function () {
    this.waitForSelector('.item', function () {                  //等到'.item'选择器匹配的元素出现时再执行回调函数
    }, function () {
        this.exit(); //失败时调用的函数,退出
    }, 3000);//等待时间
}).then(function () {
    this.scrollToBottom();//滚动页面到底部
}).then(function () {
    this.click('.more');//点击".more"元素
}).wait(3000, function() {
    this.echo(this.getHTML());//等待3秒以便页面加载完,然后输出html
}).then(function () {
    this.exit();//最后退出
})

casper.run();//前面代码只是确定的执行先后顺序,运行到此处时,才真正开始执行
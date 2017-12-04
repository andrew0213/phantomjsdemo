/**
 * Created by xinhuiyang on 2017/12/2.
 */
var casper = require('casper').create({
    pageSettings: {
        javascriptEnabled: true,
        XSSAuditingEnabled: true,
        loadImages: true,        // The WebPage instance used by Casper will
        loadPlugins: true,         // use these settings
        userAgent: "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36"
    },
    waitTimeout: 10000,
    exitOnError: true,
    logLevel: "debug",              // Only "info" level messages will be logged
    verbose: true
});
casper.on("resource.error", function(resourceError){
    console.log('Unable to load resource (#' + resourceError.id + 'URL:' + resourceError.url + ')');
    console.log('Error code: ' + resourceError.errorCode + '. Description: ' + resourceError.errorString);
});
casper.on('remote.message', function (msg) {
    this.log(msg, 'info');
});
casper.on('error', function (msg, backtrace) {
    var msgStack = ['PHANTOM ERROR: ' + msg];
    if (backtrace && backtrace.length) {
        msgStack.push('TRACE:');
        backtrace.forEach(function(t) {
            msgStack.push(' -> ' + (t.file || t.sourceURL) + ': ' + t.line + (t.function ? ' (in function ' + t.function +')' : ''));
        });
    }
    this.log(msgStack.join('\n'), "error");
});

casper.start('http://movie.douban.com/explore', function () {
    this.waitForSelector('.item', function() {                  //等到'.tweet-row'选择器匹配的元素出现时再执行回调函数
        this.echo(this.getPageContent());
    }, function() {
        this.exit();             //失败时调用的函数,输出一个消息,并退出
    }, 4000);
}).then(function(){
    this.exit();
});
casper.run();
import org.openqa.selenium.By

explorer.type (By.id("username"), "OAMobileTest@savills.com.cn");

explorer.type (By.name("password"), "0A@testcn");

//保留链接，采用子线程提交，否则会等待无法继续执行
Thread.start {
    explorer.click(By.id("Submit1"));
}

//等待提交延迟，之后需要调整优化
Thread.sleep(2000l);

//打开OA登陆页面
explorer.open("/http/China OA Offline/");

explorer.type(By.id("userName"), "mobiletest01");
explorer.type(By.id("password"), "123456");

//保持链接
Thread.start {
    explorer.findElement(By.id("loginform")).submit();
}

Thread.sleep(2000l);

import com.daosheng.field.rest.vo.BackLogPageVO
import com.daosheng.field.service.configuration.helper.PageHelper
import org.openqa.selenium.By

/**
 * 内置对象
 * logger 用于日志输出
 * explorer 浏览器对象
 * username 第三方用户名
 * password 第三方用户密码
 */

/**
 * init 中 用户初始化一些常量 explorer对象还没有创建 所以在 init 中不能使用
 * @return
 */
def init() {
    //必须用于初始化 explorer
    rootUrl = 'https://vpn.savills.com.cn/'
    //必须用于初始化explorer
    loginUri = '/wa/_welcome.html'
    //必须用于标记缓存 explorer 所使用得key 实际上使用的是 第三方用户名
    explorerCacheKey = 'username'

    //设置项目特有得常量
    vpn_user_name = 'OAMobileTest@savills.com.cn'
    vpn_password = '0A@testcn'

    //通过已办 和 未办 id 获取 响应列表得方法
    //    params.putAll(httpServletRequest.getParameterMap());
    //    params.put("token", token);
    //    params.put("moduleId", moduleId);
    //    params.put("nodeId", nodeId);
    //    params.put("pageNumber", pageNumber);
    //    params.put("pageSize", pageSize);
    //    params.put("search", search);
    idToListMethod=["1":"rootList", "2":"backlog","3":"backloged"]

    println "init success"
}

/**
 * 用于编写登陆逻辑，使explorer对象拥有有效得会话
 */
def login() {
    explorer.type(By.id("username"), vpn_user_name);

    explorer.type(By.name("password"), vpn_password);

    //保留链接，采用子线程提交，否则会等待无法继续执行
    Thread.start {
        explorer.click(By.id("Submit1"));
    }

    //等待提交延迟，之后需要调整优化
    Thread.sleep(2000l);

    //打开OA登陆页面
    explorer.open("/http/China OA Offline/");

    explorer.type(By.id("userName"), username);
    explorer.type(By.id("password"), password);

    //保持链接
    Thread.start {
        explorer.findElement(By.id("loginform")).submit();
    }

    Thread.sleep(2000l);

    println "login success"
}


def rootList(){
    def backlogList = [];

    BackLogPageVO backLogPageVO = new BackLogPageVO();
    backLogPageVO.setBacklogId("2");
    backLogPageVO.setNodeId("2");
    backLogPageVO.setBacklogTitle("待办")

    backlogList.add(backLogPageVO);

    backLogPageVO = new BackLogPageVO();
    backLogPageVO.setBacklogId("3");
    backLogPageVO.setNodeId("3");
    backLogPageVO.setBacklogTitle("已办")

    backlogList.add(backLogPageVO);

    PageHelper<BackLogPageVO> pageHelper = new PageHelper<BackLogPageVO>();
    return pageHelper.getPageView(pageNumber, pageSize, backlogList);
}
/**
 *
 * @return
 */
def backlog() {
    //列表数据
    explorer.open("/seeyon/main.do?method=morePending4App&app=Coll");

    logger.debug "${username} -> ${password}";

    def listResult = []

    explorer.findElement(By.id("bodyIDpending")).findElements(By.tagName("tr")).each {
        tr ->
            def rowData = [:];
            def tds = tr.findElements(By.tagName("td"))
            rowData."title" = tds[0].findElement(By.name("id")).getAttribute("colsubject");

            def link = tds[1].findElement(By.tagName("a")).getAttribute("href");

            rowData."link" = link[30..link.indexOf("',") - 1];

            rowData."fromUser" = tds[2].findElement(By.tagName("div")).getAttribute("title");
            rowData."fromDateTime" = tds[3].findElement(By.tagName("div")).getAttribute("title");
            rowData."toDateTime" = tds[4].findElement(By.tagName("div")).getAttribute("title");
            rowData."processLongTime" = tds[5].findElement(By.tagName("div")).getAttribute("title");
            rowData."processType" = tds[6].findElement(By.tagName("a")).getText();

            listResult.add(rowData)
    }

    logger.debug "list result is:{}", listResult

    println "list success"

    return listResult
}

/**
 *
 * @return
 */
def backloged() {
    //列表数据
    explorer.open("/seeyon/main.do?method=morePending4App&app=Coll");

    logger.debug "${username} -> ${password}";

    def listResult = []

    explorer.findElement(By.id("bodyIDpending")).findElements(By.tagName("tr")).each {
        tr ->
            def rowData = [:];
            def tds = tr.findElements(By.tagName("td"))
            rowData."title" = tds[0].findElement(By.name("id")).getAttribute("colsubject");

            def link = tds[1].findElement(By.tagName("a")).getAttribute("href");

            rowData."link" = link[30..link.indexOf("',") - 1];

            rowData."fromUser" = tds[2].findElement(By.tagName("div")).getAttribute("title");
            rowData."fromDateTime" = tds[3].findElement(By.tagName("div")).getAttribute("title");
            rowData."toDateTime" = tds[4].findElement(By.tagName("div")).getAttribute("title");
            rowData."processLongTime" = tds[5].findElement(By.tagName("div")).getAttribute("title");
            rowData."processType" = tds[6].findElement(By.tagName("a")).getText();

            listResult.add(rowData)
    }

    logger.debug "list result is:{}", listResult

    println "list success"

    return listResult
}


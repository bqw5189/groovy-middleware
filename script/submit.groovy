import com.gargoylesoftware.htmlunit.FormEncodingType
import org.openqa.selenium.By
import org.springside.modules.utils.Encodes

import java.nio.charset.Charset


def submitResult = [:]

remove_url.split(";").each {it
    //详细信息页面
    explorer.open(new String(Encodes.decodeHex(it)));

    def detailUrl =  explorer.findElement(By.name("detailMainFrame")).getAttribute("src")

    def parameters = explorer.driver.URLRequestParam(detailUrl);

    //comment result 没有传过来，让顾老师看下吧
    println comment + "=========>" + result

    def attitude = result;//2：同意 3：不同意

    def content = comment;//意见

    def postUrl = "https://vpn.savills.com.cn/seeyon/batch.do?method=doBatch&attitude=${attitude}&content=${content}&trace=false&affairId=${parameters["affairId"]}&summaryId=${parameters["summaryId"]}&category=1&code=0&parameter=2,0"

    if ("3".equals(result)){
        postUrl = "https://vpn.savills.com.cn/seeyon/savills.do?method=repealFlow&attitude=${attitude}&content=${content}&trace=false&affairId=${parameters["affairId"]}&summaryId=${parameters["summaryId"]}&category=1&code=0&parameter=2,0"
    }

    println postUrl

    //批量审批
    explorer.getDriver().post(postUrl,FormEncodingType.URL_ENCODED);

    submitResult."${it}" = true;
}
//println explorer.driver.pageSource;

//传递返回数据
render_data = submitResult

import org.openqa.selenium.By
import org.springside.modules.utils.Encodes

//
//列表数据
explorer.open(new String(Encodes.decodeHex(remove_url)));

//println explorer.getDriver().getPageSource();

def listResult = []

explorer.findElement(By.id("bodyIDpending")).findElements(By.tagName("tr")).each{
    tr->
    def rowData = [:];
    def tds = tr.findElements(By.tagName("td"))
    rowData."title" = tds[1].findElement(By.tagName("div")).getAttribute("title");

    def link = tds[1].findElement(By.tagName("div")).getAttribute("onclick");

    rowData."link" = "/seeyon/collaboration.do?method=detail&" + link[12..link.indexOf("');")-1];

    rowData."fromUser" = tds[2].findElement(By.tagName("div")).getText();
    rowData."fromDateTime" = tds[3].findElement(By.tagName("div")).getText();
    rowData."processLongTime" = tds[4].findElement(By.tagName("div")).getText();

    listResult.add(rowData)
}

render_data=listResult

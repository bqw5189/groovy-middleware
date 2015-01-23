import org.openqa.selenium.By
import org.springside.modules.utils.Encodes

//
//列表数据
explorer.open(new String(Encodes.decodeHex(remove_url)));

println oa_username

println oa_password

println explorer.getDriver().getPageSource();

def listResult = []
explorer
explorer.findElement(By.id("bodyIDpending")).findElements(By.tagName("tr")).each{
    tr->
    def rowData = [:];
    def tds = tr.findElements(By.tagName("td"))
    rowData."title" = tds[0].findElement(By.name("id")).getAttribute("colsubject");

    def link = tds[1].findElement(By.tagName("a")).getAttribute("href");

    rowData."link" = link[30..link.indexOf("',")-1];

    rowData."fromUser" = tds[2].findElement(By.tagName("div")).getAttribute("title");
    rowData."fromDateTime" = tds[3].findElement(By.tagName("div")).getAttribute("title");
    rowData."toDateTime" = tds[4].findElement(By.tagName("div")).getAttribute("title");
    rowData."processLongTime" = tds[5].findElement(By.tagName("div")).getAttribute("title");
    rowData."processType" = tds[6].findElement(By.tagName("a")).getText();

    listResult.add(rowData)
}

println listResult

render_data=listResult

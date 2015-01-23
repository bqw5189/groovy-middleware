import org.openqa.selenium.By
import org.springside.modules.utils.Encodes

//
//列表数据
explorer.open(new String(Encodes.decodeHex(remove_url)));

println oa_username

println oa_password

println explorer.getDriver().getPageSource();

def tableHead=[]
explorer.findElement(By.id("headIDpending")).findElements(By.tagName("th")).each{
    th->
    //println th.findElement(By.tagName("div")).getText()
    tableHead.add(th.findElement(By.tagName("div")).getText())
}
println "tableHead:"+tableHead
def listResult = []
explorer
explorer.findElement(By.id("bodyIDpending")).findElements(By.tagName("tr")).each{
    tr->
    def rowData = [:];
    def tds = tr.findElements(By.tagName("td"));
    rowData.put("affairId",tds[0].findElement(By.tagName("input")).getAttribute("affairId"));
    rowData.put("summaryId",tds[0].findElement(By.tagName("input")).getAttribute("value"));
    rowData.put(tableHead[1],tds[1].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[2],tds[2].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[3],tds[3].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[4],tds[4].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[5],tds[5].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[6],tds[6].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[7],tds[7].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[8],tds[8].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[9],tds[9].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[10],tds[10].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[11],tds[11].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[12],tds[12].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[13],tds[13].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[14],tds[14].findElement(By.tagName("div")).getText());
    rowData.put(tableHead[15],tds[15].findElement(By.tagName("div")).getText());
    //println rowData
    listResult.add(rowData)
}

println listResult

render_data=listResult

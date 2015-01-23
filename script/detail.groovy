import org.springside.modules.utils.Encodes

//详细信息页面

explorer.open(new String(Encodes.decodeHex(remove_url)));

println explorer.getDriver().getPageSource();

explorer.driver.switchTo().frame("detailMainFrame");

explorer.driver.switchTo().frame("contentIframe");

def pageSource = explorer.driver.pageSource + ""

def myfieldsXml = pageSource.tokenize("\n").find { it -> it.startsWith("document.getElementById(\"tarea\").value") }

myfieldsXml = myfieldsXml.split(/&&&&&&&&/)[3]

myfieldsXml = myfieldsXml.replaceAll(/\\"/, "\"");
myfieldsXml = myfieldsXml.replaceAll(/\\r\\n/, "\n");
myfieldsXml = myfieldsXml.replaceAll(/my:/, "");
myfieldsXml = myfieldsXml.trim();

def slurper = new XmlSlurper()

def myfields = slurper.parseText(myfieldsXml)

def myfieldResult = [:];

myfields."*".each { myfieldResult."${it.name()}" = it.text() }

//解析group1数据
def group1 = [];
myfields.group1."*".each {
    def group2 = [:]

    it."*".each {
        group2."${it.name()}" = it.text()
    }

    group1.add group2
}

myfieldResult."group1" = group1;

//传递返回数据
render_data = myfieldResult

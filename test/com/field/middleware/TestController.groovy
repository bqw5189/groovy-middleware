package com.field.middleware

import org.springside.modules.utils.Encodes

String removeUrl = Encodes.encodeHex("/seeyon/collaboration.do?method=listDone&condition=&textfield=&textfield1=&isQuote=&flag=&bizConfigId=&tempIds=&type=&menuId=".getBytes());

String uri = "optype=data&script=list-backloged&templet=list&vpn_user_name=OAMobileTest@savills.com.cn&vpn_password=0A@testcn&oa_username=mobiletest01&oa_password=123456&remove_url=" + removeUrl;
MiddleWare middleWare = new MiddleWare(uri);

def requestParams = uri;

//def middlerWare = new MiddleWare(requestParams);

def list(){
    println "call list"
}

"detail"();




APP_JSON = {
	send : function(fun , param,  callback){
		// var ip_url = $api.getStorage('exist_url');
		ip_url ="http://tunec.synology.me:83/webservices/boxJsonservices.php";
		var pl = new SOAPClientParameters();
		if(param){
		if(typeof param == 'object'){
		}else if(typeof param == 'string'){
			param = JSON.parse(param)
		}else{
			alert('Data Type invalid');
			return false;
		}
		for(o in param){
			pl.add(o,param[o]);
		}
	}
	SOAPClient.invoke(ip_url, fun, pl, true, callback);
	}

}
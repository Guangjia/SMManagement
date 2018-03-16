$('.aui-list-view').on('click','.btn',function(){
		if($(this).parent().next().css('display') === 'none'){
	        $(this).parent().next().slideDown()
	    }else{
	         $(this).parent().next().slideUp()
	    }
	})

$('.tab-nav li').click(function(){
    $(this).addClass('active').siblings('li').removeClass('active')
    var num = $(this).index();
    $('.tab-content:eq(' + num + ')').css('display','block').siblings('div.tab-content').css('display','none')
})



// 普通开关状态
function getstatus($v) {
    switch ($v + "") {
        case "0":
            return $.i18n.prop("string_关");
        case "1":
            return $.i18n.prop("string_开");
        default:
            return $v;
    }
}

function getalarmstatus($v) {
    switch ($v + "") {
        case "0":
            return $.i18n.prop("string_正常");
        case "1":
            return $.i18n.prop("string_告警");
        default:
            return $v;
    }
}

//多态状态量判断
function getalarmboxstatus($v, $portid) {
    var $protocolname = api.pageParam.proname;
    var $equiptypemodelid = api.pageParam.modelid;
    switch ($protocolname.toUpperCase()) {

       //新增一个协议对应多个设备模板的多态判断返回值2017-4-21
        case "UPS_BAYKEEQ":
            switch ($equiptypemodelid) {
                case "573":   //(山特MHC1-3K)
                    switch ($portid.toUpperCase()) {
                        case "PLMS":
                            switch ($v + "") {
                                case "1":  //开
                                return $.i18n.prop("string_开");
                                case "0":  // 关
                                return $.i18n.prop("string_关");
                            }
                            break;
                        case "UPSMS":
                            switch ($v + "") {
                                case "0": return '在线';
                                case "1": return '后备';
                            }
                            break;
                    }
                    break;
                case "其他modelid":
                    switch ($portid.toUpperCase()) {
                        case "UPSTYPE":
                            switch ($v + "") {
                                case "0": return '在线';
                                case "1": return '待机';
                            }
                            break;
                    }
                    break;
            }
            break;
            
         // 结束

        case "CPS_SMUA20":
            switch ($equiptypemodelid) {
                case "574":   
                    if ($portid.indexOf("_workstatus") >= 0) {
                        switch ($v + "") {
                            case "0":
                                return 'Float charging';  
                            case "1":
                                return 'Equalization charging';  
                            case "2":
                                return 'Test';  
                            case "3":
                                return 'AC power failure';  
                            default: return $v;
                        }
                    }

                    if ($portid.indexOf("_runstatus") >= 0) {
                        switch ($v + "") {
                            case "0":
                                return 'Power on';  
                            case "1":
                                return 'Power off';  
                            default: return $v;
                        }
                    }
                break;
            }
            break;
        // case "CPS_SMUA20":
        //     if ($portid.indexOf("_workstatus") >= 0) {
        //         switch ($v + "") {
        //             case "0":
        //                 return '浮充';
        //             case "1":
        //                 return '均充';
        //             case "2":
        //                 return '测试';
        //             case "3":
        //                 return '交流停电';
        //             default: return $v;
        //         }
        //     }

        //     if ($portid.indexOf("_runstatus") >= 0) {
        //         switch ($v + "") {
        //             case "0":
        //                 return '开机';
        //             case "1":
        //                 return '关机';
        //             default: return $v;
        //         }
        //     }
        //     break;
        case "UPS_APCSYPXMOD":
            switch ($equiptypemodelid) {
                case "576":  
                    switch ($portid.toUpperCase()) {
                        case "CHARGER":
                            switch ($v + "") {
                                case "0": return 'Float Charging';
                                case "1": return 'Boost Charging';
                                case "2": return 'Normal Charging';
                                case "3": return 'Resting';
                                case "4": return 'Discharging';
                                case "5": return 'Equalization Charging';
                                case "6":
                                case "7":
                                return 'Reserved';
                            }
                        break;
		                case "BATSUP":
		                    switch ($v + "") {
		                        case "1": 
		                        $("#upsimg").attr("src", "../../images/ups1.gif");
		                        return 'Open';
		                        case "0":
		                        return 'Close';
		                    }
		                break;	
		                case "BYPASSSUP":
		                    switch ($v + "") {
		                        case "1": 
		                        $("#upsimg").attr("src", "../../images/ups2.gif");
		                        return 'Open';
		                        case "0": 
		                        return 'Close';
		                    }
	                    break;	                
                    }
                    // 如果portid   （BATSUP==0&&BYPASSSUP==0  设置图片为ups3.gif）
                    if (BATSUP==0&&BYPASSSUP==0){
	                    $("#upsimg").attr("src", "../../images/ups3.gif");
                    }
                break;
              
            }
            break;

        case "ACO_DAIKINDIS2001":
            switch ($equiptypemodelid) {
                case "584":   //(设备名称：大金DIS2001)
                    switch ($portid.toUpperCase()) {
                        case "RUNST":
                            switch ($v + "") {
                                case "0": return '送风';
                                case "1": return '制热';
                                case "2": return '制冷';
                            }
                        break;                
                    }
                break;
            }
        break;
        case "UPS_HIPLUSeuv11":
            switch ($equiptypemodelid) {
                case "585":   //(设备名称：艾默生HipluseU_V11)
                    switch ($portid.toUpperCase()) {
                        case "GDFS":  // 供电方式
                            switch ($v + "") {
                                case "0": 
                                $("#upsimg").attr("src", "../../images/ups3.gif");
                                return '均不供电';
                                case "1": 
                                $("#upsimg").attr("src", "../../images/ups1.gif");
                                return 'UPS供电';
                                case "2":
                                $("#upsimg").attr("src", "../../images/ups2.gif");
                                return '旁路供电';
                            }
                        break;   
                        case "DCZJ":  // 电池自检
                            switch ($v + "") {
                                case "0": return '自检中';
                                case "1": return '未自检';
                            }
                        break;   
                        case "CDZT":  // 充电状态
                            switch ($v + "") {
                                case "0": return '浮充';
                                case "1": return '均充';
                                case "2": return '非充电状态';
                            }
                        break;  
                        case "RUNST":  // 运行状态
                            switch ($v + "") {
                                case "0": return '关机';
                                case "1": return '开机';
                            }
                        break;  
                        case "UPSGD":  // UPS供电
                            switch ($v + "") {
                                case "0": return '主路逆变供电';
                                case "1": return '电池逆变供电';
                                case "2": return '联合逆变供电';
                                case "3": return '整流电池均不供电';
                            }
                        break;
                        case "FDJZT":  // 发电机状态
                            switch ($v + "") {
                                case "0": return '发电机接入';
                                case "1": return '发电机未接入';
                            }
                        break;  
                        case "SRKGZT":   // 输入开关状态
                        case "WXPLZT":  // 维修旁路状态
                        case "PLZT":  // 旁路状态
                        case "SCZT":  // 输出状态
                            switch ($v + "") {
                                case "0": return '断开';
                                case "1": return '闭合';
                            }
                        break; 
                        case "BJXTGDZT":  // 并机系统供电状态
                            switch ($v + "") {
                                case "0": return '主路逆变供电';
                                case "1": return '电池逆变供电';
                                case "2": return '旁路供电';
                                case "3": return '均不供电';
                            }
                        break;  
                        case "XZKGZT":  // 旋转开关状态
                            switch ($v + "") {
                                case "0": return '关闭状态';
                                case "1": return '测试状态';
                                case "2": return '正常状态';
                                case "3": return '旁路状态';
                                case "4": return '维修状态';
                                case "5": return '逆变步进调试模式';
                            }
                        break; 
                        case "LBQZT": // 滤波器状态
                            switch ($v + "") {
                                case "0": return '未接入';
                                case "1": return '接入';
                            }
                        break; 
                        case "XMZT": // 休眠状态
                            switch ($v + "") {
                                case "0": return '未休眠';
                                case "1": return '本机休眠';
                            }
                        break; 
                        case "ZLQGZZT": // 整流器工作状态
                            switch ($v + "") {
                                case "0": return '正常工作状态';
                                case "1": return 'PFC工作状态';
                            }
                        break;    
                        case "ECOMSZT":  // ECO模式状态
                            switch ($v + "") {
                                case "0": return '正常模式';
                                case "1": return 'ECO模式';
                            }
                        break;              
                    }
                break;
            }
        break;

        case "ACO_ENVICOOLDC40":
            switch ($equiptypemodelid) {
                case "588":   //(设备名称：英维克DC40)
                    switch ($portid.toUpperCase()) {
                        case "RUNST":
                        case "FJ":
                        case "OFJ":
                        case "YSJ":
                        case "SBZT":
                            switch ($v + "") {
                                case "1": return 'Standby'; //待机
                                case "2": return 'Running'; //运行
                                case "3": return 'Breakdown';  // 故障
                            }
	                    break;              
                    }
                break;
            }
            break;

            
        case "UPS_EMERSONV33":
            switch ($portid.toUpperCase()) {
                case "GDFS":
                    switch ($v + "") {
                        case "1": 
                        $("#upsimg").attr("src", "../../images/ups1.gif");
                        return 'UPS供电';
                        case "2": 
                        $("#upsimg").attr("src", "../../images/ups2.gif");
                        return '旁路供电';
                        case "9":
                    	$("#upsimg").attr("src", "../../images/ups3.gif"); 
                        return '均不供电';
                    }
                    break;	      //1--UPS供电	2--旁路供电	9--均不供电                    
                    
                case "DCZJ":
                    switch ($v + "") {
                        case "0": return '自检中';
                        case "1": return '非自检';
                    }
                    break;	//      0--自检中	1--非自检
                case "CDZT":
                    switch ($v + "") {
                        case "0": return '浮充';
                        case "1": return '均充';
                        case "2": return '非充电状态';
                    }
                    break;	  //       0--浮充		1--均充		2--非充电状态
                case "KGZT":
                    switch ($v + "") {
                        case "0": return '关';
                        case "1": return '开';
                    }
                    break;	  //     0--关		1--开
                case "UPSGDZT":
                    switch ($v + "") {
                        case "0": return '主路逆变供电';
                        case "1": return '电池逆变供电';
                        case "2": return '联合逆变供电';
                        case "3": return '整流电池均不供电';
                    }
                    break;	  //     	0--主路逆变供电	1--电池逆变供电	2--联合逆变供电	3--整流电池均不供电
                case "ZJFDJJRSJ":
                    switch ($v + "") {
                        case "0": return '发电机接入';
                        case "1": return '发电机没接入';
                    }
                    break;	 //	      0--发电机接入	1--发电机没接入
                case "SRKKZT":
                case "WXPLKKZT":
                case "PLKKZT":
                case "SCKKZT":
                    switch ($v + "") {
                        case "0": return '断开';
                        case "1": return '闭合';
                    }
                    break;
                case "XZKKZT":
                    switch ($v + "") {
                        case "0": return '关闭状态';
                        case "1": return '测试状态';
                        case "2": return '正常状态';
                        case "3": return '旁路状态';
                        case "4": return '维修状态';
                    }
                    break;//	     	0--关闭状态	1--测试状态	2--正常状态	3--旁路状态	4--维修状态
                case "BJXTGDZT":
                    switch ($v + "") {
                        case "0": return '主路逆变供电';
                        case "1": return '电池逆变供电';
                        case "2": return '旁路供电';
                        case "3": return '均不供电';
                    }
                    break;//	    0--主路逆变供电	1--电池逆变供电	2--旁路供电	3--均不供电
            }

            break;
        case "UPS_MEIHEXL21":
            switch ($portid.toUpperCase()) {
                case "PLMS":
                    switch ($v + "") {
                        case "0": 
                        $("#upsimg").attr("src", "../../images/ups3.gif");
                        return '关';
                        case "1": 
                        $("#upsimg").attr("src", "../../images/ups2.gif");
                        return '开';
                    }
                    break;	      //旁路模式     0--关 [动态图ups3.gif] 	1--关 [动态图ups2.gif]                    
                    
                case "UPSMS":
                    switch ($v + "") {
                        case "0": return '在线式';
                        case "1": return '后备式';
                    }
                    break;	      //UPS模式      0--在线式 	1--后备式  
            }

            break;
        case "CPS_SMU01C":
            if ($portid.indexOf("_runstatus") > -1) {
                switch ($v + "") {
                    case "0": return '开';
                    case "1": return '关';
                }
            }
            if ($portid.indexOf("_workstatus") > -1) {
                switch ($v + "") {
                    case "0": return '浮充';
                    case "1": return '均充';
                    case "2": return '测试';
                }
            }
            break;
        case "ACO_CANATALKN10":
            switch ($portid.toUpperCase()) {
                case "CGQCONNECT": return ($v + "") == "0" ? "未连接" : "连接";
                case "MOD1CONNECT": return ($v + "") == "0" ? "未连接" : "连接";
                case "MOD2CONNECT": return ($v + "") == "0" ? "未连接" : "连接";
                case "MOD3CONNECT": return ($v + "") == "0" ? "未连接" : "连接";
                case "MOD4CONNECT": return ($v + "") == "0" ? "未连接" : "连接";
                case "MOD5CONNECT": return ($v + "") == "0" ? "未连接" : "连接";
                case "TYBJ": return ($v + "") == "0" ? "无" : "有";
                case "MK1FBST": return ($v + "") == "0" ? "无" : "有";
            }
            break;
        case "UPS_EASTEA66MOD":
            switch ($portid.toUpperCase()) {
                case "WORKMODE":
                    switch ($v + "") {
                        case "1": return '待机模式';
                        case "2": return '旁路模式';
                        case "3": return '市电模式';
                        case "4": return '电池模式';
                        case "5": return '电池自检';
                        case "6": return '故障模式';
                        case "7": return '变频模式';
                        case "8": return '紧急关机模式';
                        case "9": return '关机模式';
                    }
                    break;
            }
            break;
        case "UPS_KSTARYDC":
            switch ($portid.toUpperCase()) {
                case "POWERSUPPLY":
                    switch ($v + "") {
                        case "1": return 'UPS供电';
                        case "2": return '旁路供电';
                    }
                    break;
                case "ENEVT":
                    switch ($v + "") {
                        case "0": return '';
                        case "1": return '初始化';
                        case "2": return '待机状态';
                        case "3": return '无输出状态';
                        case "4": return '旁路输出状态';
                        case "5": return '在线输出状态';
                        case "6": return '电池输出状态';
                        case "7": return '经济模式';
                        case "8": return '自检状态';
                        case "9": return '逆变启动中';
                        case "10": return '故障状态';
                        case "11": return '维修旁路状态';
                        case "12": return '紧急关机状态';
                        case "13": return '监控系统复位';
                        case "14": return '返回正常(报警取消) ';
                        case "15": return '';
                        case "16": return '';
                        case "17": return '';
                        case "18": return '';
                        case "19": return '';
                        case "20": return '输入空开断开';
                        case "21": return '输入空开闭合';
                        case "22": return '整流器停止工作';
                        case "23": return '整流器开始工作';
                        case "24": return '整流器限流';
                        case "25": return '电池停止充电';
                        case "26": return '正电池组均充';
                        case "27": return '正电池组浮充';
                        case "28": return '负电池组均充';
                        case "29": return '负电池组浮充';
                        case "30": return '旁路空开闭合';
                        case "31": return '旁路空开断开';
                        case "32": return '输出空开闭合';
                        case "33": return '输出空开断开';
                        case "34": return '旁路柜旁路空开闭合';
                        case "35": return '旁路柜旁路空开断开';
                        case "36": return '外部输出空开闭合';
                        case "37": return '外部输出空开断开';
                        case "38": return '间断切换提示';
                        case "39": return '关机将导致过载告警';
                        case "40": return '关机将导致断电告警';
                        case "41": return '启动容量不足';
                        case "42": return '逆变主机';
                        case "43": return '切换次数到';
                        case "44": return '过载延时到关机';
                        case "45": return '负载冲击切旁路';
                        case "46": return '并机系统转旁路';
                        case "47": return 'LBS(负载总线同步)系统激活';
                        case "48": return '防雷动作';
                        case "49": return '电池电压低';
                        case "50": return '定时开机';
                        case "51": return '定时关机';
                        case "52": return '自检开始';
                        case "53": return '自检停止';
                        case "54": return '手动关机';
                        case "55": return '遥控关机';
                        case "56": return '过载延时到断开输出';
                    }
                    break;
                case "UPSRUNST":
                    switch ($v + "") {
                        case "0": return '初始化';
                        case "1": return '初始化(数据)';
                        case "2": return '待机状态';
                        case "3": return '无输出状态';
                        case "4": return '旁路状态';
                        case "5": return '市电状态';
                        case "6": return '电池状态';
                        case "7": return '经济模式输出';
                        case "8": return '电池自检状态';
                        case "9": return '逆变启动中';
                        case "10": return '故障状态';
                        case "11": return '维护旁路模式';
                        case "12": return '紧急关机状态';
                    }

                    break;
                case "OUTPUTST":
                    switch ($v + "") {
                        case "0": return '无输出';
                        case "1": return '旁路输出';
                        case "2": return '逆变输出';
                    }
                    break;
                case "NBKGZT":
                    switch ($v + "") {
                        case "0": return '关机';
                        case "1": return '软启动';
                        case "10": return '启动完毕未供电';
                        case "11": return '正常供电中';
                    }
                    break;
                case "DCZJ":
                    switch ($v + "") {
                        case "0": return '未自检';
                        case "1": return '自检中';
                    }
                    break;
                case "NDCST":
                case "PDCST":
                    switch ($v + "") {
                        case "0": return '浮充';
                        case "1": return '均充';
                    }
                    break;
                case "CHARGEST":
                    switch ($v + "") {
                        case "0": return '未充电';
                        case "1": return '充电中';
                    }
                    break;
                case "INSUPMODE":
                    switch ($v + "") {
                        case "0": return '电池供电';
                        case "1": return '输入供电';
                    }
                    break;
                case "INSUPST":
                    switch ($v + "") {
                        case "0": return '不工作';
                        case "1": return '工作';
                    }
                    break;
                case "ZLQXL":
                    switch ($v + "") {
                        case "0": return '未限流';
                        case "1": return '限流';
                    }
                    break;
                case "ZLQZT":
                    switch ($v + "") {
                        case "0": return '停止';
                        case "1": return '工作';
                    }
                    break;
            }
            break;
        case "UPS_LADISPW30":
            switch ($portid.toUpperCase()) {
                case "UPSST":
                    switch ($v + "") {
                        case "0": return '待机';
                        case "1": return '交互';
                        case "2": return '在线';
                    }
                    break;
            }
            break;
        case "UPS_INVTRM400":
            switch ($portid.toUpperCase()) {
                case "GDFS":
                    switch ($v + "") {
                        case "0": return '均不供电';
                        case "1": return 'UPS供电';
                        case "2": return '旁路供电';
                    }
                    break;
                case "DCZT":
                    switch ($v + "") {
                        case "0": return '电池未工作';
                        case "1": return '电池浮充';
                        case "2": return '电池均充';
                        case "3": return '电池放电';
                    }
                    break;
                case "DCWHZT":
                    switch ($v + "") {
                        case "0": return '未维护测试';
                        case "1": return '成功';
                        case "2": return '失败';
                        case "3": return '维护测试中';
                    }
                    break;
                case "ZLQ":
                    switch ($v + "") {
                        case "0": return 'OFF';
                        case "1": return 'SoftStart';
                        case "2": return 'NormalWork';
                    }
                    break;
            }
            break;
        case "EG_ILNT":
            switch ($portid.toUpperCase()) {
                case "EPSTAT":
                case "CBSTAT":
                    switch ($v + "") {
                        case "19": return '起始';
                        case "20": return '未预备';
                        case "21": return '预起动';
                        case "22": return '起动中';
                        case "23": return '间歇';
                        case "24": return '起动中';
                        case "25": return '运行中';
                        case "26": return '已合闸';
                        case "27": return '停机';
                        case "28": return '停机';
                        case "29": return '候命中';
                        case "30": return '冷却中';
                        case "31": return '应急手动';
                        case "32": return 'ManIdle';
                        case "33": return '市电合闸';
                        case "34": return '市电故障';
                        case "35": return '市电故障';
                        case "36": return '岛运行';
                        case "37": return '市电回复';
                        case "38": return '断路全分';
                        case "39": return '不计时';
                        case "40": return 'MCB 合闸';
                        case "41": return '恢复延时';
                        case "42": return '市并时间';
                        case "43": return '怠速运行';
                        case "44": return '最低稳时';
                        case "45": return '最高稳时';
                        case "46": return '后冷却泵';
                        case "47": return 'ＧＣＢ开';
                        case "48": return '停机阀';
                        case "49": return '起动延时';
                        case "50": return '(1Ph)';
                        case "51": return '(3PD)';
                        case "52": return '(3PY)';
                        case "53": return 'MRS 模式';
                        case "54": return '无';
                        default: return $v;
                    }
                    break;
                case "FUELSW":
                    return $v + "" == "1" ? "燃气" : "柴油";
                    break;
                case "COOLSPEED":
                    return $v + "" == "1" ? "额定转速" : "怠速";
                    break;
            }
            break;
        case "ACO_CLIMVENETA":
            switch ($portid.toUpperCase()) {
                case "FJ":
                    return $v + "" == "1" ? "出风" : "进风";
                case "FA":
                    return $v + "" == "1" ? "出水" : "进水";
                    break;
            }

            break;
        case "UPS_EMERSONNXR":
            switch ($portid.toUpperCase()) {
                case "SUPSTYLE":
                    switch ($v + "") {
                        case "1":
                            return 'UPS供电';
                        case "2":
                            return '旁路供电';
                        case "3":
                            return '均不供电';
                        default: return $v;
                    }
                    break;
                case "WORKST":
                    switch ($v + "") {
                        case "0": return "浮充";
                        case "1":
                            return '均充';
                        case "2":
                            return '非充电状态';
                        default: return $v;
                    }
                    break;
                case "UPSSUP":
                    switch ($v + "") {
                        case "0":
                            return '主路逆变供电';
                        case "1":
                            return '电池逆变供电';
                        case "2":
                            return '联合逆变供电';
                        case "3":
                            return '整流电池均不供电';
                        default: return $v;
                    }
                    break;
            }
            break;
        case "ACO_EMERSONPEXSNMP":
            switch ($portid.toUpperCase()) {
                case "SYSSTATE":
                    switch ($v + "") {
                        case "0":
                            return '关';
                        case "1":
                            return '开';
                        case "3":
                            return '待机';
                    }
                    break;
                case "HOWSYSOFF":
                    switch ($v + "") {
                        case "0":
                        case "1":
                            return '无';
                        case "2":
                            return '本地';
                        case "3":
                            return '告警';
                        case "4":
                            return '调度';
                        case "5":
                            return '监控';
                        case "6":
                            return '远程';
                        case "7":
                            return '显示';
                    }
                    break;
                case "RUNMODE":
                    switch ($v + "") {
                        case "0":
                            return '手动';
                        case "1":
                            return '自动';
                    }
                    break;
            }
            break;
        case "IPM_M822E":
            switch ($portid.toUpperCase()) {
                case "CHARGEST":
                    switch ($v + "") {
                        case "0":
                            return '浮充';
                        case "1":
                            return '均充';
                        case "2":
                            return '测试';
                        case "3":
                            return '交流停电';
                    }
                    break;
                case "BATADMINST":
                    switch ($v + "") {
                        case "0":
                            return '自动';
                        case "1":
                            return '手动';
                    }
                    break;
            }

            $newport = $portid.substr(0, $portid.length - 1)
            switch ($newport.toUpperCase()) {
                case "MODLIMITST":
                    switch ($v + "") {
                        case "0":
                            return '限流';
                        case "1":
                            return '不限流';
                    }
                    break;
                case "MODACTION":
                    switch ($v + "") {
                        case "0":
                            return '浮充';
                        case "1":
                            return '均充';
                        case "2":
                            return '测试';
                        case "3":
                            return '交流停电';
                    }
                    break;
                case "MODACLP":
                case "MODTEMPLP":
                    switch ($v + "") {
                        case "0":
                            return '正常';
                        case "1":
                            return '限功率';
                    }
                    break;
                case "MODFAN":
                    switch ($v + "") {
                        case "0":
                            return '正常';
                        case "1":
                            return '全速';
                    }
                    break;
                case "MODWALK":
                    switch ($v + "") {
                        case "0":
                            return '正常';
                        case "1":
                            return '使能';
                    }
                    break;
                case "MODOP":
                    switch ($v + "") {
                        case "0":
                            return '正常';
                        case "1":
                            return '动作';
                    }
                    break;
            }
            break;
        case "MOD_GST200":
            switch ($v + "") {
                case "0":
                    return '正常';
                case "1":
                    return '火警';
                case "2":
                    return '故障';
                case "3":
                    return '动作';
                case "4":
                    return '恢复';
                case "5":
                    return '启动';
                case "6":
                    return '停动';
                case "7":
                    return '隔离';
                case "8":
                    return '释放';
                case "9":
                    return '主电备电恢复';
                default:
                    return $v;
            }
            break;
        case "ATS_ABBS022":
            switch ($portid.toUpperCase()) {
                case "DCLN1":
                case "DCLN2":
                    switch ($v + "") {
                        case "0":
                            return '正常';
                        case "1":
                            return '失压';
                        case "2":
                            return '欠压';
                        case "3":
                            return '过压';
                        case "4":
                            return '缺相';
                        case "5":
                            return '相间电压不平衡';
                        case "6":
                            return '相序错误';
                        case "7":
                            return '频率越限';
                        default:
                            return $v;
                    }
                    break;
                case "DCSWST":
                    switch ($v + "") {
                        case "0":
                            return '无切换';
                        case "1":
                            return '主路切换备路';
                        case "2":
                            return '切换完成';
                        case "3":
                            return '备路切换主路';
                        case "4":
                            return '切换失败';
                        default:
                            return $v;
                    }
                    break;
                case "DYNAMO":
                    switch ($v + "") {
                        case "0":
                            return '停止';
                        case "1":
                            return '启动';
                        default:
                            return $v;
                    }
                    break;
            }
            break;
        case "UPS_KELONG":
            switch ($portid.toUpperCase()) {
                case "SRSCMS": //输入输出模式
                    switch ($v + "") {
                        case "0":
                            return '三相输入三相输出';
                        case "1":
                            return '三相输入单相输出';
                        default:
                            return $v;
                    }
                    break;
                case "ZLSRZT":  //直流输入状态
                    switch ($v + "") {
                        case "0":
                            return '交流输入正常';
                        case "1":
                            return '后备供电中';
                        default:
                            return $v;
                    }
                    break;
                case "DCCDMS":  //电池充电模式
                    switch ($v + "") {
                        case "0":
                            return '浮充';
                        case "1":
                            return '均充';
                        default:
                            return $v;
                    }
                    break;
                case "SDPL": // 手动旁路
                    switch ($v + "") {
                        case "0":
                            return '断开';
                        case "1":
                            return '闭合';
                        default:
                            return $v;
                    }
                    break;
                case "JTPLKGCY":   //静态旁路开关处于
                    switch ($v + "") {
                        case "0":
                            return '旁路端';
                        case "1":
                            return '逆变端';
                        default:
                            return $v;
                    }
                    break;
            }
            break;
        case "UPS_KSTARMODV12":
            switch ($portid.toUpperCase()) {
                case "DCZJ":
                    switch ($v + "") {
                        case "0":
                            return '未自检';
                        case "1":
                            return '自检中';
                        default:
                            return $v;
                    }
                case "DCCDZT":
                    switch ($v + "") {
                        case "0":
                            return '未充电';
                        case "1":
                            return '充电中';
                        default:
                            return $v;
                    }
                    break;
                case "ZZDCCDMS":
                case "JZDCCDMS":
                    switch ($v + "") {
                        case "0":
                            return '非均充';
                        case "1":
                            return '均充';
                        default:
                            return $v;
                    }
                    break;
                case "ZLQZT":
                    switch ($v + "") {
                        case "0":
                            return '停止';
                        case "1":
                            return '工作';
                        default:
                            return $v;
                    }
                    break;
                case "ZLQXLZT":
                    switch ($v + "") {
                        case "0":
                            return '未限流';
                        case "1":
                            return '限流';
                        default:
                            return $v;
                    }
                    break;
                case "SRGDZT":
                    switch ($v + "") {
                        case "0":
                            return '不工作';
                        case "1":
                            return '工作';
                        default:
                            return $v;
                    }
                    break;
                case "SRGDMS":
                    switch ($v + "") {
                        case "0":
                            return '电池供电';
                        case "1":
                            return '输入供电';
                        default:
                            return $v;
                    }
                    break;
                case "NBKGJZT1":
                case "NBKGJZT2":
                    switch ($v + "") {
                        case "0":
                            return '关机';
                        case "1":
                            return '软启动';
                        case "2":
                            return '启动完毕未供电';
                        case "3":
                            return '正常供电中';
                        default:
                            return $v;
                    }
                    break;
                case "UPSGDZT1":
                    switch ($v + "") {
                        case "0":
                            return '非逆变供电';
                        case "1":
                            return '逆变供电';
                        default:
                            return $v;
                    }
                    break;
                case "UPSGDZT2":
                    switch ($v + "") {
                        case "0":
                            return '非旁路供电';
                        case "1":
                            return '旁路供电';
                        default:
                            return $v;
                    }
                    break;
            }
            break;
        case "WD_BOX":
            switch ($portid.toUpperCase()) {
                case "DEV_MODE":
                    switch ($v + "") {
                        case "1":
                            return '远程';
                        case "0":
                            return '本地';
                        default:
                            return $v;
                    }
                    break;
                case "DEV_ST":
                    switch ($v + "") {
                        case "1":
                            return '正常';
                        case "0":
                            return '异常';
                        default:
                            return $v;
                    }
                    break;
                case "CONTROL_MODEL":
                    switch ($v + "") {
                        case "0":
                            return '隔绝通风';
                        case "1":
                            return '通风模式';
                        case "2":
                            return '滤毒模式';
                        default:
                            return $v;
                    }
                    break;
                default :
                    switch ($v + "") {
                        case "0":
                            return '关闭';
                        case "1":
                            return '打开';
                        case "2":
                            return '异常';
                        case "3":
                            return '未知';
                        default:
                            return $v;
                    }
                    break;
            }
            break;
        case "UPS_HUAWEI2000":
            switch ($portid.toUpperCase()) {
                case "SUPPLY":  //供电模式
                    switch ($v + "") {
                        case "0":
                            return '均不供电';
                        case "1":
                            return '旁路供电';
                        case "2":
                            return '主路供电';
                        case "3":
                            return '电池供电';
                        case "5":
                            return '主路ECO';
                        case "6":
                            return '电池ECO';
                        default:
                            return $v;
                    }
                    break;
                case "INMODE": //输入制式
                    switch ($v + "") {
                        case "1":
                            return '三相';
                        case "0":
                            return '单相';
                        default:
                            return $v;
                    }
                    break;
                case "OUTMODE": //输出制式
                    switch ($v + "") {
                        case "1":
                            return '三相';
                        case "0":
                            return '单相';
                        default:
                            return $v;
                    }
                    break;
                case "DCST": //电池状态
                    switch ($v + "") {
                        case "2":
                            return '休眠';
                        case "3":
                            return '浮充';
                        case "4":
                            return '均充';
                        case "5":
                            return '放电';
                        default:
                            return $v;
                    }
                    break;
                case "RUNST": //运行状态
                    switch ($v + "") {
                        case "0":
                            return '关机';
                        case "1":
                            return '开机中';
                        case "2":
                            return '开机失败(可开机)';
                        case "3":
                            return '开机完成(可关机)';
                        default:
                            return $v;
                    }
                break;
            }
            break;

    }
}

package com.hxmy.sm

import com.hxmy.sm.model.request.SmModel
import java.util.*
import java.util.regex.Pattern

/**
 * @author a488606
 * @since 3/16/18
 */

class Util {

    val notFound = "退货|撤销|预计"
    val success = "消费|退回|退款|退货|境外预授权/消费"
    val time = "(\\d{2,4}年)*\\d{2}月\\d{2}日(\\d{2}[:]\\d{2})*"
    val xiaofei = "消费\\w+\\d+[.]\\d{2}"
    val xiaofei_1 = "\\d+[.]\\d{2}"
    val card = "尾号\\w*\\d{4}|信用卡\\**\\d{4}"
    val card_1 = "\\d{4}"
    var tuihui = "退回\\w+\\d+[.]\\d{2}"
    var tuikuan = "退款\\w+\\d+[.]\\d{2}"
    var tuihuo = "退货\\w+\\d+[.]\\d{2}"
    var jingwai = "发生\\D+境外预授权/消费\\w+\\d+[.]\\d{2}"
    var jingwai_1 = "境外预授权/消费"
    var rmbDesc = "人民币|CNY|RMB"
    var yinhang = "【\\S+银行】"

    val keyword_xiaofei = "消费"
    val keyword_tuihui = "退回"
    val keyword_tuikuan = "退款"
    val keyword_tuihuo = "退货"
    val keyword_jingwai = "境外预授权/消费"


    fun filter(message: String, address: String, date: String): SmModel {
        var failed = Pattern.compile(notFound).matcher(message)
        var successed = Pattern.compile(success).matcher(message)

        //集中判断类型，分别判断
        var xfm = Pattern.compile(xiaofei).matcher(message)
        var tuihuiM = Pattern.compile(tuihui).matcher(message)
        var tuikuanM = Pattern.compile(tuikuan).matcher(message)
        var tuihuoM = Pattern.compile(tuihuo).matcher(message)

        var jingwaiM = Pattern.compile(jingwai).matcher(message)
        var jingwaiM_1 = Pattern.compile(jingwai_1).matcher(message)

        var yinhangM = Pattern.compile(yinhang).matcher(message)

        //共同
        var timeM = Pattern.compile(time).matcher(message)
        var cardM = Pattern.compile(card).matcher(message)


        var word: String = ""
        var wordLenght: Int = 0

        var sm = SmModel()
        if (!failed.find()) {
            if (successed.find()) {
                //消费
                if (xfm.find() && !jingwaiM_1.find() || tuihuiM.find() || tuihuiM.find() || tuikuanM.find() || tuihuoM.find()) {



                    sm.dTime = if (timeM.find())
                        if (timeM.group().contains("年")) {
                            timeM.group()
                        } else {
                            var year = Calendar.getInstance().get(Calendar.YEAR)
                            "${year}年${timeM.group()}"
                        }
                    else
                        date

                    //截取银行
                    var yh: String? = ""
                    if (yinhangM.find()) {
                        yh = yinhangM.group()
                    }
                    //截取银行卡号
                    sm.card = if (cardM.find()) {
                        var find = Pattern.compile(card_1).matcher(cardM.group())
                        if (find.find()) {
                            yh + find.group()
                        } else ""
                    } else ""
                    sm.telephone = address
                    sm.aType = "1"
                    sm.message = message

                    var value = xfm.group()

                    var index: Int = value.indexOf("消费") + 2
                    //找到多少钱
                    var moneyMatch = Pattern.compile(xiaofei_1).matcher(value)
                    var money = if (moneyMatch.find()) moneyMatch.group() else ""
                    var moneyIndex: Int = value.indexOf(money)
                    var type = value.substring(index, moneyIndex)

                    //判断是否是人民币
                    var rmbMatch = Pattern.compile(rmbDesc).matcher(type)
                    if (rmbMatch.find()) {
                        sm.rmb = money
                        sm.foreign = ""
                        sm.money = ""

                    } else {
                        sm.foreign = type
                        sm.money = money
                        sm.rmb = ""
                    }

                } else {

                }
            }
        }
        return sm;
    }

}
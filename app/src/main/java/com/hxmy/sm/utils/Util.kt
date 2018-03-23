package com.hxmy.sm.utils

import android.text.TextUtils
import com.hxmy.sm.model.request.SmModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class Util {

    val notFound = "失败|撤销|预计"
    val success = "消费|退回|退款|退货|境外预授权/消费|网上交易"
    val time = "(\\d{2,4}年)*\\d{1,2}月\\d{2}日(\\d{2}[:]\\d{2})*"
    val xiaofei = "消费\\D+\\d+[.]\\d{2}"
    val xiaofei_1 = "\\d+[.]\\d{2}"
    val card = "尾号\\D*\\d{4}|信用卡\\**\\d{4}"
    val card_1 = "\\d{4}"
    var tuihui = "退回\\D+\\d+[.]\\d{2}"
    var tuikuan = "退款\\D+\\d+[.]\\d{2}"
    var tuihuo = "退货\\D+\\d+[.]\\d{2}"
    var jingwai = "发生\\D+境外预授权/消费\\d+[.]\\d{2}"
    var jingwai_2 = "发生境外\\D消费\\d+[.]\\d{2}"
    var jingwai_1 = "境外预授权/消费"
    var rmbDesc = "人民币|CNY|RMB"
    var yinhang = "【\\D+银行】"
    var yinhang_1 = "\\[\\D+银行\\]"
    val zheherenminbi = "折合人民币\\D?+\\d+[.]\\d{2}"
    val ruzhangjine = "入账金额\\D?+\\d+[.]\\d{2}"

    val net = "网上交易\\D+\\d+[.]\\d{2}"

    val keyword_xiaofei = "消费"
    val keyword_tuihui = "退回"
    val keyword_tuikuan = "退款"
    val keyword_tuihuo = "退货"
    val keyword_jingwai = "境外预授权/消费"

    private val key_words = arrayOf("消费", "退回", "退款", "退货", "境外预授权/消费", "网上交易")


    fun filter(message: String, address: String, date: String): SmModel {
        var failed = Pattern.compile(notFound).matcher(message)
        var succeeded = Pattern.compile(success).matcher(message)

        //集中判断类型，分别判断
        //消费
        var xfm = Pattern.compile(xiaofei).matcher(message)
        //网上交易
        var netM = Pattern.compile(net).matcher(message)
        //退回
        var tuihuiM = Pattern.compile(tuihui).matcher(message)
        //退款
        var tuikuanM = Pattern.compile(tuikuan).matcher(message)
        //退货
        var tuihuoM = Pattern.compile(tuihuo).matcher(message)
        //境外
        var jingwaiM = Pattern.compile(jingwai).matcher(message)
        var jingwaiM_1 = Pattern.compile(jingwai_1).matcher(message)
        //境外。。。消费。。， 入账金额
        var jingwaiM_2 = Pattern.compile(jingwai_2).matcher(message)


        //折合人民币
        var zheherenminbi = Pattern.compile(zheherenminbi).matcher(message)

        var yinhangM = Pattern.compile(yinhang).matcher(message)
        var yinhangM_1 = Pattern.compile(yinhang_1).matcher(message)

        //共同
        var timeM = Pattern.compile(time).matcher(message)
        var cardM = Pattern.compile(card).matcher(message)


        var sm = SmModel()
        if (!failed.find()) {
            if (succeeded.find()) {

                var word = when {
                    jingwaiM.find() -> {
                        jingwaiM.group()
                    }
//                    jingwaiM_2.find() -> {
//                        jingwaiM_2.group()
//                    }
                    xfm.find() -> {
                        xfm.group()
                    }
                    tuihuiM.find() -> {
                        tuihuiM.group()
                    }
                    tuikuanM.find() -> {
                        tuikuanM.group()
                    }
                    tuihuoM.find() -> {
                        tuihuoM.group()
                    }
                    netM.find() -> {
                        netM.group()
                    }
                    else -> {
                        ""
                    }
                }
                if (!TextUtils.isEmpty(word)) {


                    //获取日期
                    sm.dTime = if (timeM.find()) {
                        var a = SimpleDateFormat("yyyy年MM月dd日")
                        var b = SimpleDateFormat("yyyy-MM-dd")
                        if (timeM.group().contains("年")) {
                            var date = a.parse(timeM.group())
                            b.format(date)
                        } else {
                            var year = Calendar.getInstance().get(Calendar.YEAR)
                            var date = a.parse("${year}年${timeM.group()}")
                            b.format(date)
                        }
                    } else
                        date

                    //截取银行
                    var yh: String? = ""
                    if (yinhangM.find() ) {
                        yh = yinhangM.group()
                    }else if(yinhangM_1.find())
                    {
                        yh = yinhangM_1.group()
                    }
                    //截取银行卡号
                    sm.card = if (cardM.find()) {
                        var find = Pattern.compile(card_1).matcher(cardM.group())
                        if (find.find()) {
                            yh + find.group()
                        } else yh
                    } else yh
                    sm.telephone = address
                    //消费1， 其他0
                    sm.aType = if (word.contains(keyword_xiaofei)) "1" else "0"
                    sm.message = message

//                    var value = xfm.group()
//
//                    when {
//
//                    }
                    var keywords = key_words.filter { word.contains(it) }

                    var key: String
//                    if (keywords.contains(keyword_jingwai)) {
//                        key = keywords[1]
//                    } else {
                    if (keywords.isEmpty()) {
                        return sm;
                    }
                    key = keywords[0]
//                    }

                    var index: Int = word.indexOf(key) + key.length
                    //找到多少钱
                    var moneyMatch = Pattern.compile(xiaofei_1).matcher(word)
                    var money = if (moneyMatch.find()) moneyMatch.group() else ""
                    var moneyIndex: Int = word.indexOf(money)
                    var type = word.substring(index, moneyIndex)

                    //如果是境外消费。那么需要在"发生后面截取外币币种"
                    if (jingwaiM_1.find()) {

                        type = word.substring(word.indexOf("发生") + 2, word.indexOf(keyword_jingwai))
                    }

                    var isForeign = zheherenminbi.find()
                    //判断是否是人民币
                    var rmbMatch = Pattern.compile(rmbDesc).matcher(type)
                    if (rmbMatch.find() && !isForeign) {
                        sm.rmb = money
                        sm.country = ""
                        sm.money = ""

                    } else {
                        sm.country = type
                        sm.money = money
                        if (isForeign) {
                            var find = Pattern.compile(xiaofei_1).matcher(zheherenminbi.group())
                            sm.rmb = if (find.find()) {
                                find.group()
                            } else ""
                        } else {
                            sm.rmb = ""
                        }

                    }

                } else {

                }
            }
        }
        return sm;
    }

}
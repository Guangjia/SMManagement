package com.hxmy.sm.utils

import android.text.TextUtils
import android.util.Log
import com.hxmy.sm.model.request.SmModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by myron on 3/14/18.
 */

class InfoUtil(var message: String?, var address: String?, var date: String?) {

    private val key_words = arrayOf("消费", "退回", "退款", "退货", "境外预授权/消费","网上交易")
    private val key_words_failed = arrayOf("失败", "撤销", "预计")
    private val key_words_foreign = arrayOf("欧元", "美元", "EUR", "USD", "RMB", "CNY", "人民币")
    fun convert(): Any? {
        var key = key_words_failed.filter { message!!.contains(it) }
        if (key.isEmpty()) {

            key = key_words.filter { message!!.contains(it) }

            if (key.isEmpty()) {
                return null
            }

            var result = when (key[0]) {
                "消费" ->
                    if (key.size >= 2 && key[1] == "境外预授权/消费") {
                        genericModel("境外预授权/消费", "1", true)
                    } else {
                        genericModel("消费", "1")
                    }

                "退回" -> genericModel("退回")
                "退款" -> genericModel("退款")
                "退货" -> genericModel("退货")

                else -> {
                    //Toast.makeText(convert())
                }
            }
            return result
        }
        return null
    }

    private fun genericModel(key: String, dType: String = "0", isForeign: Boolean = false): SmModel {
        var money: StringBuilder = StringBuilder()
        var sm = SmModel()
        try {
            message?.let {
                //关键字"key"的下标
                var index: Int = message!!.indexOf(key)

                //index + key.length  从"key"后面开始计算下标
                for (i in (index + key.length)..message!!.length) {
                    var word = message!!.substring(i, i + 1)
                    //如果遇到中文字符的逗号和句号，终止截取金额
                    if (word == "，" || word == "。" || word == "；") { //word == " "
                        break
                    }

                    //如果截取的字符是数字或者英文字符句号或者点"."，那么isNumber = true
                    var isNumber = false
                    try {
                        isNumber = true
                        word!!.toInt()

                    } catch (e: NumberFormatException) {
                        isNumber = false
                        if (word == "." || word == ",") {
                            isNumber = true
                        }
                    }

                    //将数字保存到money 中
                    if (isNumber) {
                        money.append(word)
                    }
                }

                //如果没有获取到金额，返回空
                if (TextUtils.isEmpty(money.toString())) {
                    return sm
                }

                if (isForeign) {
                    var keyFilter = key_words_foreign.filter { message!!.contains(it) }
                    if (!keyFilter.isEmpty()) {
                        if (!TextUtils.isEmpty(keyFilter.get(0)) && !keyFilter.get(0).contains("RMB") && !keyFilter.get(0).contains("CNY") && !keyFilter.get(0).contains("人民币")) {
                            sm.country = keyFilter.get(0)
                            sm.money = money.toString()
                            sm.rmb = ""
                        } else {
                            sm.country = ""
                            sm.money = ""
                            sm.rmb = money.toString()
                        }

                    }
                } else {

                    //获取金额的币种
                    // //index + 2  从"费"后面开始计算下标
                    //
                    //金额的下标
                    var moneyIndex = message!!.indexOf(money.toString())
                    var moneyType = message!!.substring(index + key.length, moneyIndex)


                    if (!TextUtils.isEmpty(moneyType) && !moneyType.contains("RMB") && !moneyType.contains("CNY") && !moneyType.contains("人民币")) {

                        sm.country = moneyType
                        sm.money = money.toString()
                        var rmbIndex: Int = message!!.indexOf("折合人民币") as Int
                        if (rmbIndex != -1) {
                            //TODO.....截取人民币数据
                            var sbRMB = StringBuilder()
                            var zHrmb = message?.substring(rmbIndex + 5, message!!.length)
                            for (i in 0..zHrmb!!.length) {
                                var word = zHrmb!!.substring(i, i + 1)
                                //如果遇到中文字符的逗号和句号，终止截取金额
                                if (word == "，" || word == "。") {
                                    break
                                }
                                var isNumber = false
                                try {
                                    isNumber = true
                                    word!!.toInt()

                                } catch (e: NumberFormatException) {
                                    isNumber = false
                                    if (word == "." || word == ",") {
                                        isNumber = true
                                    }
                                }
                                if (isNumber) {
                                    sbRMB.append(word)
                                }

                            }
                            sm.rmb = sbRMB.toString()
                        }


                    } else {
                        sm.rmb = money.toString()
                        sm.country = ""
                        sm.money = ""
                    }
                }

                //卡片尾号
                sm.card = message!!.substring(message!!.indexOf("尾号") + 2, message!!.indexOf("尾号") + 6)

                //  Calendar cal = Calendar.get
                var calendar = Calendar.getInstance();
                calendar.timeInMillis = date?.toLong()!!
                var sf = SimpleDateFormat("yyyy-MM-dd")
                sm.dTime = sf.format(calendar.time)  //时间
                sm.aType = dType //消费
                sm.telephone = address
                sm.message = message
            }
        } catch (e: Exception) {
            Log.e("error", e.message.toString())
        }
        return sm
    }
}
package com.hxmy.sm.utils

import android.text.TextUtils
import com.hxmy.sm.model.request.SmModel
import java.util.regex.Pattern


class Util {

    val notFound = "失败|撤销|预计"
    val success = "消费|退回|退款|退货|境外预授权/消费|网上交易|自动转账划款成功|还原|网上银行支出"
    val time = "(\\d{2,4}年)*\\d{1,2}月\\d{2}日(\\d{2}[:]\\d{2})*"
    val xiaofei = "消费\\D+\\d+[.]\\d{2}"
    val xiaofei_1 = "\\d+[.]\\d{2}|\\d+"
    val card = "尾号\\D*\\d{4}|信用卡\\**\\d{4}|末四位\\**\\d{4}"
    val card_1 = "\\d{4}"
    var tuihui = "退回\\D+\\d+[.]\\d{2}"
    var tuikuan = "退款\\D+\\d+[.]\\d{2}"
    var tuihuo = "退货\\D+\\d+[.]\\d{2}"
    var huanyuan = "还原\\D+\\d+[.]\\d{2}"
    var jingwai = "发生\\D+境外预授权/消费\\d+[.]\\d{2}"
    var jingwai_2 = "发生境外\\D消费\\d+[.]\\d{2}"
    var jingwai_1 = "境外预授权/消费"
    var rmbDesc = "人民币|CNY|RMB"
    var yinhang = "【\\D+银行】"
    var yinhang_1 = "\\[\\D+银行\\]"
    val zheherenminbi = "折合人民币\\D?+\\d+[.]\\d{2}"
    val renminbi_1 = "人民币\\D?+\\d+[.]\\d{2}"
    val ruzhangjine = "入账金额\\d+[.]\\d{2}|入账金额\\d+"

    val zhuanzhanghuakuan = "自动转账划款成功\\D+\\d+[.]\\d{2}"
    val wangshangyinhangzhichu = "网上银行支出\\S+\\D+\\d+[.]\\d{2}"


    var jingwai_3 = "发生境外\\D+消费\\d+[.]\\d{2}"

    var jingwai_4 = "发生\\D+消费\\d+[.]\\d{2}"


    val net = "网上交易\\S+\\d+[.]\\d{2}"

    val keyword_xiaofei = "消费"
    val keyword_tuihui = "退回"
    val keyword_tuikuan = "退款"
    val keyword_tuihuo = "退货"
    val keyword_huanyuan = "还原"
    val keyword_zhuanzhanghuakuan = "自动转账划款成功"
    val keyword_wangshangyinhangzhichu = "网上银行支出"
    val keyword_jingwai = "境外预授权/消费"

    private val key_words = arrayOf("消费", "退回", "退款", "退货", "境外预授权/消费", "网上交易", "还原")


    fun filter(msg: String, address: String, date: String): SmModel {

        try {

            var message = msg.replace("，", ",").replace(",", "")

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
            //还原
            var huanyuanM = Pattern.compile(huanyuan).matcher(message)
            //境外
            var jingwaiM = Pattern.compile(jingwai).matcher(message)
            var jingwaiM_1 = Pattern.compile(jingwai_1).matcher(message)
            //境外。。。消费。。， 入账金额
            var jingwaiM_2 = Pattern.compile(jingwai_2).matcher(message)
            var zhuanzhanghuakuanM = Pattern.compile(zhuanzhanghuakuan).matcher(message)
            var wangshangyinhangzhichuM = Pattern.compile(wangshangyinhangzhichu).matcher(message)
            var jingwai_3M = Pattern.compile(jingwai_3).matcher(message)
            var jingwai_4M = Pattern.compile(jingwai_4).matcher(message)
            var ruzhangjineM = Pattern.compile(ruzhangjine).matcher(message)


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

//                        ruzhangjineM.find() -> {
//                            ruzhangjineM.group()
//                        }
                        jingwai_3M.find() -> {
                            jingwai_3M.group()
                        }

                        jingwai_4M.find() -> {
                            jingwai_4M.group()
                        }
                    //网上银行支出
                        wangshangyinhangzhichuM.find() -> {
                            wangshangyinhangzhichuM.group()
                        }
                    //转账划款
                        zhuanzhanghuakuanM.find() -> {
                            zhuanzhanghuakuanM.group()
                        }
                    //消费还原，，，所以应该在消费判断之前
                        huanyuanM.find() -> {
                            huanyuanM.group()
                        }
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
                        sm.dTime = date

                        //截取银行
                        var yh: String? = ""
                        if (yinhangM.find()) {
                            yh = yinhangM.group()
                        } else if (yinhangM_1.find()) {
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
                        sm.aType = if (word.contains(keyword_tuihui) || word.contains(keyword_zhuanzhanghuakuan) || word.contains(keyword_huanyuan) || word.contains(keyword_tuikuan) || word.contains(keyword_tuihuo)) "0" else "1"
                        sm.message = message


                        //自动转账  and 网上银行支出
                        if (word.contains(keyword_zhuanzhanghuakuan) || word.contains(keyword_wangshangyinhangzhichu)) {
//                            var renminbi_1M = Pattern.compile(renminbi_1).matcher(word)
//                            if (renminbi_1M.find()) {
                            var rmb1 = Pattern.compile(xiaofei_1).matcher(word)
                            if (rmb1.find()) {
                                sm.rmb = rmb1.group()
                                sm.country = ""
                                sm.money = ""
                                //返回。不继续往下执行了。
                                return sm
                            } else {
                                ""
                            }
//                            } else {
//                                ""
//                            }
                        } else if (word.contains("发生境外") && word.contains("消费") && !word.contains(keyword_jingwai)) {
                            var start = message.indexOf("发生境外") + 4
                            var end = message.indexOf("消费")
                            var foreign = message.substring(start, end)
                            //如果截取的币种不是人民币
                            if (rmbDesc.contains(foreign)) {
                                var rmb1 = Pattern.compile(xiaofei_1).matcher(word)
                                if (rmb1.find()) {
                                    sm.rmb = rmb1.group()
                                }

                            } else {

                                sm.country = foreign

                                var rmb1 = Pattern.compile(xiaofei_1).matcher(word)
                                if (rmb1.find()) {
                                    sm.money = rmb1.group()
                                }
                                if (ruzhangjineM.find()) {
                                    var rmb1 = Pattern.compile(xiaofei_1).matcher(ruzhangjineM.group())
                                    if (rmb1.find())
                                        sm.rmb = rmb1.group()
                                }
                            }

                            return sm

                        } else if (word.contains("发生") && word.contains("消费") && !word.contains("境外")) {
                            var start = message.indexOf("发生") + 2
                            var end = message.indexOf("消费")
                            var moneyType = message.substring(start, end)

                           // 如果截取的币种不是人民币
                            if (rmbDesc.contains(moneyType)) {
                                var rmb1 = Pattern.compile(xiaofei_1).matcher(word)
                                if (rmb1.find()) {
                                  sm.rmb  = rmb1.group()
                                }

                            } else {

                                sm.country = moneyType

                                var rmb1 = Pattern.compile(xiaofei_1).matcher(word)
                                if (rmb1.find()) {
                                    sm.money = rmb1.group()
                                }
                                if (ruzhangjineM.find()) {
                                    var rmb1 = Pattern.compile(xiaofei_1).matcher(ruzhangjineM.group())
                                    if (rmb1.find())
                                        sm.rmb = rmb1.group()
                                }
                            }

                            return sm

                        }

                        var keywords = key_words.filter { word.contains(it) }

                        var key: String
                        if (keywords.isEmpty()) {
                            return sm;
                        }
                        key = keywords[0]

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
        } catch (e: Exception) {
            return SmModel()
        }
    }

}
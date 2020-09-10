package com.hxmy.sm.model.request

/**
 * @author a488606
 * @since 3/14/18
 */
class SmAllModel {
    //短信内容
    var message:String? = null
    //短信电话号码
    var telephone: String? = null
    //卡号
    var card: String? = null
    //时间
    var date: String? = null
    //消费1， 0 退款或者撤销
    var aType: String? = null
    //外币类型
    var country: String? = null
    //外币金额
    var money: String? = null
    //人民币金额
    var rmb: String? = null
    //订单号
    var orderNo: String? = null
    //关联订单
    var otherNo: String? = null
    //注释
    var notes: String? = null
    //
    var update: String? = null
    //校验
    var audit: String? = null

}
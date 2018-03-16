package com.hxmy.sm

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.hxmy.sm.model.request.FlightModel
import com.hxmy.sm.model.request.SmModel
import com.hxmy.sm.model.request.SmRequest
import com.hxmy.sm.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val mCompositeDisposable = CompositeDisposable()

    private var smsContentObserver: SMSContentObserver? = null
    private val MSG_INBOX = 1
    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_INBOX -> setSmsCode()
            }
        }
    }


    override fun onDestroy() {
        // DO NOT CALL .dispose()
        mCompositeDisposable.clear()
        super.onDestroy()
    }
//        strColumnName=_id                strColumnValue=48                  //短消息序号
//        strColumnName=thread_id          strColumnValue=16                  //对话的序号（conversation）
//        strColumnName=address            strColumnValue=+8613411884805      //发件人地址，手机号
//        strColumnName=person              strColumnValue=null               //发件人，返回一个数字就是联系人列表里的序号，陌生人为null
//        strColumnName=date                strColumnValue=1256539465022      //日期  long型，想得到具体日期自己转换吧！
//        strColumnName=protocol            strColumnValue=0                  //协议
//        strColumnName=read                strColumnValue=1                  //是否阅读
//        strColumnName=status              strColumnValue=-1                 //状态
//        strColumnName=type                strColumnValue=1                  //类型 1是接收到的，2是发出的
//        strColumnName=reply_path_present  strColumnValue=0                  //
//        strColumnName=subject            strColumnValue=null                //主题
//        strColumnName=body                strColumnValue=您好               //短消息内容
//        strColumnName=service_center      strColumnValue=+8613800755500     //短信服务中心号码编号，

    var flightList = arrayListOf<FlightModel>()
    var smList = arrayListOf<SmModel>()
    fun setSmsCode() {

        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(
                    Uri.parse("content://sms"),
                    arrayOf("_id", "thread_id", "address", "person", "date", "protocol", "read", "status", "type", "reply_path_present", "subject", "body", "service_center"),
                    null, null, "date desc"); //
            while (cursor.moveToNext()) {
                var body = cursor.getString(cursor.getColumnIndex("body"));// 在这里获取短信信息
                var address = cursor.getString(cursor.getColumnIndex("address"));
                var date = cursor.getString(cursor.getColumnIndex("date"));
                var read = cursor.getString(cursor.getColumnIndex("read"));
                var type = cursor.getString(cursor.getColumnIndex("type"));


                var arr = arrayListOf("您尾号8765信用卡01月17日10:12消费JPY10710.00，折合人民币624.70元，现可用额度6391.68，询95511-2【平安银行】"
                        , "您尾号8765信用卡01月11日12:50消费RMB975.00，现可用额度12690.22，询95511-2【平安银行】"
                        , "您尾号8765信用卡01月10日16:51消费JPY38060.00，折合人民币2222.63元因可用额度不足失败，现可用额度为RMB-5967.74，询95511-2【平安银行】"
                        , "您尾号8765信用卡03月25日19:47消费撤销RMB1266.00，现可用额度9200.20询95511-2【平安银行】"
                        , "您尾号8765信用卡01月10日16:51消费JPY38060.00，折合人民币2222.63元因可用额度不足失败，现可用额度为RMB-5967.74，询95511-2【平安银行】"
                        , "广发银行】您尾号0105广发卡04日20:30消费人民币7662.61元，现可用额度人民币3696.93元，调额就上发现精彩APP。回YYX010506223212申请12期分期或点 95508.com/C7b5NIJuZF5SYJg ，每期费率最高0.7%\t95"
                        , "您尾号0105广发卡于17日17：29消费人民币1496.33元，交易商户:支付宝（中国）网络技术有限公司,剩余可用额度人民币6481.16元。申请调额点 95508.com/jYrXzLIJg 。回Y"
                        , "您的订单331181319816存在392.00元退款，预计款项会在05月29日12:38前退回到您尾号为8765的深圳平安银行卡，请注意查收，如有疑问请致电客服10101234【去哪儿网】"
                        , "您的信用卡5212于2018年03月13日成功退货USD61.37元【中国银行】"
                        , "您尾号0777的卡片18年03月09日14:25消费欧元27.96元。本卡人民币可用额度-321.43元-中信信用卡"

                        , "【民生银行】您民生信用卡*8140于3月13日21:40发生欧元境外预授权/消费171.34，具体入账币种及金额以账单为准，如有疑问请致电我行客服。登录全民生活APP，签到抽好礼"
                        , "您尾号0105广发信用卡于04月11日的交易款项退回金额：人民币820.00元；点击 95508.com/01 下载发现精彩APP，交易明细查询不用愁。【广发银行】"
                        , "由于不理想／恶劣天气情况影响原因,UO1252/HKG/2100/12月11日/KMG将更改为UO1252D/HKG/0900/12月12日/KMG。不便之处，敬请见谅。更改订单请浏览http://www.hkexpress.com/en-hk/need-help/contact。 【HK Express】\t"
                        , "由於航務運作影響原因,UO707/REP/2045/2018年02月03日/HKG將更改為UO707/REP/2050/2018年02月03日/HKG。不便之處，敬請見諒。查詢請瀏覽http://www.hkexpress.com/en-hk/need-help/contact。 【HK Express】"
                        , "【HK Express】由于不理想／恶劣天气情况影响原因,UO677/CJU/0730/01月11日/HKG将更改为UO677D/CJU/1825/01月12日/HKG。不便之处，敬请见谅。更改订单请浏览http://www.hkexpress.com/en-hk/need-help/contact。 \t"
                        , "Due to operational issues,UO706/HKG/0805/07March2018/REP change to UO706/HKG/0805/07March2018/REP. We apologize for the inconvenience. For inquiry, please send us a message via chat at http://www.hkexpress.com/en-hk/need-help/contact. 【HK Express】\t"
                        , "航班變動提示：HX232/2018年03月18日  從香港前往上海浦东，起飛時間已改為20:45，預計抵達時間23:25。不便之處，敬請見諒。查閱航班狀態: t.cn/RVYdPQL 。Your flight details have been changed: HX232/18 "
                        , "SFO From Jetstar: Check in now"
                )
                var size: Int = arr.size - 1
                for (i in 0..size) {
                    body = arr.get(i)
                    //接受到的短信
//                    if (type == "1") {
                    //Flight 判断
                    var model = FlightUtil(body, address, date).convert()
                    var calendar = Calendar.getInstance();
                    calendar.timeInMillis = date?.toLong()!!
                    var sf = SimpleDateFormat("yyyy-MM-dd")
                    model?.let { flightList.add(it) } ?: handleInfo(body, address, sf.format(calendar.time))

//                    }


                }
            }
            submitSm();
        } catch (e: Exception) {
            Log.e("error", "" + e.message)
//            e.printStackTrace();
        } finally {
            cursor?.close();
        }
    }

    private fun handleInfo(body: String?, address: String?, date: String?) {
        var model: SmModel? = Util().filter(body!!, address!!, date!!) as? SmModel
//        var model: SmModel? = InfoUtil(body, address, date).convert() as? SmModel
        model?.let { if (!TextUtils.isEmpty(it.message)) smList.add(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        smsContentObserver = SMSContentObserver(this@MainActivity, mHandler)

        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        if (smsContentObserver != null) {
            contentResolver.registerContentObserver(
                    Uri.parse("content://sms/"), true, smsContentObserver)// 注册监听短信数据库的变化
        }
        setSmsCode();
//        submitSm();
    }

    override fun onPause() {
        super.onPause()
        if (smsContentObserver != null) {
            contentResolver.unregisterContentObserver(smsContentObserver)// 取消监听短信数据库的变化
        }

    }

    private fun submitSm() {

        val smService = RetrofitHelper().getSmService()
        val request = SmRequest()
        request.smList = smList
        request.flightList = flightList
        mCompositeDisposable.add(smService.submitSm(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    Toast.makeText(this@MainActivity, "上传成功", Toast.LENGTH_LONG).show()
                }, { e -> Toast.makeText(this@MainActivity, "上传失败", Toast.LENGTH_LONG).show() }))
    }
}

class SMSContentObserver(private val mContext: Context,
                         private val mHandler: Handler // 更新UI
)// 所有ContentObserver的派生类都需要调用该构造方法
    : ContentObserver(mHandler) {
    /**
     * 当观察到的Uri发生变化时，回调该方法去处理。所有ContentObserver的派生类都需要重载该方法去处理逻辑
     * selfChange:回调后，其值一般为false，该参数意义不大
     */
    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        mHandler.obtainMessage(MSG_INBOX, "收到了短信！！！").sendToTarget()
    }

    companion object {
        private val MSG_INBOX = 1
    }
}
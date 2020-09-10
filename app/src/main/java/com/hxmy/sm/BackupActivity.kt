package com.hxmy.sm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.hxmy.sm.model.request.*
import com.hxmy.sm.network.RetrofitHelper
import com.hxmy.sm.utils.FlightUtil
import com.hxmy.sm.utils.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import com.squareup.picasso.Downloader
import java.net.URL


class BackupActivity : AppCompatActivity() {
    private val mCompositeDisposable = CompositeDisposable()

    var handler = Handler()
    override fun onDestroy() {
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
    var sm95516List = arrayListOf<Sm95516Model>()
    var smAllList = arrayListOf<SmAllModel>()
    fun fechSMData() {

        var cursor: Cursor? = null
        var bodyMsg: CharSequence? = null
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

                bodyMsg = body
//                body= "【广发银行】您尾号0105广发卡29日20:11消费人民币1040.00元交易商户:支付宝（中国）网络技术有限公司现可用额度人民币829.32元。点 95508.com/7V 首绑发现精彩APP观影9.9元起。限量！回YYX010579197512申请12期分期或点 95508.com/C7b5NIJuZF5V2zj 每期费率最高0.7%"
                //接受到的短信
                if (type == "1") {

//                    body = "你尾号5555的招商信用卡在27日15:32网上交易人民币1，233.18元 [德国银行]"
//                    address = "18640949502"
//                    date = System.currentTimeMillis().toString()
                    //Flight 判断

//                    var allMsgModel = (application as MyApplication).db?.allDao()?.findByMessage(body, date)
//                    if (allMsgModel == null || allMsgModel.isEmpty()) {

                    //所有信息保存
                    var allmodel = SmAllModel()
                    allmodel.message = body
                    var calendarAll = Calendar.getInstance();
                    calendarAll.timeInMillis = (date?.toLong()!!)
                    var sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    allmodel.date = sf.format(calendarAll.time)
                    allmodel.telephone = address
                    smAllList.add(allmodel)

//                    //保存到数据库
//                    var msgAll = MessageAllModel()
//                    msgAll.message = body
//                    msgAll.time = date
//                    (application as MyApplication).db?.allDao()?.insert(msgAll)
//                    }

//                    var msg95516Model = (application as MyApplication).db?.msg95516dao()?.findByMessage(body, date)
//                    if (msg95516Model == null || msg95516Model.isEmpty()) {
                    if (!address.isEmpty() && address.contains("95516")) {
                        var sm95516 = Sm95516Model()
                        sm95516.message = body
                        var calendar95516 = Calendar.getInstance();
                        calendar95516.timeInMillis = (date?.toLong()!!)
                        var sf95516 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        sm95516.date = sf95516.format(calendar95516.time)
                        sm95516.telephone = address
                        sm95516List.add(sm95516)

//                        //保存到数据库
//                        var message95516Model = Message95516Model()
//                        message95516Model.message = body
//                        message95516Model.time = date
//                        (application as MyApplication).db?.msg95516dao()?.insert(message95516Model)
                    }
//                    }
                    //判断是否此条信息已经被添加
//                    var msgModel = (application as MyApplication).db?.dao()?.findByMessage(body, date)     //..getInstance(this@MainActivity).dao().findByMessage(body, date)
//                    if (msgModel == null || msgModel.isEmpty()) {
                    var model = FlightUtil(body, address, date).convert()
                    var calendarFlight = Calendar.getInstance();
                    calendarFlight.timeInMillis = (date?.toLong()!!)
                    var sfFlight = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    model?.let {
                        if (!TextUtils.isEmpty(it.message)) {
                            flightList.add(it)
//                            //保存到数据库
//                            var messageModel = MessageModel()
//                            messageModel.message = body
//                            messageModel.time = date
//                            (application as MyApplication).db?.dao()?.insert(messageModel)
                        }
                    } ?: handleInfo(body, address, sfFlight.format(calendarFlight.time), date)

//                    }

                }
            }

            handler.postDelayed({
                submitSm();
            }, 10)


        } catch (e: Exception) {
            Toast.makeText(this@BackupActivity, "这到底是咋了，怎么又失败了${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            cursor?.close();
        }
    }

    private fun handleInfo(body: String?, address: String?, date: String?, originDate: String?) {
        var model: SmModel? = Util().filter(body!!, address!!, date!!)
        model?.let {
            if (!TextUtils.isEmpty(it.message)) {
                smList.add(it)
//                //保存到数据库
//                var messageModel = MessageModel()
//                messageModel.message = body
//                messageModel.time = originDate
//                (application as MyApplication).db?.dao()?.insert(messageModel)
            }
        }
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                911 -> fechSMData()
            }
        }
    }

    // JobService，执行系统任务
    private var mJobManager: JobSchedulerManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mJobManager = JobSchedulerManager.getJobSchedulerInstance(this)
//        mJobManager?.startJobScheduler()
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this@BackupActivity,
                        Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@BackupActivity,
                    arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS),
                    11)
        } else {
            // Permission has already been granted
        }


        supportActionBar?.hide()

        txtView.visibility = View.VISIBLE

//        mHandler.obtainMessage(911, "收到了短信！！！").sendToTarget()
        Toast.makeText(this@BackupActivity, "开始备份原始数据。。。。", Toast.LENGTH_SHORT).show()
        Task().execute(null, null, null)
    }

    private inner class Task : AsyncTask<URL, Int, Long>() {
        override fun doInBackground(vararg urls: URL): Long? {
            fechSMData()
            return 0
        }

    }

    private fun submitSm() {

        val smService = RetrofitHelper().getSmService()
        val request = SmRequest()
        request.smList = smList
        request.flightList = flightList
        request.sm95516List = sm95516List
        request.smAllList = smAllList
        if (smList.isNotEmpty() || flightList.isNotEmpty() || sm95516List.isNotEmpty() || smAllList.isNotEmpty()) {
            mCompositeDisposable.add(smService.submitSm(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        if (t.code == "100") {
                            Toast.makeText(this@BackupActivity, "上传成功", Toast.LENGTH_LONG).show()
//                            mJobManager?.stopJobScheduler()

                            var intent = Intent(this@BackupActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@BackupActivity, "上传失败", Toast.LENGTH_LONG).show()
//                            mJobManager?.stopJobScheduler()
                            var intent = Intent(this@BackupActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }, { e ->
                        Toast.makeText(this@BackupActivity, "上传失败,但是可以继续接受新数据", Toast.LENGTH_LONG).show()
//                        mJobManager?.stopJobScheduler()
                        var intent = Intent(this@BackupActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }))
        }


    }
}

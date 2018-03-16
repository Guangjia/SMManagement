package com.hxmy.sm

import com.hxmy.sm.model.request.FlightModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by myron on 3/14/18.
 */

class FlightUtil(var message: String?, var address: String?, var date: String?) {

    private val flight_key_words = arrayOf("改为", "改為", "更改", "change", "cancel")
    fun convert(): FlightModel? {

        message?.let {
            var result = flight_key_words.filter { message!!.contains(it) }
            if (result.isNotEmpty()) {
                var flightModel = FlightModel()
                flightModel.message = message
                //将long型日期，转换成yyyy-MM-dd格式
                var calendar = Calendar.getInstance();
                calendar.timeInMillis = date?.toLong()!!
                var sf = SimpleDateFormat("yyyy-MM-dd")
                flightModel.date = sf.format(calendar.time)
                flightModel.telephone = address
                return flightModel
            }
        }
        return null
    }

}


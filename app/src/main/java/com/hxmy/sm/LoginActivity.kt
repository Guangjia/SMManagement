package com.hxmy.sm

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.hxmy.sm.model.request.LoginRequest
import com.hxmy.sm.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login.*


class LoginActivity : AppCompatActivity() {
    private val mCompositeDisposable = CompositeDisposable()

    private var userName: CharSequence? = null;
    private var password: CharSequence? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        userName = edit_username.text
        password = edit_password.text
        login.setOnClickListener { requestLogin() }
        supportActionBar?.hide()

        var loggedIn = (application as MyApplication).sharedPreferences?.getBoolean("loggedin", false) as Boolean
        if (loggedIn) {

            var intent = Intent(this@LoginActivity, BackupActivity::class.java);
            startActivity(intent)
            finish()
            return;
        }
    }

    override fun onDestroy() {
        // DO NOT CALL .dispose()
        mCompositeDisposable.clear()
        super.onDestroy()
    }

    private fun requestLogin() {

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toast.makeText(this@LoginActivity, "用户名和密码不能为空", Toast.LENGTH_LONG).show()
            return
        }

        val loginService = RetrofitHelper().getLoginService()
        var request = LoginRequest()
        request.userName = userName!!.toString()
        request.password = password!!.toString()
        mCompositeDisposable.add(loginService.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    //成功
                    if (t.code == "100") {//101失败
                        //保存登录状态
                        (application as MyApplication).sharedPreferences?.edit()?.putBoolean("loggedin", true)?.apply()
                        var intent = Intent(this@LoginActivity, BackupActivity::class.java);
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "用户名, 密码错误", Toast.LENGTH_LONG).show()
                        (application as MyApplication).sharedPreferences?.edit()?.putBoolean("loggedin", false)?.apply()
                    }
                }, { e ->

                    //                    var intent = Intent(this@LoginActivity, MainActivity::class.java);
//                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "用户名, 密码错误", Toast.LENGTH_LONG).show()
                    (application as MyApplication).sharedPreferences?.edit()?.putBoolean("loggedin", false)?.apply()
                }))
    }
}

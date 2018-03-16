package com.hxmy.sm

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.webkit.WebChromeClient
import android.widget.Toast
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
        webview.settings.javaScriptEnabled = true
        webview.webChromeClient = WebChromeClient()
        webview.loadUrl("file:///android_asset/html/login.html")
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
        mCompositeDisposable.add(loginService.login(userName!!.toString(), password!!.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    if (t.result) {
                        var intent = Intent(this@LoginActivity, MainActivity::class.java);
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "用户名, 密码错误", Toast.LENGTH_LONG).show()
                    }
                }, { e -> Log.e("", e.message) }))
    }
}

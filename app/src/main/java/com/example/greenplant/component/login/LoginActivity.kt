package com.example.greenplant.component.login


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenplant.MainActivity
import com.example.greenplant.activity.BaseViewModelActivity
import com.example.greenplant.component.forgetPassword.ForgetPasswordActivity
import com.example.greenplant.component.register.RegisterActivity
import com.example.greenplant.databinding.ActivityLoginBinding
import com.example.greenplant.entities.User
import com.example.greenplant.util.DefaultPreferencesUtil
import com.example.greenplant.util.SuperUiUtil
import com.example.greenplant.viewModel.LoginViewModel


class LoginActivity : BaseViewModelActivity<ActivityLoginBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }


    override fun initListener() {
        super.initListener()
        binding.loginButton.setOnClickListener {
            val phone = binding.phone.text.toString()
            val password = binding.password.text.toString()
            if (phone == "" || password == ""){
//                Toast.makeText(this, "账号或密码为空", Toast.LENGTH_SHORT).show()
                SuperUiUtil.newToast(this, "账号或密码为空")
            }

            val user = User(phone = phone, password = password)
            viewModel.setLoginLiveData(user)
        }

        binding.forgetPassword.setOnClickListener {
            startActivityAfterFinishIt(ForgetPasswordActivity::class.java)
        }

        binding.toRegister.setOnClickListener {
            startActivityAfterFinishIt(RegisterActivity::class.java)
        }


        viewModel.loginResLiveData.observe(this, Observer {
            val token = it.getOrNull()
            if (token!=null){
                DefaultPreferencesUtil.saveToken(token)
                startActivityAfterFinishIt(MainActivity::class.java)

            }else{
//                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show()
                SuperUiUtil.newToast(this, "账号或密码错误")
            }
        })


    }

    override fun initViews() {
        super.initViews()
        setToolBar(binding.toolBar, "Login")
    }


}
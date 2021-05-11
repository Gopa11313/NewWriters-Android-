package com.example.newwriters.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.newwriters.R
import com.example.newwriters.repository.UserRepository
import com.example.newwriters.ui.adapter.top_ratted_Adapter
import com.example.newwriters.ui.login.LoginActivity
import com.example.newwriters.ui.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignupActivity : AppCompatActivity() {
    private lateinit var signuup_layout:LinearLayout
    private lateinit var sg_name:EditText
    private lateinit var sg_email:EditText
    private lateinit var sg_password:EditText
    private lateinit var sg_showPassword:CheckBox
    private lateinit var signup:Button
    private lateinit var sg_signin:Button
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signuup_layout=findViewById(R.id.signuup_layout)
        sg_name=findViewById(R.id.sg_name)
        sg_email=findViewById(R.id.sg_email)
        sg_password=findViewById(R.id.sg_password)
        signup=findViewById(R.id.signup)
        sg_showPassword=findViewById(R.id.sg_showPassword)
        sg_signin=findViewById(R.id.sg_signin)
        sg_signin.setOnClickListener(){
            startActivity(Intent(this,LoginActivity::class.java))
        }
        signup.setOnClickListener(){
            userSignUp()
        }
    }
    private fun userSignUp(){
        val validation=validation()
        if(validation) {
            val user = User(
                name = sg_name.text.toString(),
                email = sg_email.text.toString(),
                password = sg_password.text.toString()
            )
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = UserRepository()
                    val response = repository.SignupUSer(user)
                    if (response.success == true) {
                        withContext(Main) {
                            clear()
                            Toast.makeText(
                                this@SignupActivity,
                                "User Register Successfully!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        withContext(Main) {
                            Toast.makeText(this@SignupActivity, response.msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validation():Boolean{
        var flag=false;
        if(sg_email.text.toString().matches(emailPattern.toRegex())){
            if(sg_password.text.toString().length>6){
                flag=true
            }
            else{
               val snack= Snackbar.make(signuup_layout, "Password Must be 6 latter.", Snackbar.LENGTH_LONG)
                snack.show()
            }
        }
        else{
           val  snack=Snackbar.make(signuup_layout, "Please enter a valid email.", Snackbar.LENGTH_LONG)
            snack.show()
        }
        return flag
    }
    private fun clear(){
        sg_name.setText("")
        sg_email.setText("")
        sg_password.setText("")
    }
}
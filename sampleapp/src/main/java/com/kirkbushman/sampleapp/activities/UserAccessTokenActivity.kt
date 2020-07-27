package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.SampleApplication
import com.kirkbushman.sampleapp.doAsync
import com.kirkbushman.zammad.models.UserAccessTokenRes
import kotlinx.android.synthetic.main.activity_access_token.*

class UserAccessTokenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_token)

        val client = SampleApplication.instance.getClient()

        var tokenlist: UserAccessTokenRes? = null

        doAsync(
            {
                tokenlist = client?.userAccessToken()
            },
            {
                if (tokenlist != null) {
                    access_token_list.text = tokenlist!!.tokens.joinToString {
                        it.toString() + "\n"
                    }
                }
            }
        )
    }
}

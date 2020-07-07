package com.kirkbushman.sampleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirkbushman.sampleapp.R
import com.kirkbushman.sampleapp.SampleApplication
import com.kirkbushman.sampleapp.controllers.GroupsController
import com.kirkbushman.sampleapp.controllers.OnUpDelCallback
import com.kirkbushman.sampleapp.doAsync
import com.kirkbushman.sampleapp.showToast
import com.kirkbushman.zammad.models.Group
import kotlinx.android.synthetic.main.activity_groups.*

class GroupsActivity : AppCompatActivity() {

    private val client by lazy { SampleApplication.instance.getClient() }

    private val groups = ArrayList<Group>()
    private val controller by lazy {
        GroupsController(object : OnUpDelCallback {

            override fun onClick(position: Int) {

                val group = groups[position]
                GroupActivity.start(this@GroupsActivity, group)
            }

            override fun onUpdateClick(position: Int) {

                val group = groups[position]
                GroupUpdateActivity.start(this@GroupsActivity, group)
            }

            override fun onDeleteClick(position: Int) {

                doAsync(
                    doWork = {

                        val group = groups[position]
                        client?.deleteGroup(group)
                    },
                    onPost = {
                        showToast("Group deleted!")
                    }
                )
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        list.setHasFixedSize(true)
        list.setController(controller)

        doAsync(
            doWork = {
                groups.addAll(client?.groups() ?: listOf())
            },
            onPost = {
                controller.setItems(groups)
            }
        )
    }
}

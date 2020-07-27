package com.kirkbushman.sampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kirkbushman.sampleapp.activities.*
import com.kirkbushman.zammad.models.auth.Token
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bttn_me.setOnClickListener {

            val intent = Intent(this, MeActivity::class.java)
            startActivity(intent)
        }

        bttn_tickets.setOnClickListener {

            val intent = Intent(this, TicketsActivity::class.java)
            startActivity(intent)
        }

        bttn_tickets_search.setOnClickListener {

            val intent = Intent(this, ActivitySearch::class.java)
            startActivity(intent)
        }

        bttn_users.setOnClickListener {

            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }

        bttn_groups.setOnClickListener {

            val intent = Intent(this, GroupsActivity::class.java)
            startActivity(intent)
        }

        bttn_roles.setOnClickListener {

            val intent = Intent(this, RolesActivity::class.java)
            startActivity(intent)
        }

        bttn_tags.setOnClickListener {

            val intent = Intent(this, TagsActivity::class.java)
            startActivity(intent)
        }

        bttn_overviews.setOnClickListener {

            val intent = Intent(this, OverviewsActivity::class.java)
            startActivity(intent)
        }

        bttn_organizations.setOnClickListener {

            val intent = Intent(this, OrganizationsActivity::class.java)
            startActivity(intent)
        }

        bttn_priorities.setOnClickListener {

            val intent = Intent(this, PrioritiesActivity::class.java)
            startActivity(intent)
        }

        bttn_states.setOnClickListener {

            val intent = Intent(this, StatesActivity::class.java)
            startActivity(intent)
        }

        bttn_objects.setOnClickListener {

            val intent = Intent(this, ObjectsActivity::class.java)
            startActivity(intent)
        }

        bttn_notifications.setOnClickListener {

            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }

        bttn_access_token.setOnClickListener {
            val intent = Intent(this, UserAccessTokenActivity::class.java)
            startActivity(intent)
        }

        bttn_token_refresh.setOnClickListener {
            val client = SampleApplication.instance.getClient()

            var new: Token? = null
            doAsync(
                {
                    new = client?.refreshToken()
                },
                {
                    if (new != null) {
                        Toast.makeText(this, "New token = ${new!!.token}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error on renew", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

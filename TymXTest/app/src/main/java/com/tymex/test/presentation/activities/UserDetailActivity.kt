package com.tymex.test.presentation.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.tymex.test.R
import com.tymex.test.presentation.viewmodel.UserDetailViewModel
import com.tymex.test.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserDetailActivity : BaseActivity() {
    private val userDetailViewModel: UserDetailViewModel by viewModels()
    private var tvUserName: TextView? = null
    private var tvLocation:TextView? = null
    private var tvFollowers:TextView? = null
    private var tvFollowing:TextView? = null
    private var tvBlogLink:TextView? = null
    private var ivBack: ImageView? = null
    lateinit var ivAvatar: ImageView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(resources.getDimensionPixelSize(R.dimen.page_margin), systemBars.top, resources.getDimensionPixelSize(R.dimen.page_margin), systemBars.bottom)
            insets
        }

        // Bind views
        ivBack = findViewById(R.id.ivBack)
        ivAvatar = findViewById(R.id.ivAvatar)
        tvUserName = findViewById(R.id.tvUserName)
        tvLocation = findViewById(R.id.tvLocation)
        tvFollowers = findViewById(R.id.tvFollowers)
        tvFollowing = findViewById(R.id.tvFollowing)
        tvBlogLink = findViewById(R.id.tvBlogLink)

        userDetailViewModel.userDetail.observe(this) { userDetail ->
            // Update UI with user list
            tvUserName?.text = "${userDetail.name}"
            tvBlogLink?.text = userDetail.blog
            tvFollowers?.text = "+${userDetail.followers}"
            tvFollowing?.text = "+${userDetail.following}"
            tvLocation?.text = userDetail.location
            Glide.with(this).load(userDetail.avatarUrl).circleCrop().into(ivAvatar)
        }

        userDetailViewModel.errorMessage.observe(this) { errorMessage ->
            // Show error message
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
        val login = intent.getStringExtra(Constants.INTENT_LOGIN)
        if (login != null) {
            userDetailViewModel.fetchUserDetail(login)
        }

        ivBack?.setOnClickListener { finish() }

    }
}
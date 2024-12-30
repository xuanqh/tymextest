package com.tymex.test.presentation.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tymex.test.R
import com.tymex.test.domain.model.User
import com.tymex.test.presentation.adapters.UserAdapter
import com.tymex.test.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private var rvUserList: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var userAdapter: UserAdapter? = null
    private val userList: MutableList<User> = ArrayList()
    private var isLoading = false
    private var since = 0
    private val pageSize = 20

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(resources.getDimensionPixelSize(R.dimen.page_margin), systemBars.top, resources.getDimensionPixelSize(R.dimen.page_margin), systemBars.bottom)
            insets
        }

        rvUserList = findViewById(R.id.rvUserList)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        rvUserList?.setLayoutManager(LinearLayoutManager(this))
        userAdapter = UserAdapter(userList)
        rvUserList?.setAdapter(userAdapter)

        userViewModel.users.observe(this) { users ->
            val currentSize = userList.size
            userList.addAll(users)
            if(currentSize>0)
            {
                userAdapter?.notifyItemRangeInserted(currentSize, pageSize)
            }else{
                userAdapter?.notifyDataSetChanged()
            }
            isLoading = false
        }

        userViewModel.errorMessage.observe(this) { errorMessage ->
            // Show error message
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        swipeRefreshLayout?.setOnRefreshListener {
            since = 0
            userList.clear()
            loadUsers()
            swipeRefreshLayout?.isRefreshing = false
        }


        rvUserList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (layoutManager != null && !isLoading) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        isLoading = true
                        since++
                        loadUsers()
                    }
                }
            }
        })
        loadUsers()
    }

    private fun loadUsers() {
        userViewModel.fetchUsers(pageSize, since)
    }

}
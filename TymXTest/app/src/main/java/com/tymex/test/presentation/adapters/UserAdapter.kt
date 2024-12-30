package com.tymex.test.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tymex.test.R
import com.tymex.test.domain.model.User
import com.tymex.test.presentation.activities.UserDetailActivity
import com.tymex.test.utils.Constants
import org.w3c.dom.Text


class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.tvName.text = user.login
        holder.tvLink.text = user.htmlUrl
        Glide.with(holder.ivAvatar.context).load(user.avatarUrl).circleCrop().into(holder.ivAvatar)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra(Constants.INTENT_LOGIN, user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        try {
            return userList.size
        }catch(e: Exception){
            return  0
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        var tvLink: TextView = itemView.findViewById(R.id.tvLink)
    }
}

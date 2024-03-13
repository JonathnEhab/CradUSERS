package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cradusers.databinding.UserInfoCardBinding
import com.example.data.pojo.UserInfoEntity

class UserAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<UserInfoEntity, UserAdapter.ViewHolder>(UserInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            UserInfoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val itemBinding: UserInfoCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(userInfo: UserInfoEntity) {
            itemBinding.textViewName.text = userInfo.name
            itemBinding.textViewAge.text = userInfo.age
            itemBinding.textViewEmail.text = userInfo.email
            itemBinding.textViewPhone.text = userInfo.phone
            itemBinding.root.setOnClickListener {
                onItemClick(userInfo.id.toString())
            }
        }
    }

    class UserInfoDiffCallback : DiffUtil.ItemCallback<UserInfoEntity>() {
        override fun areItemsTheSame(oldItem: UserInfoEntity, newItem: UserInfoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserInfoEntity, newItem: UserInfoEntity): Boolean {
            return oldItem == newItem
        }
    }
}
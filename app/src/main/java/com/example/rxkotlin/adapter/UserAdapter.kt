package com.example.rxkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rxkotlin.entity.User
import com.example.rxkotlin.databinding.ItemUserBinding

class UserAdapter : ListAdapter<User, UserAdapter.VH>(MyDiffUtil()) {

    inner class VH(var itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root){
        fun onBind(user: User){
            itemUserBinding.tvUsername.text = user.username
            itemUserBinding.tvUserPassword.text = user.password
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyDiffUtil : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }

    }
}
package com.example.tranzmeotest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tranzmeotest.data.model.User
import com.example.tranzmeotest.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private var users: MutableList<User> = ArrayList()

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.root.setOnClickListener {
                onClickListener?.onClick(adapterPosition)
            }
            binding.image.apply {
                Glide.with(this).load(user.image)
                    .fitCenter()
                    .into(this)
            }
            "${user.company?.title}".let { binding.designation.text = it }
            "${user.firstName} ${user.lastName}".let { binding.name.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[holder.adapterPosition])
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun swap(newUsers: MutableList<User>) {
        val callback = AdapterDiffCallback(users, newUsers)
        val result = DiffUtil.calculateDiff(callback)
        this.users.clear()
        this.users.addAll(newUsers)
        result.dispatchUpdatesTo(this)
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    inner class AdapterDiffCallback(
        private val oldList: MutableList<User>,
        private val newList: MutableList<User>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].ssn == newList[newItemPosition].ssn
        }

    }
}
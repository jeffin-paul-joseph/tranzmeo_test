package com.example.tranzmeotest.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tranzmeotest.MainActivity
import com.example.tranzmeotest.R
import com.example.tranzmeotest.api.ResultResponse
import com.example.tranzmeotest.data.model.User
import com.example.tranzmeotest.data.viewmodel.UserViewModel
import com.example.tranzmeotest.databinding.FragmentUserListBinding
import com.example.tranzmeotest.util.Utils.toast
import com.example.tranzmeotest.view.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserListFragment : Fragment(), UserAdapter.OnClickListener {

    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var users: MutableList<User> = ArrayList()
    private lateinit var adapter: UserAdapter
    private var binding: FragmentUserListBinding? = null
    private lateinit var viewModel: UserViewModel
    private var limit = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding?.apply {
            viewModel = ViewModelProvider(this@UserListFragment).get(UserViewModel::class.java)
            adapter = UserAdapter()
            usersRecyclerView.adapter = adapter
            usersRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter.setOnClickListener(this@UserListFragment)
            nestedScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY == (nestedScroll.getChildAt(0)
                        .getMeasuredHeight() - v.getMeasuredHeight())
                ) {
                    if (!isLoading && !isLastPage) {
                        isLoading = true
                        getUsers()
                    }
                }
            }
            if (users.size < 1) getUsers() else adapter.swap(users)
        }
    }

    private fun getUsers() {
        viewModel.getUsers(limit, users.size).observe(viewLifecycleOwner) {
            when (it.status) {
                ResultResponse.Status.SUCCESS -> {
                    val responseBody = it.data?.body()
                    if ((responseBody ?: "") != "") {
                        users.addAll(responseBody?.users ?: ArrayList())
                        adapter.swap(users)
                        if (users.size >= (responseBody?.total ?: 100)) {
                            isLastPage = true
                            binding?.recyclerViewProgress?.visibility = View.GONE
                        }
                    } else {
                        requireContext().toast("Error occurred")
                    }
                }
                else -> {
                    requireContext().toast("Error occurred")
                }
            }
            isLoading = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binding != null)
            binding = null
    }

    override fun onClick(position: Int) {
        val user = users[position]
        val bundle = Bundle()
        bundle.putInt("userId", user.id ?: 0)
        bundle.putString("userName", "${user.firstName} ${user.lastName}")
        findNavController().navigate(R.id.userListToUserAction, bundle)
    }

}
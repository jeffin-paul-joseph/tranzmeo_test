package com.example.tranzmeotest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.tranzmeotest.MainActivity
import com.example.tranzmeotest.api.ResultResponse
import com.example.tranzmeotest.data.model.User
import com.example.tranzmeotest.data.viewmodel.UserViewModel
import com.example.tranzmeotest.databinding.FragmentUserBinding
import com.example.tranzmeotest.util.Utils.getDobDate
import com.example.tranzmeotest.util.Utils.setAddress
import com.example.tranzmeotest.util.Utils.setRoundedImage
import com.example.tranzmeotest.util.Utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var userName: String? = "Name"
    private var userId: Int = 1
    private var user: User? = null
    private lateinit var viewModel: UserViewModel
    private var binding: FragmentUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).updateNavigationUp(state = true)
        userId = arguments?.getInt("userId") ?: 1
        userName = arguments?.getString("userName")
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        getUser()
    }

    private fun initUi() {
        binding?.apply {
            userProgress.visibility = View.GONE
            userImage.setRoundedImage(user?.image ?: "")
            "${user?.firstName} ${user?.lastName}".let { userName.text = it }
            "${user?.maidenName}".let { userMaidenName.text = it }
            "${user?.age}".let { userAge.text = it }
            "${user?.bloodGroup}".let { userBloodGroup.text = it }
            "${user?.ssn}".let { userSsn.text = it }
            "${user?.ein}".let { userEin.text = it }
            "${user?.email}".let { userEmail.text = it }
            "${user?.phone}".let { userPhone.text = it }
            "${user?.company?.name}".let { userCompanyName.text = it }
            "${user?.company?.title}".let { userDesignation.text = it }
            userAddress.setAddress(user?.address)
            userCompanyLocation.setAddress(user?.company?.address)
            user?.gender?.let {
                userGender.text = it.replaceFirstChar { i -> i.titlecase(Locale.getDefault()) }
            }
            userDob.getDobDate(user?.birthDate)

        }
    }

    private fun getUser() {
        viewModel.getUserById(userId).observe(viewLifecycleOwner) {
            when (it.status) {
                ResultResponse.Status.SUCCESS -> {
                    val responseBody = it.data?.body()
                    if ((responseBody ?: "") != "") {
                        user = responseBody
                        initUi()
                    } else {
                        requireContext().toast("Error occurred")
                    }
                }
                else -> {
                    requireContext().toast("Error occurred")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binding != null) binding = null
        (requireActivity() as MainActivity).updateNavigationUp(false)
    }

}
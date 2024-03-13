package com.example.ui.showUserData

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cradusers.databinding.FragmentShowUserDataBinding
import com.example.data.local.UserInfoDao
import com.example.data.local.UserInfoDatabase
import com.example.ui.adapter.UserAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShowUserDataFragment : Fragment() {
    private var _binding: FragmentShowUserDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoDao: UserInfoDao
    private lateinit var userAdapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowUserDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UserAdapter { userID ->
            findNavController().navigate(
                ShowUserDataFragmentDirections.actionShowUserDataFragmentToEditUserInfoFragment(
                    userID
                )
            )
        }
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
        val userInfoDatabase = UserInfoDatabase.getInstance(requireContext())
        userInfoDao = userInfoDatabase.userInfoDao()

        fetchUserData()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun fetchUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val userList = userInfoDao.getAllUserInfo()
            withContext(Dispatchers.Main) {
                userList.let { list->
                    if (list.isNotEmpty()){
                        userAdapter.submitList(list)
                        binding.noInfText.visibility=View.GONE
                        binding.recyclerView.visibility=View.VISIBLE

                    }else{
                        binding.recyclerView.visibility=View.GONE
                        binding.noInfText.visibility=View.VISIBLE
                    }


                }
            }
        }
    }


}
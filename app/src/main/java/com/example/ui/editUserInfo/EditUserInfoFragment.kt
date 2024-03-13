package com.example.ui.editUserInfo

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cradusers.R
import com.example.cradusers.databinding.FragmentAddUserInfoBinding
import com.example.cradusers.databinding.FragmentEditUserInfoBinding
import com.example.data.local.UserInfoDao
import com.example.data.local.UserInfoDatabase
import com.example.data.pojo.UserInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditUserInfoFragment : Fragment() {

    private var _binding: FragmentEditUserInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoDao: UserInfoDao
    private var userId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userInfoDatabase = UserInfoDatabase.getInstance(requireContext())
        userInfoDao = userInfoDatabase.userInfoDao()
        userId = arguments?.let {
            EditUserInfoFragmentArgs.fromBundle(it).id
        }
        if (!userId.isNullOrEmpty()) {
            fetchUserInfo(userId!!)
        }
        else { Toast.makeText(requireContext(),"some this is wrong",Toast.LENGTH_LONG).show() }

        binding.saveUpdate.setOnClickListener {
              updateUserInfo()
        }
        binding.deletData.setOnClickListener {

        showDeleteConfirmationDialog(userId)
        }
    }

    private fun fetchUserInfo(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userInfo = userInfoDao.getUserInfoById(userId)
            withContext(Dispatchers.Main) {
                userInfo?.let {
                    binding.userName.setTextEditable(it.name)
                    binding.userAge.setTextEditable(it.age)
                    binding.userEmail.setTextEditable(it.email)
                    binding.userPhone.setTextEditable(it.phone)
                }
            }
        }
    }
    private fun updateUserInfo() {
        val name = binding.userName.text.toString().trim()
        val age = binding.userAge.text.toString().trim()
        val email = binding.userEmail.text.toString().trim()
        val phone = binding.userPhone.text.toString().trim()

        if (name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
            val userInfo = UserInfoEntity(userId!!.toLong(), name, age, email, phone)
            CoroutineScope(Dispatchers.IO).launch {
                userInfoDao.updateUserInfo(userInfo)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "User information updated", Toast.LENGTH_SHORT).show()
                    binding.userName.setText("")
                    binding.userAge.setText("")
                    binding.userEmail.setText("")
                    binding.userPhone.setText("")
                    findNavController()
                        .navigate(EditUserInfoFragmentDirections.actionEditUserInfoFragmentToShowUserDataFragment())
                         findNavController().popBackStack()

                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog(userId: String?) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Delete User")
            .setMessage("Are you sure you want to delete this user?")
            .setPositiveButton("Yes") { dialog, _ ->
                userId?.let {
                    deleteUserInfo(it)
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->

                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteUserInfo(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userInfoDao.deleteUserInfoById(userId)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "User information deleted", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun EditText.setTextEditable(text: String?) {
        this.text = text?.let { Editable.Factory.getInstance().newEditable(it) }
    }
}
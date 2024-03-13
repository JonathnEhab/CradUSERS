package com.example.ui.addUserFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cradusers.databinding.FragmentAddUserInfoBinding
import com.example.data.local.UserInfoDatabase
import com.example.data.local.UserInfoDao
import com.example.data.pojo.UserInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUserInfoFragment : Fragment() {

    private var _binding: FragmentAddUserInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoDao: UserInfoDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userInfoDatabase = UserInfoDatabase.getInstance(requireContext())
        userInfoDao = userInfoDatabase.userInfoDao()
        binding.showData.setOnClickListener {
            findNavController().navigate(AddUserInfoFragmentDirections.actionAddUserInfoFragmentToShowUserDataFragment())
        }
        binding.saveData.setOnClickListener {
            val name = binding.userName.text.toString().trim()
            val age = binding.userAge.text.toString().trim()
            val email = binding.userEmail.text.toString().trim()
            val phone = binding.userPhone.text.toString().trim()

            if (name.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                val userInfo = UserInfoEntity(name = name, age = age, email = email, phone = phone)
                saveUserInfoToDatabase(userInfo)
            } else {
               Toast.makeText(requireContext(),"Not all items are full ",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveUserInfoToDatabase(userInfo: UserInfoEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            userInfoDao.insertUserInfo(userInfo)
            withContext(Dispatchers.Main) {
                // Switch to the main thread to update UI
                Toast.makeText(requireContext(), "User information saved successfully", Toast.LENGTH_SHORT).show()
                binding.userName.setText("")
                binding.userAge.setText("")
                binding.userEmail.setText("")
                binding.userPhone.setText("")

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

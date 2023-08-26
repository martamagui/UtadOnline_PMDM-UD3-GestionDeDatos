package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.utadonline_pmdm_ud3_gestiondedatos.MyApplication
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.FragmentRoomEmployeeDetailBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.ClockIn
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.EmployeeClockInRelation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class RoomEmployeeDetailFragment : Fragment() {

    private lateinit var _binding: FragmentRoomEmployeeDetailBinding
    private val binding: FragmentRoomEmployeeDetailBinding get() = _binding

    private val navigationArgs: RoomEmployeeDetailFragmentArgs by navArgs()
    private val adapter = ClockInRelationAdapter()
    private var employeeId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomEmployeeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recogemos el id que nos envía el fragmento previo
        employeeId = navigationArgs.employeeId

        //Configuramos la RecyclerView
        binding.rvClockIn.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvClockIn.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            if (employeeId != null) {
                getEmployeeData(employeeId!!)
            }
        }

        binding.btnEdit.setOnClickListener {
            if (employeeId != null) {
                navigateToEdit(employeeId!!)
            }
        }
        binding.btnClockIn.setOnClickListener { addClockIn() }
    }

    private fun addClockIn() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val currentDate = Date()
        val formattedDate = dateFormat.format(currentDate)

        val application = requireContext().applicationContext as MyApplication
        lifecycleScope.launch(Dispatchers.IO) {
            if (employeeId != null) {
                val clockIn = ClockIn(0, formattedDate, employeeId!!)
                application.room.clockInDao().addClockIn(clockIn)
                getEmployeeData(employeeId!!)
            }
        }
    }

    //Marrco la función como suspend para que obligar a que se ejecute en una corrutina
    private suspend fun getEmployeeData(employeeId: Int) {
        val application = requireContext().applicationContext as MyApplication
        // Hago la petición de el empleado y sus hora de entrada
        val employeeClockInRelation =
            application.room.employeeDao().getEmployeeAndClocInsById(employeeId)
        //Mostramos los datos del empleado en la interfaz
        withContext(Dispatchers.Main) {
            setEmployeeData(employeeClockInRelation)
        }
    }

    private fun setEmployeeData(employeeClockInRelation: EmployeeClockInRelation) {
        binding.ivEmployeeDetail.setImageBitmap(employeeClockInRelation.employee.image)
        binding.tvEmployeeDetailJob.text = employeeClockInRelation.employee.job
        binding.tvEmployeeDetailName.text = employeeClockInRelation.employee.name
        //Añadimos a la recyclerview la lista de horas de entrada
        adapter.submitList(employeeClockInRelation.clockInList)
    }

    private fun navigateToEdit(employeeId: Int) {
        val intent = Intent(requireContext(), RoomEditActivity::class.java)
        intent.putExtra("employeeId", employeeId)
        startActivity(intent)
    }

}
package com.utad.utadonline_pmdm_ud3_gestiondedatos.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.utad.utadonline_pmdm_ud3_gestiondedatos.MyApplication
import com.utad.utadonline_pmdm_ud3_gestiondedatos.databinding.FragmentRoomListBinding
import com.utad.utadonline_pmdm_ud3_gestiondedatos.room.entities.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomListFragment : Fragment() {

    private lateinit var _binding: FragmentRoomListBinding
    private val binding: FragmentRoomListBinding get() = _binding

    private val adapter = EmployeeListAdapter({ id -> goToDetail(id) }) { employee, position ->
        showRemoveEmployeeDialog(employee, position)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEmployee.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvEmployee.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        //Lo llamamos en onResume para que se actualice simpre que volvamos a la activity
        getEmployeeList()
    }

    private fun getEmployeeList() {
        val application = requireContext().applicationContext as MyApplication

        lifecycleScope.launch(Dispatchers.IO) {
            val employeeList = application.room.employeeDao().getAllEmployees()
            withContext(Dispatchers.Main) {
                adapter.submitList(employeeList)
            }
        }
    }

    private fun showRemoveEmployeeDialog(employee: Employee, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Vas a eliminar a ${employee.name}")
            .setMessage("¿Estás seguro?")
            .setPositiveButton("Sí") { dialog, which ->
                //Si el usuario acepta, borramos al empleado
                removeEmployee(employee, position)
            }
            .setNegativeButton("Cancelar") { dialog, which -> }
            .show()
    }

    private fun removeEmployee(employee: Employee, position: Int) {
        val application = requireContext().applicationContext as MyApplication
        lifecycleScope.launch(Dispatchers.IO) {
            application.room.employeeDao().deleteEmployee(employee)

            withContext(Dispatchers.Main) {
                //Eliminamos al empleado de la lista de la RyclerView
                // copiando la lista y eliminando el empleado seleccionado
                val newList = mutableListOf<Employee>()
                newList.addAll(adapter.currentList)
                newList.removeAt(position)
                //refrescamos la lista
                adapter.submitList(newList)
            }
        }
    }

    private fun goToDetail(employeeId: Int) {
        val action =
            RoomListFragmentDirections.actionRoomListFragmentToRoomEmployeeDetailFragment(employeeId)
        findNavController().navigate(action)
    }

}
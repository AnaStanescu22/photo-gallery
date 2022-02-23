package com.example.android.photogallery

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.android.photogallery.databinding.FragmentGalleryBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private val galleryAdapter by lazy {
        GalleryRecyclerAdapter()
    }

    private val viewModel: GalleryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imagesRecyclerView.adapter = galleryAdapter

        viewModel.state.onEach(galleryAdapter::submitList).launchIn(lifecycleScope)
    }

    private fun checkPermission() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestReadStoragePermission() {
        if (checkPermission()) {
            Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show()
            viewModel.fetchLocalImages()
        } else {
            requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE ->
                if (grantResults.isNotEmpty()) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        Toast.makeText(
                            requireContext(),
                            "Permissions Granted..",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.fetchLocalImages()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Permissions denied, Permissions are required to use the app..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        requestReadStoragePermission()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 12345
    }
}
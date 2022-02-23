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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.photogallery.databinding.FragmentGalleryBinding
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryFragment : Fragment() {

    private val galleryAdapter = GalleryRecyclerAdapter()
    private val PERMISSION_REQUEST_CODE = 200
    private val viewModel: GalleryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentGalleryBinding =
            FragmentGalleryBinding.inflate(inflater, container, false)

        requestPermissions()

        val manager = GridLayoutManager(activity, 4)
        binding.imagesRecyclerView.layoutManager = manager
        binding.imagesRecyclerView.adapter = galleryAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.state.onEach { gallery ->
            galleryAdapter.setGallery(gallery)

        }.launchIn(lifecycleScope)
    }

    private fun checkPermission(): Boolean {
        val result = context?.let { ContextCompat.checkSelfPermission(it, READ_EXTERNAL_STORAGE) }
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        if (checkPermission()) {
            Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show()
            viewModel.launchCoroutine()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
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
                        Toast.makeText(context, "Permissions Granted..", Toast.LENGTH_SHORT).show()
                        viewModel.launchCoroutine()
                    } else {
                        Toast.makeText(
                            context,
                            "Permissions denied, Permissions are required to use the app..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
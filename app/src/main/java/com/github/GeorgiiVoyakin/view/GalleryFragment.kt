package com.github.GeorgiiVoyakin.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.GeorgiiVoyakin.R
import com.github.GeorgiiVoyakin.adapter.GalleryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter

    companion object {
        private const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        galleryAdapter = GalleryAdapter()
        recyclerView.adapter = galleryAdapter

        val layoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_REQUEST
            )
        } else {
//            loadImagesFromGallery()
        }
    }

    private fun loadImagesFromGallery() {
        val images = mutableListOf<Uri>()

        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        CoroutineScope(Dispatchers.IO).launch {
            val cursor = requireContext().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            cursor?.use {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
                    val imageId = it.getLong(columnIndex)
                    val contentUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageId.toString()
                    )
                    images.add(contentUri)
                }
            }

            withContext(Dispatchers.Main) {
                setupRecyclerView(images)
            }
        }
    }


    private fun setupRecyclerView(images: List<Uri>) {
        galleryAdapter.setItems(images)

        galleryAdapter.setOnItemClickListener { position ->
            val image = galleryAdapter.getItem(position)
            // Открываем изображение на весь экран или выполняем другую логику
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                loadImagesFromGallery()
            } else {
                // Разрешение не предоставлено, обрабатываем эту ситуацию
            }
        }
    }
}
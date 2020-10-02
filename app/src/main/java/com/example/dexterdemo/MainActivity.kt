package com.example.dexterdemo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        button_single.setOnClickListener(this)
        button_multiple.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view){
            button_single -> {
                requestSinglePermission()

            }
            button_multiple -> {
                requestMultiplePermission()
            }
        }
    }

    private fun requestMultiplePermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(object:MultiplePermissionsListener{
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if(report!!.areAllPermissionsGranted()){
                            Toast.makeText(applicationContext, "All permission granted", Toast.LENGTH_SHORT).show()
                        }
                        if(report!!.isAnyPermissionPermanentlyDenied){
                            Toast.makeText(applicationContext, "permission denied permenantly", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                })
                .onSameThread()
                .check()

    }

    private fun requestSinglePermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object:PermissionListener{
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        Toast.makeText(applicationContext, "Granted ", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(applicationContext, "Denied ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                }).check()
    }
}
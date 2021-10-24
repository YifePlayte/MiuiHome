package com.yuk.miuihome.module

import android.widget.Toast
import com.yuk.miuihome.Config
import com.yuk.miuihome.HomeContext
import com.yuk.miuihome.XposedInit
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class BuildWithEverything {

    fun init() {
        fun readStream(input: InputStream) {
            val reader = BufferedReader(InputStreamReader(input))
            var read = ""
            while (true) {
                val temp: String = reader.readLine() ?: break
                read += temp
            }
            Toast.makeText(HomeContext.context, read, Toast.LENGTH_LONG).show()
        }
        try {
            readStream(
                Runtime.getRuntime()
                    .exec("cmd package compile -m everything ${Config.hookPackage}").inputStream
            )
        } catch (e: IOException) {
            Toast.makeText(HomeContext.context, e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
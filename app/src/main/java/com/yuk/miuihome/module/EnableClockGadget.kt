package com.yuk.miuihome.module

import com.yuk.miuihome.utils.OwnSP
import com.yuk.miuihome.utils.ktx.setReturnConstant

class EnableClockGadget {

    fun init() {
        if (OwnSP.ownSP.getBoolean("clockGadget", false)) {
            "com.miui.home.launcher.Workspace".setReturnConstant(
                "isScreenHasClockGadget",
                Long::class.java,
                result = false
            )
        }
    }
}
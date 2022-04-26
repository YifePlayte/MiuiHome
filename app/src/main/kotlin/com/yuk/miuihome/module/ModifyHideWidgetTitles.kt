package com.yuk.miuihome.module

import android.content.Context
import android.os.Bundle
import android.view.View
import com.github.kyuubiran.ezxhelper.init.InitFields
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.invokeMethodAuto
import com.github.kyuubiran.ezxhelper.utils.loadClass
import com.yuk.miuihome.utils.OwnSP
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import java.util.function.Predicate

class ModifyHideWidgetTitles {

    fun init() {
        if (!OwnSP.ownSP.getBoolean("hideWidgetTitles", false)) return
        val widgetInfo = loadClass("com.miui.home.launcher.LauncherAppWidgetInfo")
        val widgetProviderInfo = loadClass("android.appwidget.AppWidgetProviderInfo")
        val maMlWidgetInfo = loadClass("com.miui.home.launcher.maml.MaMlWidgetInfo")
        findMethod("com.miui.home.launcher.LauncherAppWidgetHost") {
            name == "createLauncherWidgetView" && parameterTypes[0] == Context::class.java && parameterTypes[1] == Int::class.javaPrimitiveType && parameterTypes[2] == widgetInfo && parameterTypes[3] == widgetProviderInfo
        }.hookAfter {
            val view = it.result as Any
            view.invokeMethodAuto("getTitleView")?.invokeMethodAuto("setVisibility", View.GONE)
        }
        XposedHelpers.findAndHookMethod( // TODO
            "com.miui.home.launcher.Launcher",
            InitFields.ezXClassLoader,
            "addMaMl",
            maMlWidgetInfo,
            Bundle::class.java,
            Predicate::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    val view = param.result as Any
                    view.invokeMethodAuto("getTitleView")?.invokeMethodAuto("setVisibility", View.GONE)
                }
            })
    }
}
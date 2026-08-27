package com.albukhaiti.template

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView

/**
 * شاشة نموذجية بالحد الأدنى — الهدف إثبات صلاحية خط الأتمتة
 * (بناء + اختبار + توقيع + نشر) لا تقديم واجهة فعلية.
 * استبدلها بتطبيقك الحقيقي عند استخدام القالب.
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val info = BuildInfo(
            appId = packageName,
            versionName = packageManager.getPackageInfo(packageName, 0).versionName ?: "?"
        )
        val view = TextView(this).apply {
            text = info.displayLabel()
            textSize = 20f
            gravity = Gravity.CENTER
        }
        setContentView(view)
    }
}

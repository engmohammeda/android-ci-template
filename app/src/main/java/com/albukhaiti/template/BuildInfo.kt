package com.albukhaiti.template

/**
 * نموذج منطق قابل للاختبار — بوابة الاختبارات في CI تمنع النشر
 * إن فشلت اختبارات هذا الملف أو غيره.
 */
data class BuildInfo(
    val appId: String,
    val versionName: String
) {
    fun displayLabel(): String = "$appId\nv$versionName"

    companion object {
        /** يتحقق أن versionName يتبع صيغة SemVer المبسطة: M.m.p */
        fun isValidSemver(version: String): Boolean =
            Regex("""^\d+\.\d+\.\d+$""").matches(version)

        /** يقارن نسختي SemVer: موجب إذا a أحدث من b، صفر إذا تساوتا، سالب إذا أقدم */
        fun compareSemver(a: String, b: String): Int {
            require(isValidSemver(a) && isValidSemver(b)) { "صيغة نسخة غير صالحة" }
            val pa = a.split(".").map(String::toInt)
            val pb = b.split(".").map(String::toInt)
            for (i in 0..2) {
                if (pa[i] != pb[i]) return pa[i].compareTo(pb[i])
            }
            return 0
        }
    }
}

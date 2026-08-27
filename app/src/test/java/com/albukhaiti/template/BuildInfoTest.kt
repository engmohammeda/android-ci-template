package com.albukhaiti.template

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BuildInfoTest {

    @Test
    fun `versionName الصالح يمر`() {
        assertTrue(BuildInfo.isValidSemver("1.2.3"))
        assertTrue(BuildInfo.isValidSemver("0.1.0"))
        assertTrue(BuildInfo.isValidSemver("10.20.30"))
    }

    @Test
    fun `versionName غير الصالح يُرفض`() {
        assertFalse(BuildInfo.isValidSemver("1.2"))
        assertFalse(BuildInfo.isValidSemver("v1.2.3"))
        assertFalse(BuildInfo.isValidSemver("1.2.3-beta"))
        assertFalse(BuildInfo.isValidSemver(""))
    }

    @Test
    fun `مقارنة النسخ تعمل`() {
        assertTrue(BuildInfo.compareSemver("1.2.3", "1.2.2") > 0)
        assertTrue(BuildInfo.compareSemver("1.2.3", "1.3.0") < 0)
        assertEquals(0, BuildInfo.compareSemver("2.0.0", "2.0.0"))
        assertTrue(BuildInfo.compareSemver("2.0.0", "1.99.99") > 0)
    }

    @Test
    fun `التسمية الظاهرة تتضمن المعرف والنسخة`() {
        val info = BuildInfo("com.albukhaiti.template", "0.1.0")
        assertEquals("com.albukhaiti.template\nv0.1.0", info.displayLabel())
    }
}

# ⚙️ الإعداد الكامل من الصفر

## 1) المتطلبات

- حساب GitHub بصلاحيات إنشاء مستودعات وإدارة Actions/Pages/Secrets.
- (للتطوير المحلي فقط) JDK 17 + Android Studio أو Android SDK.

## 2) إنشاء مستودعك من القالب

1. من صفحة المستودع اضغط **Use this template → Create a new repository**.
2. سمِّ المستودع باسم تطبيقك — **الاسم يُستخدم تلقائياً** كاسم للتطبيق في الإصدارات والملفات (`APP_NAME`).

## 3) تفعيل GitHub Pages

- *Settings → Pages → Build and deployment → Source*: اختر **GitHub Actions**.
- بعد أول دفعة إلى `main` ستُنشر صفحة الخصوصية على:
  `https://<user>.github.io/<repo>/`

## 4) استبدال التطبيق النموذجي

1. احذف محتوى `app/src/main/java/...` وضع كود تطبيقك.
2. حدّث `namespace` و`applicationId` في `app/build.gradle.kts`.
3. **أبقِ** منطق التوقيع كما هو (كتلة `signingConfigs`) — الخط يعتمد عليه.
4. **أبقِ** `versionName = "x.y.z"` بصيغته — خطوة Stamp تستبدله بـ sed.
5. أبقِ اختبارات وحدة فعلية في `app/src/test/` — هي بوابة النشر.

## 5) أول إصدار

```bash
git add -A
git commit -m "feat: أول نسخة من تطبيقي"
git push origin main
```

راقب *Actions* — عند النجاح ستجد في *Releases* وسماً مثل `v0.2.0-build.1` يضم:
- `<repo>-v0.2.0-debug.apk`
- `<repo>-v0.2.0-release.apk`
- `<repo>-v0.2.0-release.aab`
- ملاحظات مصنّفة + بصمات SHA256

## 6) التشغيل اليدوي

*Actions → Android · CI · Release ... → Run workflow* وحدد:
- `run_tests` — أطفئه فقط في حالات طارئة (غير منصوح).
- `send_telegram` — يتطلب سرّي تلجرام.
- `deploy_pages` — نشر صفحة الخصوصية.
- `publish_play` — يتطلب `PLAY_SERVICE_ACCOUNT_JSON`.

## 7) استكشاف الأخطاء

| العرض | السبب الأرجح | الحل |
|---|---|---|
| فشل `Unit tests` | اختبار مكسور | أصلح الكود؛ هذه البوابة مقصودة |
| فشل `google-services.json` | تبنّيت Firebase دون سرّ | أضف `GOOGLE_SERVICES_JSON` |
| Release APK غير موقّع صح | أسرار keystore ناقصة | راجع docs/SECRETS.md §1 |
| Pages لا تُنشر | المصدر ليس GitHub Actions | فعّل §3 أعلاه |
| تحذيرات store-lint | أصول متجر ناقصة | راجع docs/STORE_CHECKLIST.md |

# 🧭 خارطة التحسينات والتطويرات المقترحة

ما نُفّذ الآن هو الأساس الإنتاجي. هذه خارطة التطوير مرتبة بالأولوية والجهد.

## ✅ تحسينات نُفّذت فوق الفكرة الأولية

1. **إصلاح علة `null == false` في بوابات التشغيل** — الشروط الأصلية مثل `inputs.run_tests != false` كانت تتخطى الاختبارات وPages وتلجرام في كل دفعة عادية، لأن GitHub يقيّم `null != false` إلى false (مساواة متساهلة). اكتُشفت في أول تشغيل فعلي وأُصلحت بشرط `github.event_name != 'workflow_dispatch' || inputs.x`.
2. **إصلاح أولوية الشروط في Pages** — الشرط الأصلي `A && B || C` كان سينشر Pages من `master` حتى مع تعطيل `deploy_pages`؛ أُضيفت أقواس.
2. **بيئة `github-pages` على وظيفة الإصدار** — مطلوبة لعمل `deploy-pages@v4` بشكل موثوق مع OIDC.
3. **`concurrency`** — يلغي بناءات PR المتكررة ويمنع تسابق إصدارين على نفس الفرع.
4. **`chmod +x gradlew` قبل خطوة البناء** في مسار الإصدار حتى عند تعطيل الاختبارات يدوياً.
5. **توقيع مرن في build.gradle.kts** — يقرأ متغيرات البيئة الإنتاجية أو يسقط تلقائياً إلى debug.keystore دون تعديل يدوي.
6. **`versionCode = GITHUB_RUN_NUMBER`** — ترقيم بنائي فريد متزايد جاهز لـ Play.
7. **R8 مفعّل في release** (minify + shrinkResources) ليكون mapping.txt حقيقياً لا فارغاً.
8. **اختبارات وحدة فعلية** على منطق SemVer — البوابة تختبر شيئاً حقيقياً.

## 🎯 أولوية عالية (الخطوة التالية المنطقية)

> بنود إضافية نُفّذت في جولة الاستلهام (راجع INSPIRATION.md): إنذار تلجرام عند الفشل،
> فرق حجم APK عن الإصدار السابق، صفحة هبوط حية على Pages، Firebase App Distribution اختياري، شارات README.

- **اختبارات لقطات Roborazzi على JVM** (من nowinandroid): اختبار الواجهات بصرياً دون محاكي — أسرع بكثير من Instrumented tests.
- **Dependency Guard** (من nowinandroid): خط أساس للاعتمادات يمنع تسلل تبعية جديدة خفية عبر PR.
- **تفعيل الرفع الفعلي إلى Play**: استبدال خطوة الـ echo بـ `r0adkll/upload-google-play@v1` بعد رفع أول AAB يدوياً وضبط حساب الخدمة.
- **اختبارات Instrumented على محاكي**: وظيفة منفصلة بـ `reactivecircus/android-emulator-runner` تشغّل `connectedDebugAndroidTest` في الـ PR (مع تفعيل KVM).
- **حماية الفرع `main`**: قاعدة تشترط نجاح وظيفة CI قبل الدمج + مراجعة إلزامية.
- **Environments بمراجعين** (من maddevsio): بيئة `production` بـ Required reviewers قبل خطوة Play — نشر بموافقة بشرية.

## 🔶 أولوية متوسطة

- **Dependabot / Renovate** (ملف dependabot.yml مضمّن بالفعل للـ Actions وGradle) — راجِع الـ PRs الأسبوعية.
- **كاش أذكى**: `gradle/actions/setup-gradle` مع `cache-read-only` للـ PRs لتسريع البناء وخفض الدقائق.
- **Detekt + ktlint**: فحص جودة كود Kotlin كبوابة PR (تحذيري أولاً ثم إلزامي).
- **توليد لقطات تلقائي**: Screengrab (fastlane) أو Paparazzi لتوليد لقطات متجر من الاختبارات.
- **تقرير تغطية**: Kover + رفع النسبة إلى الـ Job Summary وشارة في README.
- **إشعار فشل إلى تلجرام**: حالياً يُرسل النجاح فقط؛ أضف خطوة `if: failure()` تنبّهك فوراً.

## 🔷 أولوية لاحقة / أفكار توسعة

- **قنوات إصدار متعددة**: وسوم `v*-beta` → مسار beta في Play، ووسوم نظيفة → production.
- **توقيع AAB بـ Play App Signing** ونقل إدارة المفاتيح إلى Google.
- **مصفوفة بناء**: نكهات (flavors) متعددة تُبنى بالتوازي (مجاني/مدفوع مثلاً).
- **SBOM + فحص ثغرات**: `gradle cyclonedx` + Dependency Review في الـ PR.
- **F-Droid / GitHub Releases كمتجر بديل** مع تحديث ذاتي داخل التطبيق (مفيد حيث Play مقيد).
- **بوت تلجرام تفاعلي**: أوامر مثل `/release` تستدعي `workflow_dispatch` عبر API — إطلاق إصدار من هاتفك.
- **صفحة Landing كاملة على Pages**: بدل صفحة الخصوصية البسيطة — صفحة تسويقية بأحدث إصدار وروابط تحميل مباشرة من Releases API.
- **توطين أصول المتجر**: بنية `store/ar/`, `store/en/` مع رفع متعدد اللغات إلى Play عبر fastlane supply.

## 📌 ملاحظات صيانة دورية

- حدّث إصدارات Actions (`checkout@v4`...) كل بضعة أشهر — dependabot يتولى التنبيه.
- راقب متطلب `targetSdk` السنوي من Play (أغسطس عادة).
- جدّد Personal Access Tokens قبل انتهائها، وأبقها بأضيق صلاحيات.

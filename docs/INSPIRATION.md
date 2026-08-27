# 🔭 الاستلهام والمقارنة المعيارية — أين نقف من أفضل المشاريع المفتوحة؟

> جولة بحث فعلية (أغسطس 2026) في المشاريع والأدوات الرائدة المشابهة لفكرتنا،
> مع تحليل فجوات صريح: ما لديهم وينقصنا، وما لدينا ويميزنا.

---

## 1) المشاريع المرجعية التي دُرست

### 🥇 [android/nowinandroid](https://github.com/android/nowinandroid) — مرجع Google الرسمي
أقوى مثال حي لممارسات CI أندرويد. من فحص workflows الفعلية لديهم:
- **Spotless** لفرض تنسيق الكود آلياً
- **Dependency Guard**: خط أساس (baseline) للاعتمادات يمنع تسلل تبعية جديدة دون مراجعة — مع **توليد تلقائي للخط الجديد داخل الـ PR** إذا فشل التحقق!
- **اختبارات لقطات Roborazzi** (screenshot tests بلا محاكي — على JVM) مع دفع اللقطات الجديدة تلقائياً للـ PR
- **Check badging**: التحقق من أذونات/خصائص الـ APK النهائي ضد خط أساس
- تقارير Lint بصيغة **SARIF** تُرفع لتبويب Security في GitHub
- **Baseline Profiles ليلية** لتحسين أداء الإقلاع
- مصفوفة بناء لكل النكهات + KVM للمحاكي

### 🛠 [fastlane](https://fastlane.tools) — المعيار الصناعي للنشر
- `supply`: رفع **بيانات المتجر متعددة اللغات** (أوصاف، لقطات، ملاحظات) إلى Play وليس فقط الـ AAB
- `screengrab`: **توليد لقطات المتجر آلياً** من اختبارات UI
- نظام lanes قابل للتركيب ومئات الإضافات (منها إضافات لمتاجر بديلة مثل RuStore)

### 🚂 [Tramline](https://github.com/tramlinehq/tramline) — إدارة إصدارات مفتوحة المصدر (Apache 2.0)
مفاهيم متقدمة لا يملكها أحد غيرهم تقريباً:
- **قطارات إصدار (Release Trains)**: إيقاع نشر منتظم بدل النشر العشوائي
- **Staged Rollout ذكي**: نشر تدريجي 1% → 5% → 10% → 50% → 100% مع إيقاف تلقائي عند تدهور مقاييس المراقبة
- **مسار Hotfix مؤتمت**: فرع إصلاح → نسخة جديدة → إغلاق الإصدار الحي → طرح جديد
- **Build Queue**: تجميع الـ commits المتسارعة قبل إطلاق بناء (توفير دقائق CI)
- **مقاييس DevOps للموبايل**: تكرار الإصدارات، مدتها، عدد الـ hotfixes
- لا يُصدر نفس الـ commit مرتين

### 📦 قوالب مجتمعية
- [maddevsio/android-ci-cd](https://github.com/maddevsio/android-ci-cd): **موافقة بشرية يدوية** قبل النشر لـ Play (trstringer/manual-approval) + إشعارات Slack **للنجاح والفشل معاً** + توزيع نسخ staging عبر **Firebase App Distribution**
- [PatilShreyas/AndroidFastlaneCICD](https://github.com/PatilShreyas/AndroidFastlaneCICD): نشر Play كامل عبر fastlane من Actions
- [asadmansr/template-android](https://github.com/asadmansr/template-android): فكرة "قالب جاهز للنسخ" — نفس فلسفتنا لكن أفقر تجهيزاً

### 📏 أدوات قياس الحجم
- [Diffuse (Jake Wharton)](https://github.com/JakeWharton/diffuse) + [diff-apk](https://github.com/doublesymmetry/diff-apk): **تعليق تلقائي على الـ PR** يشرح أثر التغيير على حجم الـ APK (dex, assets, native libs)
- [microsoft/android-app-size-diff](https://github.com/marketplace/actions/android-app-size-difference): مقارنة أحجام بين نسختين

### 🚀 قنوات توزيع بديلة
- **Firebase App Distribution** ([wzieba/Firebase-Distribution-Github-Action](https://github.com/wzieba/Firebase-Distribution-Github-Action)): توزيع فوري لمجموعات مختبرين بلا متجر — مثالي حيث Play مقيد
- **Obtainium / F-Droid**: التحديث مباشرة من GitHub Releases — مناسب جداً لسياقنا في اليمن

---

## 2) تحليل الفجوات — ما ينقصنا مرتباً بالأثر

| # | الميزة | من استلهمناها | الأثر | الجهد | الحالة |
|---|---|---|---|---|---|
| 1 | إشعار تلجرام عند **الفشل** لا النجاح فقط | maddevsio | 🔥 عالي | ساعة | ✅ **نُفّذ** |
| 2 | **فرق حجم APK** مقابل الإصدار السابق في الملاحظات | Diffuse/Microsoft | 🔥 عالي | ساعة | ✅ **نُفّذ** |
| 3 | **صفحة هبوط** حقيقية على Pages بروابط تحميل حية من Releases API | Obtainium-style | 🔥 عالي | ساعتان | ✅ **نُفّذ** |
| 4 | **Firebase App Distribution** لمجموعة مختبرين | maddevsio/fastlane | 🔥 عالي | ساعة | ✅ **نُفّذ** (اختياري بسرّين) |
| 5 | شارة حالة البناء في README | عرف مجتمعي | متوسط | دقائق | ✅ **نُفّذ** |
| 6 | اختبارات لقطات **Roborazzi** على JVM (بلا محاكي!) | nowinandroid | 🔥 عالي | يوم | 📋 مقترح تالٍ |
| 7 | **Dependency Guard** ضد تسلل التبعيات | nowinandroid | عالي | نصف يوم | 📋 مقترح |
| 8 | تعليق PR تلقائي بفرق الحجم (diffuse كامل) | diff-apk | عالي | نصف يوم | 📋 مقترح |
| 9 | موافقة بشرية (Environment + reviewers) قبل Play production | maddevsio | عالي | ساعة إعداد | 📋 مقترح (يحتاج إعداد من المالك) |
| 10 | رفع بيانات المتجر متعددة اللغات (fastlane supply) | fastlane | متوسط | يوم | 📋 مقترح |
| 11 | Staged rollout تدريجي مع مقاييس | Tramline | متوسط | أيام | 📋 طموح |
| 12 | لقطات متجر آلية (screengrab) | fastlane | متوسط | يوم | 📋 مقترح |
| 13 | Spotless/ktlint تنسيق مفروض | nowinandroid | متوسط | ساعات | 📋 مقترح |
| 14 | تقارير SARIF لتبويب Security | nowinandroid | منخفض | ساعة | 📋 مقترح |
| 15 | مسار Hotfix مؤتمت | Tramline | متوسط | يوم | 📋 طموح |
| 16 | Baseline Profiles ليلية | nowinandroid | منخفض للقالب | يوم | 📋 طموح |

---

## 3) ما يميزنا نحن (لم نجده مجتمعاً في أي مشروع واحد)

1. **التسليم عبر تلجرام** — لا يوجد قالب مشهور يرسل APK مباشرة للقناة؛ ميزة قاتلة لسياقنا حيث Play مقيد والاتصال متقطع
2. **ترقيم دلالي + ملاحظات مصنّفة بالعربية** من رسائل commits — دون خدمة خارجية مثل semantic-release
3. **صفر إعداد إلزامي**: كل الأسرار اختيارية والخط يعمل من أول push (معظم القوالب تنكسر بدون keystore)
4. **store-lint**: فحص جاهزية أصول المتجر — لم نجد مثيلاً له إلا جزئياً في validate-fastlane-supply-metadata
5. **بناء bitrot أسبوعي** — نادر جداً في القوالب
6. **توثيق عربي معمّق** — شبه معدوم في هذا المجال

## 4) القرار المعماري المستخلص

الفلسفة الصحيحة (مؤكدة من البحث): **لا نعيد اختراع العجلة — نبني طبقة توحيد فوق الأفضل**:
- الأشياء البسيطة (بناء/توقيع/إصدار/تلجرام) → bash + gh مباشرة (شفاف وقابل للتعديل)
- الأشياء المعقدة (Play متعدد اللغات، لقطات آلية) → fastlane عند الحاجة
- الأشياء المتقدمة (staged rollout بمقاييس) → تكامل مستقبلي مع Tramline

التنفيذ التفصيلي للبنود المتبقية في [IMPROVEMENTS.md](IMPROVEMENTS.md).

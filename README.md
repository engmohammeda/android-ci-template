# 🤖 android-ci-template — مستودع أتمتة إنتاجي كامل لتطبيقات أندرويد

[![Android · CI · Release](https://github.com/engmohammeda/android-ci-template/actions/workflows/android-release.yml/badge.svg)](https://github.com/engmohammeda/android-ci-template/actions/workflows/android-release.yml)
[![Latest Release](https://img.shields.io/github/v/release/engmohammeda/android-ci-template?label=%D8%A3%D8%AD%D8%AF%D8%AB%20%D8%A5%D8%B5%D8%AF%D8%A7%D8%B1)](https://github.com/engmohammeda/android-ci-template/releases/latest)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

قالب جاهز يمنح أي تطبيق أندرويد **خط إنتاج كاملاً** من أول دفعة: بناء، اختبار، توقيع، ترقيم دلالي، إصدارات GitHub، إرسال تلجرام، صفحة خصوصية على Pages، ورفع اختياري إلى Google Play.

## 🗺️ خريطة التشغيل

| الحدث | ما يحدث |
|---|---|
| `push` إلى `main`/`master` أو وسم `v*` | **Release كامل**: اختبارات → بناء Debug/Release/AAB → semver + ملاحظات → GitHub Release → Pages → تلجرام → Play (اختياري) |
| `pull_request` | **CI فقط**: اختبارات + lint + Debug APK |
| `workflow_dispatch` | تشغيل يدوي بمفاتيح تحكم (tests / telegram / pages / play) |
| `schedule` (الاثنين 03:00 UTC) | بناء **bitrot** أسبوعي يكشف تقادم `main` مبكراً |

## 🚀 البدء السريع

1. استخدم هذا المستودع كقالب (**Use this template**) أو انسخ محتوياته.
2. استبدل التطبيق النموذجي في `app/` بتطبيقك (حافظ على `:app` كاسم للموديول).
3. ادفع إلى `main` — سيُبنى الإصدار الأول تلقائياً ويُنشر في Releases.
4. (اختياري) أضف الأسرار في *Settings → Secrets and variables → Actions* — راجع [docs/SECRETS.md](docs/SECRETS.md).

## 📁 بنية المستودع

```
├── .github/workflows/android-release.yml   ← خط الأتمتة الكامل
├── app/                                    ← تطبيق نموذجي (استبدله بتطبيقك)
│   ├── build.gradle.kts                    ← توقيع مرن: إنتاجي أو debug تلقائياً
│   └── src/test/...                        ← اختبارات الوحدة (بوابة إلزامية)
├── store/                                  ← أصول المتجر (توصيف، خصوصية، لقطات)
├── docs/                                   ← توثيق معمّق
└── gradle/ + gradlew                       ← Gradle Wrapper 8.11.1
```

## 🔢 الترقيم الدلالي التلقائي

يُحسب الإصدار من رسائل الـ commits منذ آخر وسم:

- أي commit يبدأ بـ `feat` → رفع **minor** وتصفير patch (مثال: 1.2.3 → 1.3.0)
- غير ذلك (`fix`، `chore`، ...) → رفع **patch** (مثال: 1.2.3 → 1.2.4)

وتُصنَّف الملاحظات تلقائياً: `feat` → ✨ أُضيف، `fix` → 🐛 أُصلح، `refactor|perf|style` → 🛠 تغيّر، `ci|chore|build|docs` → ⚙️ داخلي.

**التزم بصيغة Conventional Commits** (`feat: ...`، `fix: ...`) لتحصل على ترقيم وملاحظات دقيقة.

## 🔐 الأسرار (كلها اختيارية — الخط يعمل بدونها)

| السرّ | الوظيفة |
|---|---|
| `KEYSTORE_BASE64` + `KEYSTORE_PASSWORD` + `KEY_ALIAS` + `KEY_PASSWORD` | توقيع إنتاجي (وإلا توقيع debug تلقائي) |
| `TELEGRAM_BOT_TOKEN` + `TELEGRAM_CHAT_ID` | إرسال الملاحظات وملفات APK/AAB إلى تلجرام |
| `GOOGLE_SERVICES_JSON` | يتفعّل فقط إذا تبنّى تطبيقك Firebase |
| `FIREBASE_APP_ID` + `FIREBASE_SERVICE_ACCOUNT_JSON` | توزيع فوري على مجموعة مختبرين عبر Firebase App Distribution |
| `PLAY_SERVICE_ACCOUNT_JSON` | الرفع إلى Google Play (مسار internal) |

التفاصيل خطوة بخطوة في [docs/SECRETS.md](docs/SECRETS.md).

## 📚 التوثيق

- [docs/SETUP.md](docs/SETUP.md) — الإعداد الكامل من الصفر
- [docs/SECRETS.md](docs/SECRETS.md) — إعداد كل سرّ بالتفصيل
- [docs/RELEASE_FLOW.md](docs/RELEASE_FLOW.md) — تشريح خط الإصدار خطوة بخطوة
- [docs/STORE_CHECKLIST.md](docs/STORE_CHECKLIST.md) — قائمة جاهزية متجر Play
- [docs/INSPIRATION.md](docs/INSPIRATION.md) — مقارنة معيارية مع أفضل المشاريع المفتوحة وتحليل فجوات
- [docs/IMPROVEMENTS.md](docs/IMPROVEMENTS.md) — خارطة التحسينات والتطويرات المقترحة

## 🧪 التطبيق النموذجي

تطبيق Kotlin بالحد الأدنى (بدون تبعيات UI خارجية) هدفه إثبات صلاحية الخط من البداية للنهاية، مع اختبارات وحدة حقيقية على منطق SemVer تعمل كبوابة قبل أي نشر.

## 📄 الترخيص

MIT — استخدمه بحرية في مشاريعك.

---
صُنع بواسطة [Mohammed Al-Bukhaiti](https://github.com/engmohammeda) · إب، اليمن 🇾🇪

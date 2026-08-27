# 🔐 إعداد الأسرار (Secrets) بالتفصيل

كل الأسرار اختيارية — الخط يعمل بدونها بتوقيع debug ودون تلجرام/Play.
تُضاف من: *Settings → Secrets and variables → Actions → New repository secret*.

---

## 1) التوقيع الإنتاجي

### إنشاء keystore (مرة واحدة، واحفظه في مكان آمن!)

```bash
keytool -genkey -v -keystore release.keystore -alias myapp \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -storepass "كلمة_سر_قوية" -keypass "كلمة_سر_قوية" \
  -dname "CN=Your Name,O=YourOrg,C=YE"
```

### تحويله إلى Base64 وإضافته

```bash
base64 -w0 release.keystore   # انسخ الناتج
```

| السرّ | القيمة |
|---|---|
| `KEYSTORE_BASE64` | ناتج الأمر أعلاه |
| `KEYSTORE_PASSWORD` | كلمة سر المخزن |
| `KEY_ALIAS` | `myapp` (الاسم المستعار) |
| `KEY_PASSWORD` | كلمة سر المفتاح |

> ⚠️ **فقدان keystore = فقدان القدرة على تحديث تطبيقك في Play للأبد.** احتفظ بنسختين احتياطيتين خارج الجهاز.

---

## 2) تلجرام (الملاحظات + الملفات تلقائياً)

1. أنشئ بوتاً عبر [@BotFather](https://t.me/BotFather) → `/newbot` → انسخ التوكن.
2. أضف البوت إلى قناتك/مجموعتك (كمشرف للقنوات).
3. اعرف `chat_id`:
   - أرسل رسالة في المجموعة ثم افتح:
     `https://api.telegram.org/bot<TOKEN>/getUpdates`
   - القنوات العامة تقبل الصيغة `@channelusername` مباشرة.

| السرّ | القيمة |
|---|---|
| `TELEGRAM_BOT_TOKEN` | `123456:ABC-...` |
| `TELEGRAM_CHAT_ID` | `-1001234567890` أو `@channelusername` |

> حد تلجرام للبوتات 50MB للملف الواحد — كافٍ لمعظم التطبيقات.

---

## 3) Firebase (فقط إذا تبنّاه تطبيقك)

يتفعّل الفحص تلقائياً عند وجود `com.google.gms.google-services` في `app/build.gradle.kts`.

| السرّ | القيمة |
|---|---|
| `GOOGLE_SERVICES_JSON` | محتوى `google-services.json` كما هو (JSON) **أو** بترميز Base64 — الخط يكتشف الصيغة تلقائياً |

---

## 4) Google Play (الرفع إلى مسار internal)

1. في [Play Console](https://play.google.com/console): *Setup → API access* → أنشئ **Service Account** من Google Cloud.
2. أنشئ مفتاح JSON للحساب ونزّله.
3. في Play Console امنح الحساب صلاحية **Release manager** على تطبيقك.
4. ارفع أول AAB يدوياً مرة واحدة (شرط Play لأي تطبيق جديد).

| السرّ | القيمة |
|---|---|
| `PLAY_SERVICE_ACCOUNT_JSON` | محتوى ملف JSON كاملاً |

ثم شغّل الخط يدوياً مع `publish_play = true`.

---

## قواعد أمان

- لا تودِع أبداً keystore أو JSON مفاتيح في المستودع.
- دوّر التوكنات فور أي اشتباه بتسريب.
- استخدم Environments مع Required reviewers لحماية النشر الإنتاجي (راجع IMPROVEMENTS.md).

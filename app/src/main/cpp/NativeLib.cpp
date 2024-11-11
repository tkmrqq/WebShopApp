#include <jni.h>
#include <string.h>
#include <stdio.h>
#include "cart_library.h"
#include <android/log.h>

extern "C" JNIEXPORT jdouble JNICALL
Java_com_tkmrqq_pmsapp_data_model_CartLibrary_calculateTotalPrice
(JNIEnv *env, jobject obj, jobjectArray cartItems) {
    int itemCount = env->GetArrayLength(cartItems);
    std::vector<CartItem> items(itemCount);

    for (int i = 0; i < itemCount; i++) {
        jstring itemString = (jstring) env->GetObjectArrayElement(cartItems, i);
        const char *itemChars = env->GetStringUTFChars(itemString, 0);

        // Парсим строку itemChars и заполняем структуру items[i]
        std::istringstream stream(itemChars);
        std::string token;
        int tokenIndex = 0;

        while (std::getline(stream, token, ',')) {
            switch (tokenIndex) {
                case 0:
                    items[i].id = std::stoi(token);
                    break;
                case 1:
                    strncpy(items[i].name, token.c_str(), sizeof(items[i].name) - 1);
                    items[i].name[sizeof(items[i].name) - 1] = '\0';
                    break;
                case 2:
                    items[i].quantity = std::stoi(token);
                    break;
                case 3:
                    items[i].price = std::stod(token);
                    break;
            }
            tokenIndex++;
        }

        env->ReleaseStringUTFChars(itemString, itemChars);
    }

    // Вычисляем общую стоимость
    double totalPrice = calculateTotalPrice(items.data(), itemCount);
    return totalPrice;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_tkmrqq_pmsapp_data_model_CartLibrary_addTwoNumbers(JNIEnv* env, jobject /* this */, jint a, jint b) {
    return a + b;
}

extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_tkmrqq_pmsapp_ui_screen_HomeFragment_findProducts(JNIEnv* env, jobject, jobjectArray jProducts, jstring jName) {
    const char* name = env->GetStringUTFChars(jName, nullptr);
    std::string searchName(name);
    std::transform(searchName.begin(), searchName.end(), searchName.begin(), ::tolower);

    std::vector<jobject> matchingProducts;
    jclass productClass = env->FindClass("com/tkmrqq/pmsapp/data/model/Product");
    jfieldID nameField = env->GetFieldID(productClass, "name", "Ljava/lang/String;");

    jsize length = env->GetArrayLength(jProducts);
    for (jsize i = 0; i < length; ++i) {
        jobject product = env->GetObjectArrayElement(jProducts, i);
        jstring productName = (jstring) env->GetObjectField(product, nameField);
        const char* productNameStr = env->GetStringUTFChars(productName, nullptr);

        // Приводим имя продукта к нижнему регистру
        std::string productNameLower(productNameStr);
        std::transform(productNameLower.begin(), productNameLower.end(), productNameLower.begin(), ::tolower);

        // Проверяем, содержит ли имя продукта подстроку поиска
        if (productNameLower.find(searchName) != std::string::npos) {
            matchingProducts.push_back(product);
        }

        env->ReleaseStringUTFChars(productName, productNameStr);
        env->DeleteLocalRef(productName);
    }

    env->ReleaseStringUTFChars(jName, name);

    jobjectArray result = env->NewObjectArray(matchingProducts.size(), productClass, nullptr);
    for (size_t i = 0; i < matchingProducts.size(); ++i) {
        env->SetObjectArrayElement(result, i, matchingProducts[i]);
    }

    return result;
}

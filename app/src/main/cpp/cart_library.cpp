#include <stdio.h>
#include <string.h>
#include "cart_library.h"


// Функция для сохранения корзины в файл
void saveCartToFile(CartItem* items, int itemCount, const char* filename) {
    FILE *file = fopen(filename, "w");
    if (file == NULL) {
        printf("Error opening file for writing\n");
        return;
    }
    for (int i = 0; i < itemCount; i++) {
        fprintf(file, "%d,%s,%d,%.2f\n", items[i].id, items[i].name, items[i].quantity, items[i].price);
    }
    fclose(file);
}

// Функция для загрузки корзины из файла
int loadCartFromFile(CartItem* items, int maxItems, const char* filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error opening file for reading\n");
        return 0;
    }
    int itemCount = 0;
    while (fscanf(file, "%d,%[^,],%d,%lf\n", &items[itemCount].id, items[itemCount].name, &items[itemCount].quantity, &items[itemCount].price) != EOF) {
        itemCount++;
        if (itemCount >= maxItems) break;
    }
    fclose(file);
    return itemCount;
}

// Функция для поиска товара в корзине по ключевому слову
int searchInCart(CartItem* items, int itemCount, const char* keyword, CartItem* resultItems, int maxResults) {
    int foundCount = 0;
    for (int i = 0; i < itemCount && foundCount < maxResults; i++) {
        if (strstr(items[i].name, keyword) != NULL) {
            resultItems[foundCount++] = items[i];
        }
    }
    return foundCount;
}

double calculateTotalPrice(CartItem* items, int itemCount) {
    double totalPrice = 0.0;
    for (int i = 0; i < itemCount; i++) {
        totalPrice += items[i].price * items[i].quantity;
    }
    return totalPrice;
}
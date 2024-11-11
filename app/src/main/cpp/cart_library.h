#ifndef CART_LIBRARY_H
#define CART_LIBRARY_H

#include "vector"
#include "sstream"

typedef struct {
    int id;
    char name[50];
    int quantity;
    double price;
} CartItem;

#define MAX_CART_ITEMS 100

void saveCartToFile(CartItem* items, int itemCount, const char* filename);
int loadCartFromFile(CartItem* items, int maxItems, const char* filename);
int searchInCart(CartItem* items, int itemCount, const char* keyword, CartItem* resultItems, int maxResults);
double calculateTotalPrice(CartItem* items, int itemCount);

#endif // CART_LIBRARY_H

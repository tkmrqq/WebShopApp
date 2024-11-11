package com.tkmrqq.pmsapp.data.model;

import java.lang.reflect.Array;

public class CartLibrary {
    static {
        System.loadLibrary("native-lib");
    }

    public native void saveCartToFile(CartItem[] items, int itemCount, String filename);
    public native int loadCartFromFile(CartItem[] items, int maxItems, String filename);
    public native int searchInCart(CartItem[] items, int itemCount, String keyword, CartItem[] resultItems, int maxResults);
    public native double calculateTotalPrice(String[] cartItems);
    public native int addTwoNumbers(int a, int b);
}


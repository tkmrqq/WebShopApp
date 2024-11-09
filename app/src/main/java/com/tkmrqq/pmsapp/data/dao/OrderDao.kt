package com.tkmrqq.pmsapp.data.dao

import androidx.room.*
import com.tkmrqq.pmsapp.data.model.CartItem
import com.tkmrqq.pmsapp.data.model.Order
import com.tkmrqq.pmsapp.data.model.OrderItemCrossRef

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItemCrossRef(orderItemCrossRef: OrderItemCrossRef)

    @Transaction
    @Query("SELECT * from orders")
    suspend fun getAllOrders(): List<Order>

    @Transaction
    @Query("DELETE FROM orders")
    suspend fun clearOrders()

    @Transaction
    suspend fun placeOrder(order: Order, cartItems: List<CartItem>) {
        insertOrder(order)

        cartItems.forEach { cartItem ->
            insertOrderItemCrossRef(OrderItemCrossRef(order.orderId, cartItem.id))
        }
    }

    @Transaction
    @Query(
        """
        SELECT * FROM orders 
        JOIN order_items ON orders.orderId = order_items.orderId
        JOIN cart_items ON order_items.cartItemId = cart_items.id
        """
    )
    suspend fun getAllOrdersWithCartItems(): Map<Order, List<CartItem>>

}
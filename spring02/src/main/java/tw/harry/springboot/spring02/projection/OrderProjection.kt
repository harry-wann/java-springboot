package tw.harry.springboot.spring02.projection

import tw.harry.springboot.spring02.entity.OrderDetail
import java.util.Date

interface OrderProjection {
    fun getOrderId(): Int
    fun getOrderDate(): Date
    fun getOrderDetails(): List<OrderDetailProjection>
}